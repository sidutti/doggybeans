package com.harish.sidutti.doggybeans.service;


import akka.actor.ActorRef;
import com.harish.sidutti.doggybeans.dto.Quote;
import com.harish.sidutti.doggybeans.dto.Stats;
import com.harish.sidutti.doggybeans.dto.StockAndQuote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.Utils;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistQuotesRequest;
import yahoofinance.histquotes.Interval;
import yahoofinance.histquotes2.CrumbManager;
import yahoofinance.quotes.stock.StockQuote;
import yahoofinance.quotes.stock.StockStats;
import yahoofinance.util.RedirectableRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class YahooFinanceService {
    private static final Logger log = LoggerFactory.getLogger(HistQuotesRequest.class);
    private final ActorRef stockDataParsingActorRef;


    public YahooFinanceService(ActorRef stockDataParsingActorRef) {
        this.stockDataParsingActorRef = stockDataParsingActorRef;
    }

    public StockAndQuote processStockData(String symbol) {
        Stock stock = new Stock(symbol);

        StockStats yahooStats = stock.getStats();
        Stats stats = new Stats();
        BeanUtils.copyProperties(yahooStats, stats);
        stats.setSymbol(symbol);
        StockQuote yahooQuote = stock.getQuote();
        Quote localQuote = new Quote();
        BeanUtils.copyProperties(yahooQuote, localQuote);
        localQuote.setSymbol(symbol);
        StockAndQuote result = new StockAndQuote();
        com.harish.sidutti.doggybeans.dto.Stock stocklocal = new com.harish.sidutti.doggybeans.dto.Stock();
        result.setQuote(localQuote);
        result.setStats(stats);
        stocklocal.setCurrency(stock.getCurrency());
        stocklocal.setName(stock.getName());
        stocklocal.setStockExchange(stock.getStockExchange());
        result.setStock(stocklocal);
        return result;
    }

    public void processHistoricalResult(String symbol, Calendar from, Calendar to, Interval interval) {
        try {
            Map<String, String> params = new LinkedHashMap<>();
            params.put("period1", String.valueOf(from.getTimeInMillis() / 1000));
            params.put("period2", String.valueOf(to.getTimeInMillis() / 1000));
            params.put("interval", interval.getTag());
            params.put("crumb", CrumbManager.getCrumb());

            String url = YahooFinance.HISTQUOTES2_BASE_URL + URLEncoder.encode(symbol, StandardCharsets.UTF_8) + "?" + Utils.getURLParameters(params);


            // Get CSV from Yahoo
            log.info("Sending request: " + url);

            URL request = new URL(url);
            RedirectableRequest redirectableRequest = new RedirectableRequest(request, 15);
            redirectableRequest.setConnectTimeout(YahooFinance.CONNECTION_TIMEOUT);
            redirectableRequest.setReadTimeout(YahooFinance.CONNECTION_TIMEOUT);
            Map<String, String> requestProperties = new HashMap<>();
            requestProperties.put("Cookie", CrumbManager.getCookie());
            URLConnection connection = redirectableRequest.openConnection(requestProperties);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                br.readLine();
                for (String line = br.readLine(); line != null; line = br.readLine()) {
                    stockDataParsingActorRef.tell(line + YahooFinance.QUOTES_CSV_DELIMITER + symbol, null);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

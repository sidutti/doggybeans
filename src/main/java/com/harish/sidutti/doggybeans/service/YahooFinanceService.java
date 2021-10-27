package com.harish.sidutti.doggybeans.service;


import akka.actor.ActorRef;
import com.harish.sidutti.doggybeans.dto.Stats;
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
import java.io.IOException;
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
    private final ActorRef statsMongoActorRef;
    private final ActorRef stockMongoActorRef;

    public YahooFinanceService(ActorRef stockDataParsingActorRef, ActorRef statsMongoActorRef, ActorRef stockMongoActorRef) {
        this.stockDataParsingActorRef = stockDataParsingActorRef;
        this.statsMongoActorRef = statsMongoActorRef;
        this.stockMongoActorRef = stockMongoActorRef;
    }
    public void processStockData(String symbol) throws IOException {
        Stock stock = new Stock(symbol);
        StockStats yahooStats = stock.getStats();
        Stats stats = new Stats();
        BeanUtils.copyProperties(yahooStats,stats);
        stats.setSymbol(symbol);
        StockQuote yahooQuote = stock.getQuote();
        com.harish.sidutti.doggybeans.dto.Stock localStock =  new com.harish.sidutti.doggybeans.dto.Stock();
        BeanUtils.copyProperties(yahooQuote,localStock);
        localStock.setSymbol(symbol);

    }
    public void processHistoricalResult(String symbol, Calendar from, Calendar to, Interval interval) throws IOException {


        Map<String, String> params = new LinkedHashMap<>();
        params.put("period1", String.valueOf(from.getTimeInMillis() / 1000));
        params.put("period2", String.valueOf(to.getTimeInMillis() / 1000));
        params.put("interval", interval.getTag());
        params.put("crumb", CrumbManager.getCrumb());

        String url = YahooFinance.HISTQUOTES2_BASE_URL + URLEncoder.encode(symbol , StandardCharsets.UTF_8) + "?" + Utils.getURLParameters(params);


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
                stockDataParsingActorRef.tell(line+YahooFinance.QUOTES_CSV_DELIMITER+symbol, null);
            }
        }
    }
}

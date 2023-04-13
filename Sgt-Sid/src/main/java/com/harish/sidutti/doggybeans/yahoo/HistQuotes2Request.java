package com.harish.sidutti.doggybeans.yahoo;

import com.harish.sidutti.Utils;
import com.harish.sidutti.YahooFinance;
import com.harish.sidutti.histquotes.HistoricalQuote;
import com.harish.sidutti.histquotes.Interval;
import com.harish.sidutti.histquotes2.CrumbManager;
import com.harish.sidutti.histquotes2.IntervalMapper;
import com.harish.sidutti.histquotes2.QueryInterval;
import com.harish.sidutti.util.RedirectableRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class HistQuotes2Request {


        public static final Calendar DEFAULT_FROM = Calendar.getInstance();
        public static final Calendar DEFAULT_TO = Calendar.getInstance();
        public static final QueryInterval DEFAULT_INTERVAL = QueryInterval.MONTHLY;
        private static final Logger log = LoggerFactory.getLogger(HistQuotes2Request.class);


        private final String symbol;
        private final Calendar from;
        private final Calendar to;
        private final QueryInterval interval;


        public HistQuotes2Request(String symbol, Calendar from, Calendar to, QueryInterval interval) {
                this.symbol = symbol;
                this.from = this.cleanHistCalendar(from);
                this.to = this.cleanHistCalendar(to);
                this.interval = interval;
        }

        public HistQuotes2Request(String symbol, Date from, Date to) {
                this(symbol, from, to, DEFAULT_INTERVAL);
        }

        public HistQuotes2Request(String symbol, Date from, Date to, QueryInterval interval) {
                this(symbol);
                this.from.setTime(from);
                this.to.setTime(to);
                this.cleanHistCalendar(this.from);
                this.cleanHistCalendar(this.to);
        }

        // Constructors to support the old Interval
        public HistQuotes2Request(String symbol) {
                this(symbol, DEFAULT_FROM, DEFAULT_TO, Interval.DAILY);
        }

        public HistQuotes2Request(String symbol, Calendar from, Calendar to, Interval interval) {
                this(symbol, from, to, IntervalMapper.get(interval));
        }

        public HistQuotes2Request(String symbol, Date from, Date to, Interval interval) {
                this(symbol, from, to, IntervalMapper.get(interval));
        }

        /**
         * Put everything smaller than days at 0
         *
         * @param cal calendar to be cleaned
         */
        private Calendar cleanHistCalendar(Calendar cal) {
                cal.set(Calendar.MILLISECOND, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.HOUR, 0);
                return cal;
        }

        public List<HistoricalQuote> getResult() throws IOException {
                List<HistoricalQuote> result = new ArrayList<>();
                BufferedReader br = getBufferedReader();
                // Parse CSV
                for (String line = br.readLine(); line != null; line = br.readLine()) {

                        log.info("Parsing CSV line: " + Utils.unescape(line));
                        HistoricalQuote quote = this.parseCSVLine(line);
                        result.add(quote);
                }
                return result;
        }

        public BufferedReader getBufferedReader() throws IOException {


                Map<String, String> params = new LinkedHashMap<>();
                params.put("period1", String.valueOf(this.from.getTimeInMillis() / 1000));
                params.put("period2", String.valueOf(this.to.getTimeInMillis() / 1000));

                params.put("interval", this.interval.getTag());

                params.put("crumb", CrumbManager.getCrumb());

                String url = YahooFinance.HISTQUOTES2_BASE_URL + URLEncoder.encode(this.symbol, StandardCharsets.UTF_8) + "?" + Utils.getURLParameters(params);

                // Get CSV from Yahoo
                log.info("Sending request: " + url);

                URL request = new URL(url);
                RedirectableRequest redirectableRequest = new RedirectableRequest(request, 5);
                redirectableRequest.setConnectTimeout(YahooFinance.CONNECTION_TIMEOUT);
                redirectableRequest.setReadTimeout(YahooFinance.CONNECTION_TIMEOUT);
                Map<String, String> requestProperties = new HashMap<>();
                requestProperties.put("Cookie", CrumbManager.getCookie());
                URLConnection connection = redirectableRequest.openConnection(requestProperties);

                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(is);
                br.readLine(); // skip the first line
                return br;
        }

        public HistoricalQuote parseCSVLine(String line) {
                String[] data = line.split(YahooFinance.QUOTES_CSV_DELIMITER);
                return new HistoricalQuote(this.symbol,
                            Utils.parseHistDate(data[0]),
                            Utils.getBigDecimal(data[1]),
                            Utils.getBigDecimal(data[3]),
                            Utils.getBigDecimal(data[2]),
                            Utils.getBigDecimal(data[4]),
                            Utils.getBigDecimal(data[5]),
                            Utils.getLong(data[6])
                );
        }

}

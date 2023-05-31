package com.harish.sidutti.doggybeans.dto;

import com.harish.sidutti.JacksonSerializable;

import java.util.Calendar;

public class StockWithInterval implements JacksonSerializable {
        private String stockSymbol;
        private Calendar from;
        private Calendar to;

        public String getStockSymbol() {
                return stockSymbol;
        }

        public void setStockSymbol(String stockSymbol) {
                this.stockSymbol = stockSymbol;
        }

        public Calendar getFrom() {
                return from;
        }

        public void setFrom(Calendar from) {
                this.from = from;
        }

        public Calendar getTo() {
                return to;
        }

        public void setTo(Calendar to) {
                this.to = to;
        }
}

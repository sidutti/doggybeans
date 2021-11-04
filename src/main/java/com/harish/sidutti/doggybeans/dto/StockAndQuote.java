package com.harish.sidutti.doggybeans.dto;

public class StockAndQuote {

    private Quote quote;
    private History history;
    private Stats stats;
    private Dividend dividend;
    private Stock stock;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Dividend getDividend() {
        return dividend;
    }

    public void setDividend(Dividend dividend) {
        this.dividend = dividend;
    }
}

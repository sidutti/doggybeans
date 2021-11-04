package com.harish.sidutti.doggybeans.dto;

import java.math.BigDecimal;

public class Dividend {

    private final String symbol;

    private String payDate;
    private String exDate;
    private BigDecimal annualYield;
    private BigDecimal annualYieldPercent;

    public Dividend(String symbol) {
        this.symbol = symbol;
    }

    public Dividend(String symbol, String payDate, String exDate, BigDecimal annualYield, BigDecimal annualYieldPercent) {
        this(symbol);
        this.payDate = payDate;
        this.exDate = exDate;
        this.annualYield = annualYield;
        this.annualYieldPercent = annualYieldPercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getExDate() {
        return exDate;
    }

    public void setExDate(String exDate) {
        this.exDate = exDate;
    }

    public BigDecimal getAnnualYield() {
        return annualYield;
    }

    public void setAnnualYield(BigDecimal annualYield) {
        this.annualYield = annualYield;
    }

    public BigDecimal getAnnualYieldPercent() {
        return annualYieldPercent;
    }

    public void setAnnualYieldPercent(BigDecimal annualYieldPercent) {
        this.annualYieldPercent = annualYieldPercent;
    }
}

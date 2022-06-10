package com.harish.sidutti.quotes.stock;

import com.harish.sidutti.JacksonSerializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.math.BigDecimal;
import java.util.Calendar;

@Document(indexName = "stockdividend")
public class StockDividend implements JacksonSerializable {

    @Id
    private String symbol;

    private Calendar payDate;
    private Calendar exDate;
    private BigDecimal annualYield;
    private BigDecimal annualYieldPercent;

    public StockDividend() {
    }

    public StockDividend(String symbol) {
        this.symbol = symbol;
    }

    public StockDividend(String symbol, Calendar payDate, Calendar exDate, BigDecimal annualYield, BigDecimal annualYieldPercent) {
        this(symbol);
        this.payDate = payDate;
        this.exDate = exDate;
        this.annualYield = annualYield;
        this.annualYieldPercent = annualYieldPercent;
    }

    public String getSymbol() {
        return symbol;
    }

    public Calendar getPayDate() {
        return payDate;
    }

    public void setPayDate(Calendar payDate) {
        this.payDate = payDate;
    }

    public Calendar getExDate() {
        return exDate;
    }

    public void setExDate(Calendar exDate) {
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

    @Override
    public String toString() {
        String payDateStr = "/";
        String exDateStr = "/";
        String annualYieldStr = "/";
        if (this.payDate != null) {
            payDateStr = this.payDate.getTime().toString();
        }
        if (this.exDate != null) {
            exDateStr = this.exDate.getTime().toString();
        }
        if (this.annualYieldPercent != null) {
            annualYieldStr = this.annualYieldPercent + "%";
        }
        return "Pay date: " + payDateStr + ", Ex date: " + exDateStr + ", Annual yield: " + annualYieldStr;
    }

}

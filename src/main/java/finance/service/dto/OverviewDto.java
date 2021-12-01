package finance.service.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Slf4j
public class OverviewDto {

    int lots;
    String name;
    String ticker;
    String currency;
    String instrumentType;
    double averagePositionPrice;
    double expectedYield;
    double recommendationMean;
    double returnOnEquity;
    double priceToBook;
    double enterpriseValue;
    double dividendYield;
    double trailingPE;
    double priceToSalesTrailing12Months;
    double marketCap;

    public OverviewDto() {
    }

    public OverviewDto(int lots,
                       String name,
                       String ticker,
                       String instrumentType,
                       String currency,
                       double averagePositionPrice,
                       double expectedYield,
                       double recommendationMean,
                       double returnOnEquity,
                       double priceToBook,
                       double enterpriseValue,
                       double dividendYield,
                       double trailingPE,
                       double priceToSalesTrailing12Months,
                       double marketCap) {
        this.name = name;
        this.ticker = ticker;
        this.averagePositionPrice = averagePositionPrice;
        this.lots = lots;
        this.currency = currency;
        this.expectedYield = expectedYield;
        this.instrumentType = instrumentType;
        this.recommendationMean = recommendationMean;
        this.returnOnEquity = returnOnEquity;
        this.priceToBook = priceToBook;
        this.enterpriseValue = enterpriseValue;
        this.dividendYield = dividendYield;
        this.trailingPE = trailingPE;
        this.priceToSalesTrailing12Months = priceToSalesTrailing12Months;
        this.marketCap = marketCap;
    }
}

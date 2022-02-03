package invest.service.service;

import invest.service.dto.ExchangeDto;
import invest.service.dto.OverviewDto;
import invest.service.dto.QuickAnalysisDto;
import invest.service.dto.TcsTickerDto;
import invest.service.dto.YahooFinancialDto;
import invest.service.dto.YahooStatisticsDto;
import invest.service.dto.YahooSummaryDto;
import invest.service.entity.ExchangeEntity;
import invest.service.entity.PortfolioEntity;
import invest.service.entity.TcsTickerEntity;
import invest.service.entity.YahooFinancialEntity;
import invest.service.entity.YahooStatisticsEntity;
import invest.service.entity.YahooSummaryEntity;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class StockService {

    private final TcsService tcsService;
    private final YahooStockService yahooStockService;
    private final RepositoryService repositoryService;
    private final TickerDictionaryService dictionaryService;

    @Autowired
    public StockService(
            TcsService tcsService,
            YahooStockService yahooStockService,
            RepositoryService repositoryService,
            TickerDictionaryService dictionaryService) {
        this.tcsService = tcsService;
        this.yahooStockService = yahooStockService;
        this.repositoryService = repositoryService;
        this.dictionaryService = dictionaryService;
    }

    public List<OverviewDto> getLatestAvailableData(String token) throws JSONException {
        PortfolioEntity latest = repositoryService.getLatestPortfolioEntity();
        if (latest == null) {
            repositoryService.prepareView();
            return getNewData(token);
        }
        return repositoryService.getOverviewDtos(latest.getUuid());
    }

    // TODO: Рефакторим
    public List<OverviewDto> getNewData(String token) throws JSONException {
        ArrayList<TcsTickerDto> portfolioDtos = tcsService.getPortfolio(token);
        Function<TcsTickerDto, String> checkDictionary = x -> {
            String ticker = x.getTicker();
            String yahooTicker = dictionaryService.getTickerFromDictionary(ticker);
            if (!yahooTicker.equals(ticker)) x.setTicker(yahooTicker);
            return x.getTicker();
        };
        String tickers = portfolioDtos.stream().map(x -> checkDictionary.apply(x)).collect(Collectors.joining(" "));

        List<YahooFinancialDto> financialDtos = yahooStockService.getFinancialDtoList(tickers);
        List<YahooStatisticsDto> statisticsDtos = yahooStockService.getStatisticsDtoList(tickers);
        List<YahooSummaryDto> summaryDtos = yahooStockService.getSummaryDtoList(tickers);
        List<ExchangeDto> exchangeDtos = yahooStockService.getExchangeDtoList();

        UUID uuid = UUID.randomUUID();
        repositoryService.savePortfolioEntity(new PortfolioEntity(uuid));

        portfolioDtos.stream().forEach(x -> repositoryService.saveTcsEntity(new TcsTickerEntity(uuid, x)));
        financialDtos.stream().forEach(x -> repositoryService.saveYahooFinancialEntity(new YahooFinancialEntity(uuid, x)));
        statisticsDtos.stream().forEach(x -> repositoryService.saveYahooStatisticsEntity(new YahooStatisticsEntity(uuid, x)));
        summaryDtos.stream().forEach(x -> repositoryService.saveYahooSummaryEntity(new YahooSummaryEntity(uuid, x)));
        exchangeDtos.stream().forEach(x -> repositoryService.saveExchangeEntity(new ExchangeEntity(uuid, x)));

        return repositoryService.getOverviewDtos(uuid);
    }

    public List<QuickAnalysisDto> getQuickAnalysis(String tickers) {
        tickers = Stream.of(tickers.split(" ")).map(x -> dictionaryService.getTickerFromDictionary(x)).collect(Collectors.joining(" "));
        return yahooStockService.getQuickAnalysis(tickers);
    }

}
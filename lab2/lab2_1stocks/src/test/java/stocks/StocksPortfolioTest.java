package stocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.hamcrest.MockitoHamcrest;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(MockitoExtension.class)
public class StocksPortfolioTest {
    private List<Stock> stocks;

    @Mock
    IStockmarketService market;

    @InjectMocks
    StocksPortfolio portfolio;


    @BeforeEach
    public void setUp() {
        stocks = Arrays.asList(new Stock("stock1", 1), new Stock("stock2", 2));
    }

    @AfterEach
    public void tearDown() {
        stocks = null;
    }

    @Test
    public void getTotalValueTest() {
        when(market.lookUpPrice("stock1")).thenReturn(10.0);
        when(market.lookUpPrice("stock2")).thenReturn(15.0);
        stocks.forEach(portfolio::addStock);
        double res = portfolio.getTotalValue();
        assertThat(res, is(40.0)); // 1 * 10 + 2 * 15 = 40
        verify(market, times(2)).lookUpPrice(anyString());
    }
}

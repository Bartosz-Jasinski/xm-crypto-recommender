package com.epam.recommendationservice.converter;

import com.epam.recommendationservice.model.Crypto;
import com.epam.recommendationservice.model.CryptoStats;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CryptoListToStatsConverterTest {

    public static final String BTC_SYMBOL = "BTC";
    public static final String DOGE_SYMBOL = "DOGE";
    private final CryptoListToStatsConverter converter = new CryptoListToStatsConverter();

    @Test
    public void testStatsAreConvertedProperly() {
        Crypto first = createCrypto(LocalDateTime.of(2022, 1, 1, 6, 0), BTC_SYMBOL, BigDecimal.valueOf(1000));
        Crypto second = createCrypto(LocalDateTime.of(2022, 1, 1, 8, 0), BTC_SYMBOL, BigDecimal.valueOf(3000));
        Crypto third = createCrypto(LocalDateTime.of(2022, 1, 1, 10, 0), BTC_SYMBOL, BigDecimal.valueOf(2000));

        CryptoStats stats = converter.convert(List.of(
                first, second, third
        ));

        Assertions.assertEquals(second, stats.getMax());
        Assertions.assertEquals(first, stats.getMin());
        Assertions.assertEquals(first, stats.getOldest());
        Assertions.assertEquals(third, stats.getNewest());
    }

    @Test
    public void testMultipleStatsAreConvertedProperly() {
        Crypto btcFirst = createCrypto(LocalDateTime.of(2022, 1, 1, 6, 0), BTC_SYMBOL, BigDecimal.valueOf(1000));
        Crypto btcSecond = createCrypto(LocalDateTime.of(2022, 1, 1, 8, 0), BTC_SYMBOL, BigDecimal.valueOf(3000));
        Crypto btcThird = createCrypto(LocalDateTime.of(2022, 1, 1, 10, 0), BTC_SYMBOL, BigDecimal.valueOf(2000));

        Crypto dogeFirst = createCrypto(LocalDateTime.of(2022, 1, 1, 6, 0), DOGE_SYMBOL, BigDecimal.valueOf(100));
        Crypto dogeSecond = createCrypto(LocalDateTime.of(2022, 1, 1, 8, 0), DOGE_SYMBOL, BigDecimal.valueOf(300));
        Crypto dogeThird = createCrypto(LocalDateTime.of(2022, 1, 1, 10, 0), DOGE_SYMBOL, BigDecimal.valueOf(200));

        var symbolToStatsMap = converter.convertAllCryptos(
                Map.of(
                        BTC_SYMBOL, List.of(btcFirst, btcSecond, btcThird),
                        DOGE_SYMBOL, List.of(dogeFirst, dogeSecond, dogeThird)
                )
        );

        Assertions.assertEquals(2, symbolToStatsMap.size());
        Assertions.assertEquals(btcSecond, symbolToStatsMap.get(BTC_SYMBOL).getMax());
        Assertions.assertEquals(btcFirst, symbolToStatsMap.get(BTC_SYMBOL).getMin());
        Assertions.assertEquals(btcFirst, symbolToStatsMap.get(BTC_SYMBOL).getOldest());
        Assertions.assertEquals(btcThird, symbolToStatsMap.get(BTC_SYMBOL).getNewest());
        Assertions.assertEquals(dogeSecond, symbolToStatsMap.get(DOGE_SYMBOL).getMax());
        Assertions.assertEquals(dogeFirst, symbolToStatsMap.get(DOGE_SYMBOL).getMin());
        Assertions.assertEquals(dogeFirst, symbolToStatsMap.get(DOGE_SYMBOL).getOldest());
        Assertions.assertEquals(dogeThird, symbolToStatsMap.get(DOGE_SYMBOL).getNewest());
    }

    private Crypto createCrypto(LocalDateTime measurementDateTime, String symbol, BigDecimal price) {
        Crypto crypto = new Crypto();
        crypto.setMeasurementDateTime(measurementDateTime);
        crypto.setSymbol(symbol);
        crypto.setPriceUsd(price);
        return crypto;
    }
}

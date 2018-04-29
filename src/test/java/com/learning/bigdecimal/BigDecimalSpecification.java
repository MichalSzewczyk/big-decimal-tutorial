package com.learning.bigdecimal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.testng.Assert.assertEquals;

/**
 * BigDecimal class in Java gives us the most precise way of representing decimal numbers.
 * When the domain of an application is related to money - precision of operations on numbers is crucial.
 * Operations on doubles may lead us to precision errors.
 * Operations on BigDecimal are slower and less convenient (operators are not overloaded for BigDecimal
 * so we have to call methods on each arithmetical operations) than on doubles, however we win greater precision.
 */
public class BigDecimalSpecification {
    /**
     * This test aims to compare different rounding modes supported by BigDecimal class
     *
     * @param initialValue  - value for rounding
     * @param expectedValue - expected value after rounding
     * @param roundingMode  - rounding mode
     * @param scale         - number of decimal digits
     */
    @Test(dataProvider = "bigDecimalRoundingModesComparison")
    void shouldDoRoundingBasedOnPassedRoundingMode(String initialValue, String expectedValue, int roundingMode, int scale) {
        // GIVEN
        BigDecimal anyValue = new BigDecimal(initialValue, MathContext.UNLIMITED);

        // WHEN
        anyValue = anyValue.setScale(scale, roundingMode);

        // THEN
        assertEquals(anyValue, new BigDecimal(expectedValue, MathContext.UNLIMITED));
    }

    @DataProvider
    Object[][] bigDecimalRoundingModesComparison() {
        return new Object[][]{
                // Rounding up to nearest value
                {"10.5000000001", "10.51", BigDecimal.ROUND_UP, 2},
                // Rounding down to nearest value
                {"10.5099999999", "10.50", BigDecimal.ROUND_DOWN, 2},
                // Rounding to nearest value, if half then to upper
                {"10.5050000000", "10.51", BigDecimal.ROUND_HALF_UP, 2},
                // Rounding to nearest value, if half then to lower
                {"10.5000000005", "10.50", BigDecimal.ROUND_HALF_DOWN, 2},
                // Rounding to nearest value, if half then to nearest even value
                {"10.5000000005", "10.50", BigDecimal.ROUND_HALF_EVEN, 2},
                {"10.5050000005", "10.51", BigDecimal.ROUND_HALF_EVEN, 2},
                {"10.5150000000", "10.52", BigDecimal.ROUND_HALF_EVEN, 2},
                {"10.5050000000", "10.50", BigDecimal.ROUND_HALF_EVEN, 2},
                // Rounding to nearest greater value
                {"10.5000000005", "10.51", BigDecimal.ROUND_CEILING, 2},
                // Rounding to nearest greater value
                {"-10.5000000005", "-10.50", BigDecimal.ROUND_CEILING, 2},
                // Rounding to nearest lower value
                {"10.5000000005", "10.50", BigDecimal.ROUND_FLOOR, 2},
                // Rounding to nearest lower value
                {"-10.5000000005", "-10.51", BigDecimal.ROUND_FLOOR, 2},
        };
    }


}
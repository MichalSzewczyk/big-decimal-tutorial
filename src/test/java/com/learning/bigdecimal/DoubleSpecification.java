package com.learning.bigdecimal;


import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;


/**
 * Double type in Java is useful in case of values which precise value is not crucial.
 * For instance to represent physical values - in that case precision error is less then measurement error.
 * Calculations on numbers with different magnitudes can result in precision errors.
 * Therefore for calculations witch precision it's necessary to use BigDecimal
 */
public class DoubleSpecification {
    @Test
    void shouldFailOnPreciseOperations() {
        // GIVEN
        double firstAnyNonZeroValue = 0.02d;
        double secondAnyNonZeroValue = 0.03d;
        double expectedResult = -0.01d;

        // WHEN
        double result = firstAnyNonZeroValue - secondAnyNonZeroValue;

        // THEN
        assertNotEquals(result, expectedResult);
    }
}
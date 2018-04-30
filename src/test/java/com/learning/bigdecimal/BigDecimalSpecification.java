package com.learning.bigdecimal;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.function.BiFunction;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

/**
 * BigDecimal class in Java gives us the most precise way of representing decimal numbers.
 * When the domain of an application is related to money - precision of operations on numbers is crucial.
 * Operations on doubles may lead us to precision errors.
 * Operations on BigDecimal are slower and less convenient (operators are not overloaded for BigDecimal
 * so we have to call methods on each arithmetical operations) than on doubles, however we win greater precision.
 */
public class BigDecimalSpecification {
    /**
     * BigDecimal supports 'fail fast' error policy.
     * When any operation doesn't have required information to be performed it throws ArithmeticException
     */
    @Test
    void shouldThrowArithmeticExceptionWhenCannotDetermineHowToScale() {
        // GIVEN
        BigDecimal bigDecimal = new BigDecimal("0.9");

        // WHEN - THEN
        assertThrows(() -> bigDecimal.setScale(0));
    }

    /**
     * This test aims to show basic arithmetical operations on BigDecimals
     *
     * @param leftOperand    - left operand for the operator
     * @param rightOperand   - right operand for the operator
     * @param operator       - operator
     * @param expectedResult - result of operation
     */
    @Test(dataProvider = "arithmeticalOperations")
    void shouldPerformBasicArithmeticalOperations(String leftOperand,
                                                  String rightOperand,
                                                  BiFunction<BigDecimal, BigDecimal, BigDecimal> operator,
                                                  String expectedResult) {
        // GIVEN
        BigDecimal leftDecimal = new BigDecimal(leftOperand);
        BigDecimal rightDecimal = new BigDecimal(rightOperand);
        BigDecimal expectedDecimal = new BigDecimal(expectedResult);

        // WHEN
        BigDecimal result = operator.apply(leftDecimal, rightDecimal);

        // THEN
        assertEquals(result, expectedDecimal);
    }

    @DataProvider
    Object[][] arithmeticalOperations() {
        return new Object[][]{
                {"10", "10", (BiFunction<BigDecimal, BigDecimal, BigDecimal>) BigDecimal::add, "20"},
                {"10", "20", (BiFunction<BigDecimal, BigDecimal, BigDecimal>) BigDecimal::subtract, "-10"},
                {"20", "-10", (BiFunction<BigDecimal, BigDecimal, BigDecimal>) BigDecimal::divide, "-2"},
                {"-10", "-2", (BiFunction<BigDecimal, BigDecimal, BigDecimal>) BigDecimal::multiply, "20"},
                {"-2", "-20", (BiFunction<BigDecimal, BigDecimal, BigDecimal>) BigDecimal::max, "-2"},
                {"-20", "-2", (BiFunction<BigDecimal, BigDecimal, BigDecimal>) BigDecimal::min, "-20"},
        };
    }

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
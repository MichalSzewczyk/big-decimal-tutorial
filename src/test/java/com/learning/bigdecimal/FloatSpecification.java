package com.learning.bigdecimal;

import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;

/**
 * Float type works analogous to double.
 * It's not precise enough to store values which precision is crucial (such as money or stock value)
 */
public class FloatSpecification {
    @Test
    void shouldFailOnPreciseOperations() {
        // GIVEN
        float firstAnyNonZeroValue = 1.89f;
        float expectedResult = 20.79f;

        // WHEN
        float result = firstAnyNonZeroValue * 11;

        // THEN
        assertNotEquals(result, expectedResult);
    }
}
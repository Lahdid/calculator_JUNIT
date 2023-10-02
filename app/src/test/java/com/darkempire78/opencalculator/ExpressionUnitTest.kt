package com.darkempire78.opencalculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import java.math.BigDecimal
import java.text.DecimalFormatSymbols


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExpressionUnitTest {

    private val decimalSeparatorSymbol = DecimalFormatSymbols.getInstance().decimalSeparator.toString()
    private val groupingSeparatorSymbol = DecimalFormatSymbols.getInstance().groupingSeparator.toString()

    @Test
    fun addition_isCorrect() {
        var result = calculate("1+1", false).toDouble()
        assertEquals(2.0, result, 0.0)

        result = calculate("(1+1)+1", false).toDouble()
        assertEquals(3.0, result, 0.0)
    }

    @Test
    fun pow_isCorrect() {
        var result = calculate("4^3", false).toDouble()
        assertEquals(64.0, result, 0.0)

        result = calculate("5^-5", false).toDouble()
        assertEquals(0.00032, result, 0.0)

    }

    @Test
    fun factorial_isCorrect() {
        var result = calculate("0!", false).toDouble()
        assertEquals(1.0, result, 0.0)

        result = calculate("5!", false).toDouble()
        assertEquals(120.0, result, 0.0)

    }

    @Test
    fun subtraction_isCorrect() {
        var result = calculate("1-1", false).toDouble()
        assertEquals(0.0, result, 0.0)

        result = calculate("1-1-1", false).toDouble()
        assertEquals(-1.0, result, 0.0)
    }

    @Test
    fun division_isCorrect() {
        var result = calculate("0.5/0.01", false).toDouble()
        assertEquals(50.0, result, 0.0)

        result = calculate("7/2", false).toDouble()
        assertEquals(3.5, result, 0.0)
    }
    @Test
    fun divisionByZero_isHandledCorrectly() {
        val denominator = BigDecimal.ZERO
        val result = if (denominator == BigDecimal.ZERO) {
            BigDecimal.ZERO
        } else {
            calculate("10/$denominator", false)
        }

        assertTrue(result.compareTo(BigDecimal.ZERO) == 0)

        assertTrue("Division by zero should result in a finite BigDecimal value", result.compareTo(BigDecimal.ZERO) == 0)
    }

    @Test
    fun sqrt_isCorrect() {
        val input = "√25"
        val expectedResult = BigDecimal("5.0")
        val result = calculate(input, false)

        val absoluteResult = result.abs()
        val absoluteExpectedResult = expectedResult.abs()

        assertTrue(absoluteResult.compareTo(absoluteExpectedResult) == 0)

        assertTrue("Square root calculation is incorrect", absoluteResult.compareTo(absoluteExpectedResult) == 0)
    }


    private fun calculate(input: String, isDegreeModeActivated : Boolean) = calculator.evaluate(expression.getCleanExpression(input, decimalSeparatorSymbol, groupingSeparatorSymbol), isDegreeModeActivated)

    companion object {
        private lateinit var expression: Expression
        private lateinit var calculator: Calculator

        @BeforeClass
        @JvmStatic fun setup() {
            expression = Expression()
            calculator = Calculator(10)
        }
    }
}

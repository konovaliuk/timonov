package ua.timonov.web.project.model.user;

import java.math.BigDecimal;

/**
 * represents sum of Money
 */
public final class Money {
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static final int DECIMALS = 2;

    /* sum of money */
    private final BigDecimal value;

    public Money(BigDecimal value) {
        this.value = value;
    }

    public Money(double value) {
        this.value = rounded(BigDecimal.valueOf(value));
    }

    /**
     * adds this sum and addendum
     * @param addendum      sum to add
     * @return              result of adding
     */
    public Money add(Money addendum) {
        return new Money(value.add(addendum.value));
    }

    /**
     * subtracts other sum of money from this
     * @param subtrahend        sum to subtract
     * @return                  result of subtract
     */
    public Money subtract(Money subtrahend){
        return new Money(value.subtract(subtrahend.value));
    }

    /**
     * multiplies this sum on multiplier
     * @param multiplier        coefficient to multiply
     * @return                  result of multiplication
     */
    public Money multiply(double multiplier) {
        return new Money(rounded(value.multiply(BigDecimal.valueOf(multiplier))));
    }

    /* rounds value to certain decimals */
    private BigDecimal rounded(BigDecimal value) {
        return value.setScale(DECIMALS, ROUNDING_MODE);
    }

    public BigDecimal getValue() {
        return value;
    }

    public String toString() {
        return value.toString();
    }
}

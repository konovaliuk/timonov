package ua.timonov.web.project.model.user;

import java.math.BigDecimal;

public final class Money {
    private static final int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
    private static final int DECIMALS = 2;

    private final BigDecimal value;

    public Money(BigDecimal value) {
        this.value = value;
    }

    public Money(double value) {
        this.value = rounded(BigDecimal.valueOf(value));
    }

    /*public Money(long integerPart, int fractionalPart) {
        value = new BigDecimal(formString(integerPart, fractionalPart));
    }*/

    public Money add(Money addendum) {
        return new Money(value.add(addendum.value));
    }

    public Money subtract(Money subtrahend){
        return new Money(value.subtract(subtrahend.value));
    }

    public Money multiply(double multiplier) {
        return new Money(rounded(value.multiply(BigDecimal.valueOf(multiplier))));
    }

    private BigDecimal rounded(BigDecimal value) {
        return value.setScale(DECIMALS, ROUNDING_MODE);
    }

    private String formString(long sumIntegerPart, int sumFractalPart) {
        return String.valueOf(sumIntegerPart) + "." + String.valueOf(sumFractalPart);
    }

    public BigDecimal getValue() {
        return value;
    }

    public String toString() {
        return value.toString();
//        NumberFormat numberFormat = NumberFormat.getInstance();
//        numberFormat.setMinimumFractionDigits(DECIMALS);
//        numberFormat.setMaximumFractionDigits(DECIMALS);
//        return numberFormat.format(value);
    }
}

package com.loloof64.beer.beer_model;

public class Amount {
    private double value;
    private String unit;

    public Amount(double value, String unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}

package com.loloof64.beer.beer_model;

import java.util.Arrays;

public class MethodsList {
    private MethodTemp[] mashTemp;
    private MethodTemp fermentation;
    private String twist;

    public MethodsList(MethodTemp[] mashTemp, MethodTemp fermentation, String twist) {
        this.mashTemp = mashTemp;
        this.fermentation = fermentation;
        this.twist = twist;
    }

    public MethodTemp[] getMashTemp() {
        return mashTemp;
    }

    public MethodTemp getFermentation() {
        return fermentation;
    }

    public String getTwist() {
        return twist;
    }

    public void setMashTemp(MethodTemp[] mashTemp) {
        this.mashTemp = mashTemp;
    }

    public void setFermentation(MethodTemp fermentation) {
        this.fermentation = fermentation;
    }

    public void setTwist(String twist) {
        this.twist = twist;
    }

    @Override
    public String toString() {
        return "MethodsList{" +
                "mashTemp=" + Arrays.toString(mashTemp) +
                ", fermentation=" + fermentation +
                ", twist='" + twist + '\'' +
                '}';
    }
}

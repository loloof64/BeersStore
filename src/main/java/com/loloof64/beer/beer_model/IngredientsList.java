package com.loloof64.beer.beer_model;

import java.util.Arrays;

public class IngredientsList {
    private Ingredient[] malt;
    private Ingredient[] hops;
    private String yeast;

    public IngredientsList(Ingredient[] malt, Ingredient[] hops, String yeast) {
        this.malt = malt;
        this.hops = hops;
        this.yeast = yeast;
    }

    public Ingredient[] getMalt() {
        return malt;
    }

    public Ingredient[] getHops() {
        return hops;
    }

    public String getYeast() {
        return yeast;
    }

    public void setMalt(Ingredient[] malt) {
        this.malt = malt;
    }

    public void setHops(Ingredient[] hops) {
        this.hops = hops;
    }

    public void setYeast(String yeast) {
        this.yeast = yeast;
    }

    @Override
    public String toString() {
        return "IngredientsList{" +
                "malt=" + Arrays.toString(malt) +
                ", hops=" + Arrays.toString(hops) +
                ", yeast='" + yeast + '\'' +
                '}';
    }
}

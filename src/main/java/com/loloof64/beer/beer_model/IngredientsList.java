package com.loloof64.beer.beer_model;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class IngredientsList {
    private final Map<String, Ingredient[]> ingredientList;
    private final String yeast;

    public IngredientsList(Map<String, Ingredient[]> ingredientList, String yeast) {
        this.ingredientList = ingredientList;
        this.yeast = yeast;
    }

    public String getYeast() {
        return yeast;
    }

    public Ingredient[] getIngredientsListFor(String ingredientType){
        return ingredientList.get(ingredientType);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IngredientsList(");
        stringBuilder.append("ingredientsList = [");
        Set<String> ingredientsListKeys = ingredientList.keySet();
        for (String key : ingredientsListKeys){
            stringBuilder.append(key);
            stringBuilder.append(" -> ");
            stringBuilder.append(Arrays.toString(ingredientList.get(key)));
        }
        stringBuilder.append("], yeast = ");
        stringBuilder.append(yeast);
        stringBuilder.append(")");

        return stringBuilder.toString();
    }
}

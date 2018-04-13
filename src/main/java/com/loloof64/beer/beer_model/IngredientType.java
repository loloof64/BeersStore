package com.loloof64.beer.beer_model;

public enum IngredientType {
    MALT("malt"), HOPS("hops");

    private String representation;

    IngredientType(String representation){
        this.representation = representation;
    }

    public String getRepresentation(){
        return representation;
    }
}

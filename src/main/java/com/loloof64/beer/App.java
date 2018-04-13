package com.loloof64.beer;

import com.loloof64.beer.beer_model.Beer;
import com.loloof64.beer.beer_model.IngredientType;

import javax.json.JsonStructure;
import java.io.*;
import java.net.MalformedURLException;

public class App 
{
    public static void main( String[] args )
    {
        testAPI_includingIngredient();
    }

    private static void testAPI_getAllBeers(){
        Beer [] allBeers = new BeersListAPI().getAllBeers();
        for (int beerIndex = 0; beerIndex < allBeers.length; beerIndex++){
            System.out.println(allBeers[beerIndex].getName());
        }
        System.out.println(allBeers.length + " beer(s) found.");
    }

    public static void testAPI_getAllBeersMatchingId(){
        Beer [] allBeers = new BeersListAPI().getBeersMatchingId(10);
        for (int beerIndex = 0; beerIndex < allBeers.length; beerIndex++){
            System.out.println(allBeers[beerIndex].getName());
        }
        System.out.println(allBeers.length + " beer(s) found.");
    }

    private static void testAPI_includingIngredient(){
        Beer[] allBeers = new BeersListAPI().getBeersContainingIngredient(IngredientType.MALT, "Extra Pale", 6);
        for (int beerIndex = 0; beerIndex < allBeers.length; beerIndex++){
            System.out.println(allBeers[beerIndex].getName());
        }
        System.out.println(allBeers.length + " beer(s) found.");
    }
}

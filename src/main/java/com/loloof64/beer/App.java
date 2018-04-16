package com.loloof64.beer;

import com.loloof64.beer.beer_model.Beer;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        testAPI_includingIngredient();
    }

    private static void testAPI_getAllBeers(){
        List<Beer> allBeers = new BeersListAPI().getAllBeers();
        for (int beerIndex = 0; beerIndex < allBeers.size(); beerIndex++){
            System.out.println(allBeers.get(beerIndex).getName());
        }
        System.out.println(allBeers.size() + " beer(s) found.");
    }

    public static void testAPI_getAllBeersMatchingId(){
        Beer matchingBeer = new BeersListAPI().getBeersMatchingId(10);
        System.out.println("Matching beer : "+matchingBeer);
    }

    private static void testAPI_includingIngredient(){
        List<Beer> allBeers = new BeersListAPI().getBeersContainingIngredient("malt", "Extra Pale", 6, -1);
        for (int beerIndex = 0; beerIndex < allBeers.size(); beerIndex++){
            System.out.println(allBeers.get(beerIndex).getName());
        }
        System.out.println(allBeers.size() + " beer(s) found.");
    }
}

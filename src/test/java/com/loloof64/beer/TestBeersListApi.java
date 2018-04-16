package com.loloof64.beer;

import com.loloof64.beer.beer_model.Beer;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBeersListApi {

    private BeersListAPI api;

    @Before
    public void setup(){
        api = new BeersListAPI();
    }

    @Test
    public void testGetAllBeers(){
        List<Beer> beerList = api.getAllBeers();
        assertEquals(234, beerList.size());
    }

    @Test
    public void testGetBeersMatchingId(){
        Beer matchingBeer = api.getBeersMatchingId(10);
        assertEquals("Bramling X", matchingBeer.getName());

        Beer matchingBeer2 = api.getBeersMatchingId(500);
        assertEquals(null, matchingBeer2);
    }

    @Test
    public void testGetBeersWithMinimalIngredientAmount(){
        List<Beer> allBeers = new BeersListAPI().getBeersContainingIngredient("malt", "Extra Pale", 6, +1);
        assertEquals(49, allBeers.size());
    }

    @Test
    public void testGetBeersWithMaxmimalIngredientAmount(){
        List<Beer> allBeers = new BeersListAPI().getBeersContainingIngredient("malt", "Extra Pale", 6, -1);
        assertEquals(90, allBeers.size());
    }

}

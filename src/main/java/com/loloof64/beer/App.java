package com.loloof64.beer;

import com.loloof64.beer.beer_model.Beer;

import javax.json.JsonStructure;
import java.io.*;
import java.net.MalformedURLException;

public class App 
{
    public static void main( String[] args )
    {
        Beer[] allBeers = new BeersListAPI().getBeersWhoseNameMatchPattern(" Rebel");
        for (int beerIndex = 0; beerIndex < allBeers.length; beerIndex++){
            System.out.println(allBeers[beerIndex].getName());
        }
    }
}

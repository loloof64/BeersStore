package com.loloof64.beer;

import javax.json.JsonStructure;
import java.io.*;
import java.net.MalformedURLException;

public class App 
{
    public static void main( String[] args )
    {
        new BeersListAPI().getAllBeers();
    }
}

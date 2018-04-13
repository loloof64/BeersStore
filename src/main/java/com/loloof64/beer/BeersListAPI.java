package com.loloof64.beer;

import com.loloof64.beer.beer_model.*;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonStructure;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class BeersListAPI {

    /**
     * Gets all beers
     * @return Beer[]
     */
    public Beer[] getAllBeers(){
        List<Beer> allPagesBeers = new ArrayList<>();
        boolean isNotComplete = true;
        int currentPage = 1;
        while (isNotComplete){
            Beer [] currentPageBeers = getBeersForRequest("https://api.punkapi.com/v2/beers?page="+currentPage);
            allPagesBeers.addAll(List.of(currentPageBeers));

            if (currentPageBeers.length == 0){
                isNotComplete = false;
            }
            currentPage++;
        }
        Beer [] allPagesBeersArray = new Beer[allPagesBeers.size()];
        allPagesBeers.toArray(allPagesBeersArray);

        return allPagesBeersArray;
    }

    /**
     * Returns all beers matching a given pattern
     * @param namePattern - the pattern with optional spaces into the name
     * @return Beer[]
     */
    public Beer[] getBeersWhoseNameMatchPattern(String namePattern){
        String realNamePattern = namePattern.replaceAll(" ", "_");
        String baseRequest = "https://api.punkapi.com/v2/beers?beer_name=";
        return getBeersForRequest(baseRequest + realNamePattern);
    }

    /**
     * Gets all beers matching the given id
     * @param id - int
     * @return Beer[]
     */
    public Beer[] getBeersMatchingId(int id){
        String baseRequest = "https://api.punkapi.com/v2/beers?ids=";
        return getBeersForRequest(baseRequest + Integer.valueOf(id).toString());
    }

    /**
     * Gets all beers including at least the given ingredient at least in the given quantity
     * @param ingredientType - IngredientType - the ingredient type
     * @param partOfIngredientName - String - part of the ingredient name
     * @param quantity - int - minimum quantity
     * @return Beer[] - list of beers matching criteria.
     */
    public Beer[] getBeersContainingIngredient(IngredientType ingredientType, String partOfIngredientName, int quantity){
        Beer[] allBeers = getAllBeers();

        List<Beer> filteredBeersList = new ArrayList<>();
        for (Beer currentBeer : filteredBeersList){
            IngredientsList ingredientsList = currentBeer.getIngredients();

            Ingredient[] ingredientsArray;
            switch (ingredientType){
                case MALT:
                    ingredientsArray = ingredientsList.getMalt();
                    break;
                case HOPS:
                    ingredientsArray = ingredientsList.getHops();
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized ingredient type "+ingredientType.getRepresentation());
            }

            // Chercher un ingredient a la correspondance partielle du nom
            // Si trouve comparer la quantité et décider en fonction

            boolean isMatchingCriteria = false;
            if (isMatchingCriteria) {
                filteredBeersList.add(currentBeer);
            }
        }

        Beer [] filteredBeersArray = new Beer[filteredBeersList.size()];
        filteredBeersList.toArray(filteredBeersArray);

        return filteredBeersArray;
    }

    private Beer[] getBeersForRequest(String request){
        RequestJSONFetcher jsonFetcher = new RequestJSONFetcher();
        try {
            JsonStructure jsonInstance = jsonFetcher.apiAdressToJSONStructure(
                    request);
            return parseBeersListJSON(jsonInstance);
        }
        catch (MalformedURLException e){
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Beer[] parseBeersListJSON(JsonStructure beersJSONList){
        List<Beer> beersList = new ArrayList<>();

        JsonArray beersListAsArray = (JsonArray) beersJSONList;
        for (int beerIndex = 0; beerIndex < beersListAsArray.size(); beerIndex++) {
            Beer beer = parseBeerFromJsonObject(beersListAsArray.getJsonObject(beerIndex));
            beersList.add(beer);
        }

        Beer [] beersToReturn = new Beer[beersList.size()];
        beersList.toArray(beersToReturn);

        return beersToReturn;
    }

    private Beer parseBeerFromJsonObject(JsonObject jsonObject) {
        int id = parseIntForKey(jsonObject, "id");
        String name = jsonObject.getString("name");
        String tagLine = jsonObject.getString("tagline");
        String firstBrewed = jsonObject.getString("first_brewed");
        String description = jsonObject.getString("description");
        String imageUrl = jsonObject.getString("image_url");
        double abv = parseDoubleForKey(jsonObject, "abv");
        double ibu = parseDoubleForKey(jsonObject, "ibu");
        double targetFg = parseDoubleForKey(jsonObject, "target_fg");
        double targetOg = parseDoubleForKey(jsonObject, "target_og");
        double ebc = parseDoubleForKey(jsonObject, "ebc");
        double srm = parseDoubleForKey(jsonObject, "srm");
        double ph = parseDoubleForKey(jsonObject, "ph");
        double attenuationLevel = parseDoubleForKey(jsonObject, "attenuation_level");
        Amount volume = parseAmount(jsonObject.getJsonObject("volume"));
        Amount boilVolume = parseAmount(jsonObject.getJsonObject("boil_volume"));
        MethodsList method = parseMethod(jsonObject);
        IngredientsList ingredients = parseIngredients(jsonObject);
        String [] foodPairings = jsonStringArrayToPrimitiveStringArray(jsonObject.getJsonArray("food_pairing"));
        String brewersTips = jsonObject.getString("brewers_tips");
        String contributor = jsonObject.getString("contributed_by");

        Beer currentBeer = new Beer(
                id, name, tagLine, firstBrewed, description, imageUrl, abv, ibu, targetFg, targetOg, ebc, srm, ph, attenuationLevel, volume,
                boilVolume, method, ingredients, foodPairings, brewersTips, contributor
        );

        return currentBeer;
    }

    private Amount parseAmount(JsonObject amountObject) {
        Amount valueToReturn;
        if (amountObject != null) {
            double value = Double.parseDouble(amountObject.get("value").toString());
            String unit = amountObject.getString("unit");

            valueToReturn = new Amount(value, unit);
        }
        else {
            valueToReturn = null;
        }
        return valueToReturn;
    }

    private int parseIntForKey(JsonObject jsonObject, String key){
        int value;
        try {
            String valueStr = jsonObject.get(key).toString();
            value = Integer.parseInt(valueStr);
        } catch (NullPointerException e){
            value = 0;
        } catch (NumberFormatException e){
            value = 0;
        }
        return value;
    }

    private double parseDoubleForKey(JsonObject jsonObject, String key){
        double value;
        try {
            String jsonValueString = jsonObject.get(key).toString();
            value = Double.parseDouble(jsonValueString);
        }
        catch (NullPointerException e){
            value = 0.0;
        }
        catch (NumberFormatException e){
            value = 0.0;
        }
        return value;
    }

    private MethodsList parseMethod(JsonObject jsonObject) {
        JsonArray mashTempJSONArray = jsonObject.getJsonArray("mash_temp");
        JsonObject fermentationJSONObject = jsonObject.getJsonObject("fermentation");
        String twistString;
        try {
            twistString = jsonObject.getString("twist");
        }
        catch (NullPointerException e){
            twistString = null;
        }

        List<MethodTemp> mashTempList = new ArrayList<>();
        MethodTemp [] mashTemp = new MethodTemp[mashTempList.size()];
        try {
            for (int mashTempIndex = 0; mashTempIndex < mashTempJSONArray.size(); mashTempIndex++){
                mashTempList.add(parseMethodTemperature(mashTempJSONArray.getJsonObject(mashTempIndex)));
            }
        }
        catch (NullPointerException e){
            // skip !!!
        }
        mashTempList.toArray(mashTemp);

        MethodTemp fermentation = parseMethodTemperature(fermentationJSONObject);

        return new MethodsList(mashTemp, fermentation, twistString);
    }

    private MethodTemp parseMethodTemperature(JsonObject jsonObject){
        Amount temp;
        try {
            temp = parseAmount(jsonObject.getJsonObject("temp"));
        } catch (NullPointerException e){
            temp = null;
        }

        double duration = parseDoubleForKey(jsonObject, "duration");

        return new MethodTemp(temp, duration);
    }

    private IngredientsList parseIngredients(JsonObject jsonObject) {
        Ingredient [] malt = parseIngredientsList(jsonObject.getJsonArray("malt"));
        Ingredient [] hops = parseIngredientsList(jsonObject.getJsonArray("hops"));
        String yeast;
        try {
            yeast = jsonObject.getString("yeast");
        } catch (NullPointerException e){
            yeast = null;
        }

        return new IngredientsList(malt, hops, yeast);
    }

    private Ingredient[] parseIngredientsList(JsonArray jsonArray){
        List<Ingredient> ingredientList = new ArrayList<>();
        try {
            for (int ingredientIndex = 0; ingredientIndex < jsonArray.size(); ingredientIndex++){
                JsonObject ingredientObject = jsonArray.getJsonObject(ingredientIndex);
                String name = ingredientObject.getString("name");
                Amount amount = parseAmount(ingredientObject);
                Ingredient currentIngredient = new Ingredient(name, amount);
                ingredientList.add(currentIngredient);
            }
        } catch (NullPointerException e){
            // skip !!!
        }

        Ingredient [] ingredientsArray = new Ingredient[ingredientList.size()];
        ingredientList.toArray(ingredientsArray);
        return ingredientsArray;
    }

    private String[] jsonStringArrayToPrimitiveStringArray(JsonArray jsonArray) {
        List<String> stringsList = new ArrayList<>();

        for (int strIndex = 0; strIndex < jsonArray.size(); strIndex++){
            String currentString = jsonArray.getString(strIndex);
            stringsList.add(currentString);
        }

        String [] listToReturn = new String[stringsList.size()];
        stringsList.toArray(listToReturn);
        return listToReturn;
    }

}

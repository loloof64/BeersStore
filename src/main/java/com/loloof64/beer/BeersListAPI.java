package com.loloof64.beer;

import com.loloof64.beer.beer_model.*;

import javax.json.*;
import java.io.IOException;
import java.math.BigDecimal;
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
        for (Beer currentBeer : allBeers){
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

            Ingredient [] ingredientsMatchingNamePartially = findIngredientsMatchingNamePartially(partOfIngredientName, ingredientsArray);
            boolean isMatchingCriteria = checkIfIngredientIsMatchingCriteria(ingredientsMatchingNamePartially, quantity);

            if (isMatchingCriteria) {
                filteredBeersList.add(currentBeer);
            }
        }

        Beer [] filteredBeersArray = new Beer[filteredBeersList.size()];
        filteredBeersList.toArray(filteredBeersArray);

        return filteredBeersArray;
    }

    private boolean checkIfIngredientIsMatchingCriteria(Ingredient[] ingredientsMatchingNamePartially, int quantity) {
        boolean isMatchingCriteria;
        double ingredientQuantity = 0.0;
        for (Ingredient currentIngredient : ingredientsMatchingNamePartially){
            ingredientQuantity += currentIngredient.getAmount().getValue();
        }
        isMatchingCriteria = ingredientsMatchingNamePartially.length > 0 && ingredientQuantity >= quantity;
        return isMatchingCriteria;
    }

    private Ingredient[] findIngredientsMatchingNamePartially(String partOfIngredientName, Ingredient[] ingredientsArray) {
        List<Ingredient> matchingIngredients = new ArrayList<>();
        for (int ingredientIndex = 0; ingredientIndex < ingredientsArray.length; ingredientIndex++){
            Ingredient currentIngredient = ingredientsArray[ingredientIndex];
            boolean matchSoughtIngredientPartially = partOfIngredientName.contains(currentIngredient.getName());
            if (matchSoughtIngredientPartially){
                matchingIngredients.add(currentIngredient);
            }
        }

        Ingredient[] resultToReturn = new Ingredient[matchingIngredients.size()];
        matchingIngredients.toArray(resultToReturn);

        return resultToReturn;
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
            if (beer != null) {
                beersList.add(beer);
            }
        }

        Beer [] beersToReturn = new Beer[beersList.size()];
        beersList.toArray(beersToReturn);

        return beersToReturn;
    }

    private Beer parseBeerFromJsonObject(JsonObject jsonObject) {
        if (jsonObject == null) return null;
        int id = parseIntForKey(jsonObject, "id");
        String name = parseStringForKey(jsonObject, "name");
        String tagLine = parseStringForKey(jsonObject, "tagline");
        String firstBrewed = parseStringForKey(jsonObject, "first_brewed");
        String description = parseStringForKey(jsonObject, "description");
        String imageUrl = parseStringForKey(jsonObject, "image_url");
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
        IngredientsList ingredients = parseIngredients(jsonObject.getJsonObject("ingredients"));
        String [] foodPairings = jsonStringArrayToPrimitiveStringArray(jsonObject.getJsonArray("food_pairing"));
        String brewersTips = parseStringForKey(jsonObject, "brewers_tips");
        String contributor = parseStringForKey(jsonObject, "contributed_by");

        Beer currentBeer = new Beer(
                id, name, tagLine, firstBrewed, description, imageUrl, abv, ibu, targetFg, targetOg, ebc, srm, ph, attenuationLevel, volume,
                boilVolume, method, ingredients, foodPairings, brewersTips, contributor
        );

        return currentBeer;
    }

    private String parseStringForKey(JsonObject object, String key){
        String value;
        try {
            value = object.getString(key);
            return value;
        }
        catch (NullPointerException e){
            return "";
        }
    }

    private Amount parseAmount(JsonObject amountObject) {
        Amount valueToReturn;
        if (amountObject != null) {
            double value = parseDoubleForKey(amountObject, "value");
            String unit = parseStringForKey(amountObject, "unit");

            valueToReturn = new Amount(value, unit);
        }
        else {
            valueToReturn = new Amount(0.0, "");
        }
        return valueToReturn;
    }

    private int parseIntForKey(JsonObject jsonObject, String key){
        if (jsonObject.getJsonNumber(key) == null) return 0;
        JsonNumber jsonNumber = jsonObject.getJsonNumber(key);
        return jsonNumber.intValue();
    }

    private double parseDoubleForKey(JsonObject jsonObject, String key){
        if (jsonObject.getJsonNumber(key) == null) return 0.0;
        JsonNumber jsonNumber = jsonObject.getJsonNumber(key);
        BigDecimal numberBigDecimal = jsonNumber.bigDecimalValue();
        return numberBigDecimal.doubleValue();
    }

    private MethodsList parseMethod(JsonObject jsonObject) {
        if (jsonObject == null) return  new MethodsList(new MethodTemp[0], new MethodTemp(new Amount(0.0, ""), 0.0), "");
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

        MethodTemp fermentation = fermentationJSONObject != null ? parseMethodTemperature(fermentationJSONObject) : new MethodTemp(new Amount(0.0, ""), 0.0);

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
            yeast = jsonObject.get("yeast").toString();
        } catch (NullPointerException e){
            yeast = null;
        }

        return new IngredientsList(malt, hops, yeast);
    }

    private Ingredient[] parseIngredientsList(JsonArray jsonArray){
        List<Ingredient> ingredientList = new ArrayList<>();
        for (int ingredientIndex = 0; ingredientIndex < jsonArray.size(); ingredientIndex++){
            JsonObject ingredientObject = jsonArray.getJsonObject(ingredientIndex);
            String name = parseStringForKey(ingredientObject, "name");
            Amount amount = parseAmount(ingredientObject);
            String add = parseStringForKey(ingredientObject, "add");
            String attribute = parseStringForKey(ingredientObject, "attribute");
            Ingredient currentIngredient = new Ingredient(name, amount, add, attribute);
            ingredientList.add(currentIngredient);
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

package com.loloof64.beer.beer_model;

import java.util.Arrays;

public class Beer {
    private int id;
    private String name;
    private String tagLine;
    private String firstBrewed;
    private String description;
    private String imageUrl;
    private double abv;
    private double ibu;
    private double targetFg;
    private double targetOg;
    private double ebc;
    private double srm;
    private double ph;
    private double attenuationLevel;
    private Amount volume;
    private Amount boilVolume;
    private MethodsList method;
    private IngredientsList ingredients;
    private String[] foodPairings;
    private String brewersTips;
    private String contributedBy;

    public Beer(int id, String name, String tagLine, String firstBrewed, String description, String imageUrl, double abv, double ibu, double targetFg, double targetOg, double ebc, double srm, double ph, double attenuationLevel, Amount volume, Amount boilVolume, MethodsList method, IngredientsList ingredients, String[] foodPairings, String brewersTips, String contributedBy) {
        this.id = id;
        this.name = name;
        this.tagLine = tagLine;
        this.firstBrewed = firstBrewed;
        this.description = description;
        this.imageUrl = imageUrl;
        this.abv = abv;
        this.ibu = ibu;
        this.targetFg = targetFg;
        this.targetOg = targetOg;
        this.ebc = ebc;
        this.srm = srm;
        this.ph = ph;
        this.attenuationLevel = attenuationLevel;
        this.volume = volume;
        this.boilVolume = boilVolume;
        this.method = method;
        this.ingredients = ingredients;
        this.foodPairings = foodPairings;
        this.brewersTips = brewersTips;
        this.contributedBy = contributedBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public String getFirstBrewed() {
        return firstBrewed;
    }

    public void setFirstBrewed(String firstBrewed) {
        this.firstBrewed = firstBrewed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getAbv() {
        return abv;
    }

    public void setAbv(double abv) {
        this.abv = abv;
    }

    public double getIbu() {
        return ibu;
    }

    public void setIbu(double ibu) {
        this.ibu = ibu;
    }

    public double getTargetFg() {
        return targetFg;
    }

    public void setTargetFg(double targetFg) {
        this.targetFg = targetFg;
    }

    public double getTargetOg() {
        return targetOg;
    }

    public void setTargetOg(double targetOg) {
        this.targetOg = targetOg;
    }

    public double getEbc() {
        return ebc;
    }

    public void setEbc(double ebc) {
        this.ebc = ebc;
    }

    public double getSrm() {
        return srm;
    }

    public void setSrm(double srm) {
        this.srm = srm;
    }

    public double getPh() {
        return ph;
    }

    public void setPh(double ph) {
        this.ph = ph;
    }

    public double getAttenuationLevel() {
        return attenuationLevel;
    }

    public void setAttenuationLevel(double attenuationLevel) {
        this.attenuationLevel = attenuationLevel;
    }

    public Amount getVolume() {
        return volume;
    }

    public void setVolume(Amount volume) {
        this.volume = volume;
    }

    public Amount getBoilVolume() {
        return boilVolume;
    }

    public void setBoilVolume(Amount boilVolume) {
        this.boilVolume = boilVolume;
    }

    public MethodsList getMethod() {
        return method;
    }

    public void setMethod(MethodsList method) {
        this.method = method;
    }

    public IngredientsList getIngredients() {
        return ingredients;
    }

    public void setIngredients(IngredientsList ingredients) {
        this.ingredients = ingredients;
    }

    public String[] getFoodPairings() {
        return foodPairings;
    }

    public void setFoodPairings(String[] foodPairings) {
        this.foodPairings = foodPairings;
    }

    public String getBrewersTips() {
        return brewersTips;
    }

    public void setBrewersTips(String brewersTips) {
        this.brewersTips = brewersTips;
    }

    public String getContributedBy() {
        return contributedBy;
    }

    public void setContributedBy(String contributedBy) {
        this.contributedBy = contributedBy;
    }

    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", firstBrewed='" + firstBrewed + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", abv=" + abv +
                ", ibu=" + ibu +
                ", targetFg=" + targetFg +
                ", targetOg=" + targetOg +
                ", ebc=" + ebc +
                ", srm=" + srm +
                ", ph=" + ph +
                ", attenuationLevel=" + attenuationLevel +
                ", volume=" + volume +
                ", boilVolume=" + boilVolume +
                ", method=" + method +
                ", ingredients=" + ingredients +
                ", foodPairings=" + Arrays.toString(foodPairings) +
                ", brewersTips='" + brewersTips + '\'' +
                ", contributedBy='" + contributedBy + '\'' +
                '}';
    }
}

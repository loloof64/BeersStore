package com.loloof64.beer.beer_model;

public class MethodTemp {
    private Amount temperature;
    private double duration;

    public MethodTemp(Amount temperature, double duration) {
        this.temperature = temperature;
        this.duration = duration;
    }

    public Amount getTemperature() {
        return temperature;
    }

    public double getDuration() {
        return duration;
    }

    public void setTemperature(Amount temperature) {
        this.temperature = temperature;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "MethodTemp{" +
                "temperature=" + temperature +
                ", duration=" + duration +
                '}';
    }
}

package com.example.carrentalmanagmentsystem;

public class Car {
    private String regId;
    private String carName;
    private String companyName;
    private String model;
    private double dailyRate;
    private String color;

    public Car(String regId, String carName, String companyName, String model, double dailyRate, String color) {
        this.regId = regId;
        this.carName = carName;
        this.companyName = companyName;
        this.model = model;
        this.dailyRate = dailyRate;
        this.color = color;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

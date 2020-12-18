package com.ruanjie.donkey.bean;


public class TravelStatisticsBean  {

    /**
     * mileage_total : 0
     * duration_total : 19
     */

    private double mileage_total;
    private double duration_total;

    public double getMileage_total() {
        return mileage_total;
    }

    public void setMileage_total(double mileage_total) {
        this.mileage_total = mileage_total;
    }

    public double getDuration_total() {
        return duration_total;
    }

    public void setDuration_total(double duration_total) {
        this.duration_total = duration_total;
    }

    @Override
    public String toString() {
        return "TravelStatisticsBean{" +
                "mileage_total=" + mileage_total +
                ", duration_total=" + duration_total +
                '}';
    }
}

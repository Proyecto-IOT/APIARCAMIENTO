package com.example.apiarcamento.models;

import java.util.List;

public class Spot {

    private List<Spot.Result> data;
    public List<Spot.Result> getData() {
        return data;
    }

    public void setData(List<Spot.Result> data) {
        this.data = data;
    }

    public static class Result {

        public String spot;
        public int parking_id;

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public String getSpot() {
            return spot;
        }

        public void setSpot(String spot) {
            this.spot = spot;
        }
    }
}

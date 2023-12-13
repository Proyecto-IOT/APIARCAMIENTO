// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import java.util.List;

import java.time.OffsetDateTime;

public class Vehicle {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String value) {
        this.msg = value;
    }
    private List<Result> data;
    public List<Result> getData() {
        return data;
    }

    public void setData(List<Result> data) {
        this.data = data;
    }

    public static class Result {
        private String spot;

        private String license_plate;
        private String color;
        private OffsetDateTime updatedAt;
        private long user_id;
        private OffsetDateTime createdAt;
        private String model;
        private String brand;
        private long vehicle_id;

        private int parking_id;

        public int getParking_id() {
            return parking_id;
        }

        public void setParking_id(int parking_id) {
            this.parking_id = parking_id;
        }

        public String getLicensePlate() { return license_plate; }
        public void setLicensePlate(String value) { this.license_plate = value; }

        public String getColor() { return color; }
        public void setColor(String value) { this.color = value; }

        public OffsetDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(OffsetDateTime value) { this.updatedAt = value; }

        public long getUserid() { return user_id; }
        public void setUserid(long value) { this.user_id = value; }

        public OffsetDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

        public String getModel() { return model; }
        public void setModel(String value) { this.model = value; }

        public String getBrand() { return brand; }
        public void setBrand(String value) { this.brand = value; }

        public long getVehicleid() { return vehicle_id; }
        public void setVehicleid(long value) { this.vehicle_id = value; }
    }
}

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import java.util.List;

import java.time.OffsetDateTime;

public class Vehicle {

    private List<Result> data;

    public List<Result> getData() {
        return data;
    }

    public void setData(List<Result> data) {
        this.data = data;
    }

    public class Result {
        private String licensePlate;
        private String color;
        private OffsetDateTime updatedAt;
        private long userid;
        private OffsetDateTime createdAt;
        private String model;
        private String brand;
        private long vehicleid;


        public String getLicensePlate() { return licensePlate; }
        public void setLicensePlate(String value) { this.licensePlate = value; }

        public String getColor() { return color; }
        public void setColor(String value) { this.color = value; }

        public OffsetDateTime getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(OffsetDateTime value) { this.updatedAt = value; }

        public long getUserid() { return userid; }
        public void setUserid(long value) { this.userid = value; }

        public OffsetDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

        public String getModel() { return model; }
        public void setModel(String value) { this.model = value; }

        public String getBrand() { return brand; }
        public void setBrand(String value) { this.brand = value; }

        public long getVehicleid() { return vehicleid; }
        public void setVehicleid(long value) { this.vehicleid = value; }
    }
}

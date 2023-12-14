// Activityresponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.OffsetDateTime;
import java.util.List;

public class Activityresponse {
    private String msg;
    private boolean result;
    private List<Datum> data;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public boolean getResult() { return result; }
    public void setResult(boolean value) { this.result = value; }

    public List<Datum> getData() { return data; }
    public void setData(List<Datum> value) { this.data = value; }

    public class Datum {
        @SerializedName("license_plate")
        @Expose
        private String licensePlate;
        private long isActive;
        private String color;
        @SerializedName("created_at")
        @Expose
        private String updatedAt;
        private long parkingvehiclesid;
        private String spot;
        private long parkingid;
        @SerializedName("updated_at")
        @Expose
        private String createdAt;
        private String model;
        private long vehicleid;
        private String brand;

        public String getLicensePlate() { return licensePlate; }
        public void setLicensePlate(String value) { this.licensePlate = value; }

        public long getIsActive() { return isActive; }
        public void setIsActive(long value) { this.isActive = value; }

        public String getColor() { return color; }
        public void setColor(String value) { this.color = value; }

        public String getUpdatedAt() { return updatedAt; }
        public void setUpdatedAt(String value) { this.updatedAt = value; }

        public long getParkingvehiclesid() { return parkingvehiclesid; }
        public void setParkingvehiclesid(long value) { this.parkingvehiclesid = value; }

        public String getSpot() { return spot; }
        public void setSpot(String value) { this.spot = value; }

        public long getParkingid() { return parkingid; }
        public void setParkingid(long value) { this.parkingid = value; }

        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String value) { this.createdAt = value; }

        public String getModel() { return model; }
        public void setModel(String value) { this.model = value; }

        public long getVehicleid() { return vehicleid; }
        public void setVehicleid(long value) { this.vehicleid = value; }

        public String getBrand() { return brand; }
        public void setBrand(String value) { this.brand = value; }
    }
}

// Datum.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation



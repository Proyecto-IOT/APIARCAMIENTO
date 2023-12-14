// Incidenteresponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Incidentresponsehumo {
    private String msg;
    private boolean result;
    private List<Humo> humo;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public boolean getResult() { return result; }

    public void setResult(boolean value) { this.result = value; }

    public List<Humo> getHumo() { return humo; }
    public void setHumo(List<Humo> value) { this.humo = value; }

    public static class Humo {
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        private String value;

        public String getCreatedAt() { return createdAt; }
        public void setCreatedAt(String value) { this.createdAt = value; }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}



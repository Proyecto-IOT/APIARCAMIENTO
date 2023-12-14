// Incidenteresponse.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Incidenteresponse {
    private String msg;
    private boolean result;

    private List<Sonido> sonido;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public boolean getResult() { return result; }
    public void setResult(boolean value) { this.result = value; }


    public List<Sonido> getSonido() {
        return sonido;
    }

    public void setSonido(List<Sonido> sonido) {
        this.sonido = sonido;
    }

    public class Sonido {
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



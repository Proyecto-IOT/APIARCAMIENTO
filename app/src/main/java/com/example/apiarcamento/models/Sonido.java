// Sonido.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import java.util.List;
import java.time.OffsetDateTime;
public class Sonido {
    private String msg;
    private boolean result;
    private List<Result> sonido;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public boolean getResult() { return result; }
    public void setResult(boolean value) { this.result = value; }

    public List<Result> getSonido() { return sonido; }
    public void setSonido(List<Result> value) { this.sonido = value; }

    public static class Result {
        private OffsetDateTime createdAt;
        private String value;

        public OffsetDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}


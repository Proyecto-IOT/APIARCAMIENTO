// Humo.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.time.OffsetDateTime;

public class Humo {
    private String msg;
    private boolean result;
    private List<Result> humo;

    public String getMsg() { return msg; }
    public void setMsg(String value) { this.msg = value; }

    public boolean getResult() { return result; }
    public void setResult(boolean value) { this.result = value; }

    public List<Result> getHumo() { return humo; }
    public void setHumo(List<Result> value) { this.humo = value; }

    public static class Result {
        private OffsetDateTime createdAt;
        private String value;

        public String getCreatedAt() { return createdAt.format(DateTimeFormatter.ISO_DATE_TIME); }
        public void setCreatedAt(OffsetDateTime value) { this.createdAt = value; }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
    }
}

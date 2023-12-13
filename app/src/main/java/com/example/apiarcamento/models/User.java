// Loginjson.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.example.apiarcamento.models;
import java.time.OffsetDateTime;

// Credentials.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

// User.java

// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

//import java.time.OffsetDateTime;

public class User {
    private int is_active;
    private String gender;
    private OffsetDateTime updated_at;
    private int user_id;
    private String token;

    private String name;
    private String last_name;
    private String mother_surname;
    private OffsetDateTime created_at;
    private String email;
    public String password;

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String value) {
        this.msg = value;
    }
    public int getIsActive() { return is_active; }
    public void setIsActive(int value) { this.is_active = value; }

    public String getGender() { return gender; }
    public void setGender(String value) { this.gender = value; }

    public OffsetDateTime getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(OffsetDateTime value) { this.updated_at = value; }

    public int getUserid() { return user_id; }
    public void setUserid(int value) { this.user_id = value; }

    public String getName() { return name; }
    public void setName(String value) { this.name = value; }

    public String getLastName() { return last_name; }
    public void setLastName(String value) { this.last_name = value; }

    public String getMotherSurname() { return mother_surname; }
    public void setMotherSurname(String value) { this.mother_surname = value; }

    public OffsetDateTime getCreatedAt() { return created_at; }
    public void setCreatedAt(OffsetDateTime value) { this.created_at = value; }

    public String getEmail() { return email; }
    public void setEmail(String value) { this.email = value; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String toString() {
        return "User{" +
                "isActive=" + is_active +
                ", gender='" + gender + '\'' +
                ", updatedAt=" + updated_at +
                ", userid=" + user_id +
                ", name='" + name + '\'' +
                ", lastName='" + last_name + '\'' +
                ", motherSurname='" + mother_surname + '\'' +
                ", createdAt=" + created_at +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
    public User() {
        // Constructor predeterminado sin argumentos
    }
}

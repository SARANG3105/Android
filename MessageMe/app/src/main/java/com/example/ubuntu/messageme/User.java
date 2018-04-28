package com.example.ubuntu.messageme;

import java.io.Serializable;

/**
 * Created by ubuntu on 4/25/18.
 */

public class User implements Serializable {
    private String email;
    private String name;
    private String userId;

    public User(){

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

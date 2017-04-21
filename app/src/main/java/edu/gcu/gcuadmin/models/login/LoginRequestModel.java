package edu.gcu.gcuadmin.models.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrivats on 3/24/2017.
 */

public class LoginRequestModel {
    @SerializedName("username")
    String username;

    @SerializedName("password")
    String password;

    @SerializedName("grant_type")
    String grant_type;


    public LoginRequestModel(String username, String password, String grant_type) {
        this.username = username;
        this.password = password;
        this.grant_type = grant_type;
    }

    @Override
    public String toString() {
        return "LoginRequestModel{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", grant_type='" + grant_type + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

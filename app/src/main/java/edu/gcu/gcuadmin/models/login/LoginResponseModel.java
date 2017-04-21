package edu.gcu.gcuadmin.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrivats on 3/24/2017.
 */

public class LoginResponseModel {

    @SerializedName("access_token")
    @Expose
    public String accessToken;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("expires_in")
    @Expose
    public Integer expiresIn;
    @SerializedName("as:client_id")
    @Expose
    public String asClientId;
    @SerializedName("userName")
    @Expose
    public String userName;
    @SerializedName("group_id")
    @Expose
    public String groupId;
    @SerializedName("staffid")
    @Expose
    public String staffid;
    @SerializedName(".issued")
    @Expose
    public String issued;
    @SerializedName(".expires")
    @Expose
    public String expires;


    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public String getAsClientId() {
        return asClientId;
    }

    public String getUserName() {
        return userName;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getStaffid() {
        return staffid;
    }

    public String getIssued() {
        return issued;
    }

    public String getExpires() {
        return expires;
    }
}

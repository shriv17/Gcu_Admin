package edu.gcu.gcuadmin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrivats on 4/4/2017.
 */

public class CancelClassModel {

    @SerializedName("sessionno")
    @Expose
    public String sessionno;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("entryby")
    @Expose
    public String entryby;
    @SerializedName("opt_id")
    @Expose
    public String optId;

    public String getSessionno() {
        return sessionno;
    }

    public void setSessionno(String sessionno) {
        this.sessionno = sessionno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }
}

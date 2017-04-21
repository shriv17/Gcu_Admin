package edu.gcu.gcuadmin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrivats on 4/17/2017.
 */

public class AdmissionSourcesModel {
    @SerializedName("SourceInfo")
    @Expose
    public String sourceInfo;
    @SerializedName("Total")
    @Expose
    public String total;

    public String getSourceInfo() {
        return sourceInfo;
    }

    public String getTotal() {
        return total;
    }
}

package edu.gcu.gcuadmin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrivats on 4/17/2017.
 */

public class AdmissionEnquiryModel {
    @SerializedName("year")
    @Expose
    public String year;
    @SerializedName("totalenquiry")
    @Expose
    public String totalenquiry;
    @SerializedName("totalenrolled")
    @Expose
    public String totalenrolled;
    @SerializedName("totaladmitted")
    @Expose
    public String totaladmitted;

    public String getYear() {
        return year;
    }

    public String getTotalEnquiry() {
        return totalenquiry;
    }

    public String getTotalEnrolled() {
        return totalenrolled;
    }

    public String getTotalAdmitted() {
        return totaladmitted;
    }
}

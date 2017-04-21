package edu.gcu.gcuadmin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shrivats on 4/17/2017.
 */

public class CourseEnquiryModel {
    @SerializedName("Course")
    @Expose
    public String course;
    @SerializedName("totalenquiry")
    @Expose
    public String totalenquiry;
    @SerializedName("totalpending")
    @Expose
    public String totalpending;
    @SerializedName("totalenrolled")
    @Expose
    public String totalenrolled;

    public String getCourse() {
        return course;
    }

    public String getTotalenquiry() {
        return totalenquiry;
    }

    public String getTotalpending() {
        return totalpending;
    }

    public String getTotalenrolled() {
        return totalenrolled;
    }
}

package edu.gcu.gcuadmin.view;

import java.util.ArrayList;

import edu.gcu.gcuadmin.models.CourseEnquiryModel;

/**
 * Created by Shrivats on 4/13/2017.
 */

public interface CourseEnquiryView {
    void onCourseEnquirySuccess(ArrayList<CourseEnquiryModel> ceModel);
    void onCourseEnquiryError(Throwable msg);
}

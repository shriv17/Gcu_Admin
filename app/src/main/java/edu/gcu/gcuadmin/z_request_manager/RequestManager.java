package edu.gcu.gcuadmin.z_request_manager;

import java.util.ArrayList;

import edu.gcu.gcuadmin.constants.UrlConstants;
import edu.gcu.gcuadmin.models.AdmissionEnquiryModel;
import edu.gcu.gcuadmin.models.AdmissionSourcesModel;
import edu.gcu.gcuadmin.models.CourseEnquiryModel;
import edu.gcu.gcuadmin.models.login.LoginResponseModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Shrivats on 3/24/2017.
 */

public interface RequestManager {


    @FormUrlEncoded
    @POST(UrlConstants.loginUrl)
    Call<LoginResponseModel> login(@Field("username") String username, @Field("password") String
            password, @Field("grant_type") String grant_type);

    @GET(UrlConstants.admissionEnquiryUrl)
    Call<ArrayList<AdmissionEnquiryModel>> getAdmissionEnquiry();

    @GET(UrlConstants.admissionEnquiryDatedUrl)
    Call<ArrayList<AdmissionEnquiryModel>> getDatedAdmissionEnquiry(@Query("date") String date);

    @GET(UrlConstants.admissionSourcesUrl)
    Call<ArrayList<AdmissionSourcesModel>> getAdmissionSources(@Query("Year") String year);

    @GET(UrlConstants.courseEnquiryUrl)
    Call<ArrayList<CourseEnquiryModel>> getCourseEnquiry();

    @GET(UrlConstants.courseDayOrWeekEnquiryUrl)
    Call<ArrayList<CourseEnquiryModel>> getDatedCourseEnquiry(@Query("fromdate") String fromDate, @Query("todate") String toDate);

    @GET(UrlConstants.admissionSourcesDateWiseUrl)
    Call<ArrayList<AdmissionSourcesModel>> getDatedAdmissionSources(@Query("fromdate") String fromDate, @Query("todate") String toDate);
}

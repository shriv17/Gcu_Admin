package edu.gcu.gcuadmin.presenter;

import java.util.ArrayList;

import edu.gcu.gcuadmin.models.CourseEnquiryModel;
import edu.gcu.gcuadmin.view.CourseEnquiryView;
import edu.gcu.gcuadmin.z_request_manager.RequestManager;
import edu.gcu.gcuadmin.z_request_manager.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shrivats on 4/13/2017.
 */

public class CourseEnquiryPresenterImpl implements CourseEnquiryPresenter {

    private CourseEnquiryView ceView;

    public CourseEnquiryPresenterImpl(CourseEnquiryView ceView) {
        this.ceView = ceView;
    }

    @Override
    public void getCourseEnquiry(String fromDate, String toDate) {
        RequestManager manager = ServiceGenerator.getRestWebService();
        Call<ArrayList<CourseEnquiryModel>> call = manager.getDatedCourseEnquiry(fromDate, toDate);
        call.enqueue(new Callback<ArrayList<CourseEnquiryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CourseEnquiryModel>> call, Response<ArrayList<CourseEnquiryModel>> response) {
                if(response.body() != null)
                    ceView.onCourseEnquirySuccess(response.body());
                else
                    ceView.onCourseEnquiryError(null);
            }

            @Override
            public void onFailure(Call<ArrayList<CourseEnquiryModel>> call, Throwable t) {
                ceView.onCourseEnquiryError(t);
            }
        });
    }

    @Override
    public void getCourse(String year) {
        RequestManager manager = ServiceGenerator.getRestWebService();
        Call<ArrayList<CourseEnquiryModel>> call = manager.getCourseEnquiry();
        call.enqueue(new Callback<ArrayList<CourseEnquiryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<CourseEnquiryModel>> call, Response<ArrayList<CourseEnquiryModel>> response) {
                if(response.body() != null)
                    ceView.onCourseEnquirySuccess(response.body());
                else
                    ceView.onCourseEnquiryError(null);
            }

            @Override
            public void onFailure(Call<ArrayList<CourseEnquiryModel>> call, Throwable t) {
                ceView.onCourseEnquiryError(t);
            }
        });
    }
}

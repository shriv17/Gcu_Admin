package edu.gcu.gcuadmin.presenter;

import java.util.ArrayList;

import edu.gcu.gcuadmin.models.AdmissionEnquiryModel;
import edu.gcu.gcuadmin.view.AdmissionEnquiryView;
import edu.gcu.gcuadmin.z_request_manager.RequestManager;
import edu.gcu.gcuadmin.z_request_manager.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shrivats on 4/13/2017.
 */

public class AdmissionEnquiryPresenterImpl implements AdmissionEnquiryPresenter {

    private AdmissionEnquiryView aeView;

    public AdmissionEnquiryPresenterImpl(AdmissionEnquiryView aeView) {
        this.aeView = aeView;
    }

    @Override
    public void getAdmissionEnquiry() {
        RequestManager manager = ServiceGenerator.getRestWebService();
        Call<ArrayList<AdmissionEnquiryModel>> call = manager.getAdmissionEnquiry();
        call.enqueue(new Callback<ArrayList<AdmissionEnquiryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AdmissionEnquiryModel>> call, Response<ArrayList<AdmissionEnquiryModel>> response) {
                if(response.body() != null)
                    aeView.onAdmissionEnquirySuccess(response.body());
                else
                    aeView.onAdmissionEnquiryError("Empty Response. Please try again.");
            }

            @Override
            public void onFailure(Call<ArrayList<AdmissionEnquiryModel>> call, Throwable t) {
                aeView.onAdmissionEnquiryError(t.getMessage());
            }
        });
    }

    @Override
    public void getDatedAdmissionEnquiry(String date) {
        RequestManager manager = ServiceGenerator.getRestWebService();
        Call<ArrayList<AdmissionEnquiryModel>> call = manager.getDatedAdmissionEnquiry(date);
        call.enqueue(new Callback<ArrayList<AdmissionEnquiryModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AdmissionEnquiryModel>> call, Response<ArrayList<AdmissionEnquiryModel>> response) {
                if(response.body() != null)
                aeView.onDatedAdmissionEnquirySuccess(response.body());
                else
                  aeView.onAdmissionEnquiryError("Can't fetch report for selected date. Please " +
                          "try again");
            }

            @Override
            public void onFailure(Call<ArrayList<AdmissionEnquiryModel>> call, Throwable t) {
                aeView.onAdmissionEnquiryError("Can't connect to server. Please check your " +
                        "internet connection");
            }
        });
    }
}

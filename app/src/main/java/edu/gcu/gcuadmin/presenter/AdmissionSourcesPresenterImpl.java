package edu.gcu.gcuadmin.presenter;

import java.util.ArrayList;

import edu.gcu.gcuadmin.models.AdmissionSourcesModel;
import edu.gcu.gcuadmin.view.AdmissionSourcesView;
import edu.gcu.gcuadmin.z_request_manager.RequestManager;
import edu.gcu.gcuadmin.z_request_manager.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shrivats on 4/13/2017.
 */

public class AdmissionSourcesPresenterImpl implements AdmissionSourcesPresenter {

    AdmissionSourcesView aSView;


    public AdmissionSourcesPresenterImpl(AdmissionSourcesView aSView) {
        this.aSView = aSView;
    }

    @Override
    public void getAdmissionSources(String year) {
        RequestManager manager = ServiceGenerator.getRestWebService();
        Call<ArrayList<AdmissionSourcesModel>> call = manager.getAdmissionSources(year);
        call.enqueue(new Callback<ArrayList<AdmissionSourcesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AdmissionSourcesModel>> call, Response<ArrayList<AdmissionSourcesModel>> response) {
                if(response.body() != null)
                    aSView.onAdmissionSourceSuccess(response.body());
                else
                    aSView.onAdmissionSourceError("Empty response. Please try again.");

            }

            @Override
            public void onFailure(Call<ArrayList<AdmissionSourcesModel>> call, Throwable t) {
                aSView.onAdmissionSourceError("Server not reachable.");
            }
        });
    }

    @Override
    public void getAdmissionSourcesByDate(String fromDate, String toDate) {
        RequestManager manager = ServiceGenerator.getRestWebService();
        Call<ArrayList<AdmissionSourcesModel>> call = manager.getDatedAdmissionSources(fromDate, toDate);
        call.enqueue(new Callback<ArrayList<AdmissionSourcesModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AdmissionSourcesModel>> call, Response<ArrayList<AdmissionSourcesModel>> response) {
                if(response.body() != null)
                    aSView.onAdmissionSourceSuccess(response.body());
                else
                    aSView.onAdmissionSourceError("Empty response. Please try again.");
            }

            @Override
            public void onFailure(Call<ArrayList<AdmissionSourcesModel>> call, Throwable t) {
                aSView.onAdmissionSourceError("Server not reachable.");
            }
        });
    }
}

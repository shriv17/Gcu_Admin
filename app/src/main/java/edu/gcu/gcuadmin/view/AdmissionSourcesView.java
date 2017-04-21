package edu.gcu.gcuadmin.view;

import java.util.ArrayList;

import edu.gcu.gcuadmin.models.AdmissionSourcesModel;

/**
 * Created by Shrivats on 4/13/2017.
 */

public interface AdmissionSourcesView {
    void onAdmissionSourceSuccess(ArrayList<AdmissionSourcesModel> asModel);
    void onAdmissionSourceError(String msg);
}

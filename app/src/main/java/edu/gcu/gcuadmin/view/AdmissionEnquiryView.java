package edu.gcu.gcuadmin.view;

import java.util.ArrayList;

import edu.gcu.gcuadmin.models.AdmissionEnquiryModel;

/**
 * Created by Shrivats on 4/13/2017.
 */

public interface AdmissionEnquiryView {
    void onAdmissionEnquirySuccess(ArrayList<AdmissionEnquiryModel> aeModel);
    void onDatedAdmissionEnquirySuccess(ArrayList<AdmissionEnquiryModel> aeDatedModel);
    void onAdmissionEnquiryError(String msg);
}

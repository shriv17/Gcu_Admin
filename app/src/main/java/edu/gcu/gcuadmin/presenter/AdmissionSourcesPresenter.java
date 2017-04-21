package edu.gcu.gcuadmin.presenter;

/**
 * Created by Shrivats on 4/13/2017.
 */

public interface AdmissionSourcesPresenter {
    void getAdmissionSources(String year);
    void getAdmissionSourcesByDate(String fromDate, String toDate);
}

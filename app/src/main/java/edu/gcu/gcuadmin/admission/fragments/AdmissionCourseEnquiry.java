package edu.gcu.gcuadmin.admission.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.adapters.CourseEnquiryAdapter;
import edu.gcu.gcuadmin.models.CourseEnquiryModel;
import edu.gcu.gcuadmin.presenter.CourseEnquiryPresenter;
import edu.gcu.gcuadmin.presenter.CourseEnquiryPresenterImpl;
import edu.gcu.gcuadmin.view.CourseEnquiryView;

/**
 * Created by Shrivats on 4/11/2017.
 */

public class AdmissionCourseEnquiry extends Fragment implements CourseEnquiryView, View.OnClickListener {

    private ImageButton mRefresh;
    private RecyclerView mRecycler;
    private String fromDate = "01Jan2017";
    private String toDate = "";
    List<ArrayList<CourseEnquiryModel>> megaModel = new ArrayList<>();
    int c;
    private ProgressBar pBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_report_graph, container, false);
        mRefresh = (ImageButton) v.findViewById(R.id.course_enquiry_refresh);
        pBar = (ProgressBar) v.findViewById(R.id.course_enquiry_loading);
        mRefresh.setOnClickListener(this);
        mRecycler = (RecyclerView) v.findViewById(R.id.course_enquiry_recycler);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(manager);

        Calendar cal = Calendar.getInstance();

        toDate = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)) + getMonth(cal.get(Calendar.MONTH)) +
                cal.get
                (Calendar.YEAR) ;

        c = 0;
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchCourseEnquiry();
    }

    private void fetchCourseEnquiry() {
        pBar.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
        CourseEnquiryPresenter presenter = new CourseEnquiryPresenterImpl(this);
        presenter.getCourse("");

    }
    private void fetchDatedCourseEnquiry(){
        pBar.setVisibility(View.VISIBLE);
        mRecycler.setVisibility(View.GONE);
        CourseEnquiryPresenter presenter = new CourseEnquiryPresenterImpl(this);
        presenter.getCourseEnquiry(fromDate, toDate);

    }
    @Override
    public void onCourseEnquirySuccess(ArrayList<CourseEnquiryModel> ceModel) {
        switch (c){
            case 0:
                megaModel.add(ceModel);
                fromDate = toDate;
                fetchDatedCourseEnquiry();
                ++c;
                break;
            case 1:
                megaModel.add(ceModel);
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(Calendar.DAY_OF_YEAR, -7);
                Date newDate = cal.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("ddMMMyyyy");
                fromDate = sdf.format(newDate);
                fetchDatedCourseEnquiry();
                ++c;
                break;
            case 2:
                megaModel.add(ceModel);
                initializeRecycler(megaModel);
                c = 0;
                break;

        }

    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    private void initializeRecycler(List<ArrayList<CourseEnquiryModel>> megaModel) {
        pBar.setVisibility(View.GONE);
        mRecycler.setVisibility(View.VISIBLE);
        CourseEnquiryAdapter adapter = new CourseEnquiryAdapter(megaModel.get(0), megaModel.get(1), megaModel.get(2));
        mRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCourseEnquiryError(Throwable msg) {
        pBar.setVisibility(View.GONE);
        mRecycler.setVisibility(View.GONE);
        try{
        Snackbar.make(getView(), "Error fetching. Please try again.", 2500).show();
        }catch (Exception e){
            Toast.makeText(getContext(), "Can't connect. Please try again", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.course_enquiry_refresh:
                megaModel = new ArrayList<>();
                c = 0;
                fetchCourseEnquiry();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    private String getMonth(int month){
        switch (month){
            case 0:
                return "Jan";
            case 1:
                return "Feb";
            case 2:
                return "Mar";
            case 3:
                return "Apr";
            case 4:
                return "May";
            case 5:
                return "Jun";
            case 6:
                return "Jul";
            case 7:
                return "Aug";
            case 8:
                return "Sep";
            case 9:
                return "Oct";
            case 10:
                return "Nov";
            case 11:
                return "Dec";
            default:
                return "May";
        }
    }
}

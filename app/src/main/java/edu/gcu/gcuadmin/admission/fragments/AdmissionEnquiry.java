package edu.gcu.gcuadmin.admission.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.models.AdmissionEnquiryModel;
import edu.gcu.gcuadmin.presenter.AdmissionEnquiryPresenter;
import edu.gcu.gcuadmin.presenter.AdmissionEnquiryPresenterImpl;
import edu.gcu.gcuadmin.view.AdmissionEnquiryView;

/**
 * Created by Shrivats on 4/11/2017.
 */

public class AdmissionEnquiry extends Fragment implements AdmissionEnquiryView{

    private BarChart mBarChart;
    private ProgressDialog pDialog;
    int ENTRY_COUNT = 0;
    private int isDatedEnquiry = 0;
    String selectedDate = "";
    private List<IBarDataSet> barDataSets;
    private TextView mSelectDate;
    List<BarEntry> mTotalEnquiryEntry;
    List<BarEntry> mTotalEnrolledEntry;
    List<BarEntry> mTotalAdmittedEntry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_total_report, container, false);
        mBarChart = (BarChart) v.findViewById(R.id.total_report_bar_chart);
        mSelectDate = (TextView) v.findViewById(R.id.total_report_date);
        mSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar();
            }
        });
        Button mTotal = (Button) v.findViewById(R.id.total_report_button_total);
        mTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareBarData();
            }
        });
        Button mDateInclusive = (Button) v.findViewById(R.id.total_report_button_date_inclusive);
        mDateInclusive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo fetch report unto the selected date
            }
        });
        ImageButton mRefresh = (ImageButton) v.findViewById(R.id.total_report_button_refresh);
        pDialog = new ProgressDialog(getContext());
        pDialog.setTitle("Fetching Admission enquiries..");
        pDialog.setMessage("Please wait while we generate admission enquiries.");
        pDialog.setIcon(R.drawable.ic_icon_wait);
        pDialog.setCancelable(false);
        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prepareBarData();
            }
        });
        Calendar cal = Calendar.getInstance();
        selectedDate = cal.get(Calendar.DAY_OF_MONTH) + getMonth(cal.get(Calendar.MONTH)) + cal
                .get(Calendar.YEAR);
        mSelectDate.setText("Click to select");
        return v;
    }

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

    @Override
    public void onStart()
    {
        super.onStart();
        prepareBarData();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            ENTRY_COUNT = 1;
        }

    }

    private void prepareBarData() {
        AdmissionEnquiryPresenter presenter = new AdmissionEnquiryPresenterImpl(this);
        presenter.getAdmissionEnquiry();
        if(ENTRY_COUNT != 0)
            pDialog.show();
    }

    @Override
    public void onAdmissionEnquirySuccess(ArrayList<AdmissionEnquiryModel> aeModel) {
        initializeChart(aeModel);
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onDatedAdmissionEnquirySuccess(ArrayList<AdmissionEnquiryModel> aeDatedModel) {
        initializeChart(aeDatedModel);
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    private void initializeChart(final ArrayList<AdmissionEnquiryModel> aeModel) {
        mTotalEnquiryEntry = new ArrayList<>();
        mTotalEnrolledEntry = new ArrayList<>();
        mTotalAdmittedEntry = new ArrayList<>();

        float i = 0 ;
        for(AdmissionEnquiryModel model : aeModel){
            mTotalEnquiryEntry.add(new BarEntry(i, Float.parseFloat(model
                    .getTotalEnquiry())));
            mTotalEnrolledEntry.add(new BarEntry(i,
                    Float.parseFloat(model.getTotalEnrolled())));
            mTotalAdmittedEntry.add(new BarEntry(i, Float.parseFloat(model.getTotalAdmitted() !=
                    null ? model.getTotalAdmitted() : "0")));
            ++i;
        }
        BarDataSet mTotalEnquirySet = new BarDataSet(mTotalEnquiryEntry, "Total Enquiry");
        mTotalEnquirySet.setColor(Color.BLUE);

        BarDataSet mTotalEnrolledSet = new BarDataSet(mTotalEnrolledEntry, "Total Enrolled");
        mTotalEnrolledSet.setColor(Color.DKGRAY);

        barDataSets = new ArrayList<>();
        barDataSets.add(mTotalEnquirySet);
        barDataSets.add(mTotalEnrolledSet);

        IAxisValueFormatter formatter = new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                if(value < aeModel.size() && value >= 0)
                    return aeModel.get((int) value).getYear();
                else
                    return String.valueOf(value);
            }
        };


        BarData mData = new BarData(barDataSets);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum(mData.getXMax() + 1);
        xAxis.setGranularity(1f);
        //xAxis.setLabelCount(aeModel.size()-1);
        xAxis.setValueFormatter(formatter);
        xAxis.setAxisLineWidth(1f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setSpaceMax(1f);

        mData.setValueTextSize(14f);
        mData.setBarWidth(0.4f);
        Legend legend = mBarChart.getLegend();
        legend.setFormSize(14f);
        legend.setTextSize(12f);
        legend.setXEntrySpace(20f);
        mBarChart.getAxisLeft().setAxisMinimum(0f);
        mBarChart.setData(mData);
        mBarChart.getAxisRight().setEnabled(false);
        //mBarChart.getAxisLeft().setCenterAxisLabels(true);
        mBarChart.setFitBars(true);
        mBarChart.groupBars(0f, 0.16f, 0.02f);
        mBarChart.animateXY(1000, 800);
        mBarChart.invalidate();
        mBarChart.getDescription().setEnabled(false);

    }

    @Override
    public void onAdmissionEnquiryError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
        ENTRY_COUNT = 0;
    }

    private void showCalendar(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog dialog = new DatePickerDialog(getContext());
            dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        selectedDate = String.valueOf(dayOfMonth) + getMonth(month) + String.valueOf
                                (year);
                        mSelectDate.setText(
                                String.valueOf(dayOfMonth) +
                                        " - " + getMonth(month));

                }
            });
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    fetchDatedEnquiry();
                }
            });
        }
        else{
            final DatePicker picker = new DatePicker(getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select Date");
            builder.setView(picker);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                        selectedDate = String.valueOf(picker.getDayOfMonth()) + getMonth(picker
                                .getMonth()) + String.valueOf(picker.getYear());

                        mSelectDate.setText(
                                String.valueOf(picker.getDayOfMonth()) +
                                        " - " + getMonth(picker.getMonth())
                        );
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    fetchDatedEnquiry();
                }
            });
        }
    }
    private void fetchDatedEnquiry(){
        AdmissionEnquiryPresenter presenter = new AdmissionEnquiryPresenterImpl(this);
        presenter.getDatedAdmissionEnquiry(selectedDate);
        pDialog.show();

    }
}

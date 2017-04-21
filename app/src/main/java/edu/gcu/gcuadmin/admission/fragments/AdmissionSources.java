package edu.gcu.gcuadmin.admission.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.constants.AppConstants;
import edu.gcu.gcuadmin.models.AdmissionSourcesModel;
import edu.gcu.gcuadmin.presenter.AdmissionSourcesPresenter;
import edu.gcu.gcuadmin.presenter.AdmissionSourcesPresenterImpl;
import edu.gcu.gcuadmin.view.AdmissionSourcesView;

/**
 * Created by Shrivats on 4/11/2017.
 */

public class AdmissionSources extends Fragment implements AdmissionSourcesView, View.OnClickListener{

    private PieChart mDailyReportPieChart;
    private ImageButton mRefresh;
    private ProgressDialog pDialog;
    String YEAR = "26";
    private TextView mFromDate;
    private TextView mToDate;
    private String fromDate = "";
    private String toDate = "";
    private Button mSubmit;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dashboard_daily_report, container, false);
        mDailyReportPieChart = (PieChart) v.findViewById(R.id.daily_report_pie_chart);
        mRefresh = (ImageButton) v.findViewById(R.id.daily_report_button_refreshPie);
        mFromDate = (TextView) v.findViewById(R.id.admission_sources_from_date);
        mToDate = (TextView) v.findViewById(R.id.admission_sources_to_date);
        mSubmit = (Button) v.findViewById(R.id.admission_sources_submit);

        mSubmit.setOnClickListener(this);
        mFromDate.setOnClickListener(this);
        mToDate.setOnClickListener(this);

        pDialog = new ProgressDialog(getContext());
        pDialog.setTitle("Fetching Admission sources..");
        pDialog.setMessage("Please wait while we generate admission sources report.");
        pDialog.setIcon(R.drawable.ic_icon_wait);
        pDialog.setCancelable(false);
        Calendar cal = Calendar.getInstance();
        fromDate = cal.get(Calendar.DAY_OF_MONTH) + getMonth(cal.get(Calendar.MONTH)) + cal.get
                (Calendar.YEAR);
        toDate = cal.get(Calendar.DAY_OF_MONTH) + getMonth(cal.get(Calendar.MONTH)) + cal.get
                (Calendar.YEAR);
        mFromDate.setText(cal.get(Calendar.DAY_OF_MONTH) +
                " - " + getMonth(cal.get(Calendar.MONTH))+
                " - " + cal.get(Calendar.YEAR));
        mToDate.setText(cal.get(Calendar.DAY_OF_MONTH) +
                " - " + getMonth(cal.get(Calendar.MONTH))+
                " - " + cal.get(Calendar.YEAR));
        fetchAdmissionSources(YEAR);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        mRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchAdmissionSources(YEAR);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    private void initializeChart( ArrayList<AdmissionSourcesModel> asModel) {

        List<PieEntry> pieEntryList = new ArrayList<>();

        for (AdmissionSourcesModel model: asModel) {
            pieEntryList.add(new PieEntry(Float.parseFloat(model.getTotal()), model.getSourceInfo
                    () + " (" + model.getTotal() + ") "));

        }

        PieDataSet dataSet = new PieDataSet(pieEntryList, "Sources");

        dataSet.setColors(AppConstants.getColorArray(), getContext());
        dataSet.setHighlightEnabled(true);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(14f);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);

        PieData data = new PieData(dataSet);
        Legend chartLegend = mDailyReportPieChart.getLegend();
        chartLegend.setFormSize(14f);
        chartLegend.setTextSize(12f);
        chartLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        chartLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        chartLegend.setWordWrapEnabled(true);
        chartLegend.setDrawInside(false);
        chartLegend.setXEntrySpace(20f);
        chartLegend.setYEntrySpace(5f);
        mDailyReportPieChart.setData(data);
        mDailyReportPieChart.setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        mDailyReportPieChart.setCenterTextColor(Color.BLACK);
        mDailyReportPieChart.setDrawEntryLabels(false);
        mDailyReportPieChart.setDrawHoleEnabled(false);
        mDailyReportPieChart.setDrawSlicesUnderHole(false);
        mDailyReportPieChart.setCenterTextColor(Color.DKGRAY);
        mDailyReportPieChart.animateY(1500);
        Description desc = mDailyReportPieChart.getDescription();
        desc.setText("Admission Sources");
        desc.setTextAlign(Paint.Align.LEFT);
        desc.setTextSize(18f);
        mDailyReportPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.e("entryvalue", e.toString());
            }

            @Override
            public void onNothingSelected() {

            }
        });
        mDailyReportPieChart.invalidate();

    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    private void fetchAdmissionSources(String year){
        pDialog.show();
        AdmissionSourcesPresenter presenter = new AdmissionSourcesPresenterImpl(this);
        presenter.getAdmissionSources(year);
    }

    @Override
    public void onAdmissionSourceSuccess(ArrayList<AdmissionSourcesModel> asModel) {
        initializeChart(asModel);
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onAdmissionSourceError(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
        if(pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.admission_sources_from_date:
                showCalendar("From");
                break;

            case R.id.admission_sources_to_date:
                showCalendar("To");
                break;

            case R.id.admission_sources_submit:
                fetchDatedSourcesReport();
                break;
        }
    }
    private void showCalendar(final String param){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog dialog = new DatePickerDialog(getContext());
            dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    if(param.equals("From")){
                        fromDate = String.valueOf(dayOfMonth) + getMonth(month) + String.valueOf
                                (year);
                    }
                    else{
                        toDate = String.valueOf(dayOfMonth) + getMonth(month) + String.valueOf
                                (year);
                    }
                }
            });

            dialog.show();
        }
        else{
            final DatePicker picker = new DatePicker(getContext());
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Select " + param + " Date");
            builder.setView(picker);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(param.equals("From")){
                        fromDate = String.valueOf(picker.getDayOfMonth()) + getMonth(picker
                                .getMonth()) + String.valueOf(picker.getYear());

                        mFromDate.setText(
                                String.valueOf(picker.getDayOfMonth()) +
                                " - " + getMonth(picker.getMonth()) +
                                " - " + String.valueOf(picker.getYear())
                        );
                    }
                    else{
                        toDate = String.valueOf(picker.getDayOfMonth()) + getMonth(picker
                                .getMonth()) + String.valueOf(picker.getYear());

                        mToDate.setText(
                                String.valueOf(picker.getDayOfMonth()) +
                                " - " + getMonth(picker.getMonth()) +
                                " - " + String.valueOf(picker.getYear())
                        );
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void fetchDatedSourcesReport() {
        pDialog.show();
        AdmissionSourcesPresenter presenter = new AdmissionSourcesPresenterImpl(this);
        presenter.getAdmissionSourcesByDate(fromDate, toDate);
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
}

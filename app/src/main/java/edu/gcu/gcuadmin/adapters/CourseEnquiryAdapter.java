package edu.gcu.gcuadmin.adapters;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.models.CourseEnquiryModel;

/**
 * Created by Shrivats on 4/18/2017.
 */

public class CourseEnquiryAdapter extends RecyclerView.Adapter<CourseEnquiryAdapter.ViewHolder> {

    private ArrayList<CourseEnquiryModel> ceModel;
    private ArrayList<CourseEnquiryModel> ceTodayModel;
    private ArrayList<CourseEnquiryModel> ceLastWeekModel;
    int totalEnquiry = 0;
    int totalEnrolled = 0;
    int totalEnquiryToday = 0;
    int totalEnrolledtoday = 0;
    int totalEnquiryWeek = 0;
    int totalEnrolledWeek = 0;

    public CourseEnquiryAdapter(ArrayList<CourseEnquiryModel> ceModel, ArrayList<CourseEnquiryModel> ceTodayModel, ArrayList<CourseEnquiryModel> ceLastWeekModel) {
        this.ceModel = ceModel;
        this.ceTodayModel = ceTodayModel;
        this.ceLastWeekModel = ceLastWeekModel;
        /*if(ceModel.size()>ceTodayModel.size() && ceModel.size() > ceLastWeekModel.size()){
            size = ceModel.size();
        }
        else if(ceTodayModel.size() > ceModel.size() && ceTodayModel.size() > ceLastWeekModel.size()){
            size = ceTodayModel.size();
        }
        else{
            size = ceLastWeekModel.size();
        }*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_course_enquiry,
                parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (position != ceModel.size()) {
            CourseEnquiryModel model = ceModel.get(position);

            holder.mCourseName.setText(model.getCourse());
            holder.mEnquiry.setText(model.getTotalenquiry());
            holder.mEnrolled.setText(model.getTotalenrolled());
            totalEnquiry += Integer.parseInt(model.getTotalenquiry());
            totalEnrolled += Integer.parseInt(model.getTotalenrolled());


            try {
                holder.mEnquiryToday.setText(ceTodayModel.get(position).getTotalenquiry());
                holder.mEnrolledToday.setText(ceTodayModel.get(position).getTotalenrolled());
                totalEnquiryToday += Integer.parseInt(ceTodayModel.get(position).getTotalenquiry());
                totalEnrolledtoday += Integer.parseInt(ceTodayModel.get(position).getTotalenrolled());

                holder.mEnquiryLastWeek.setText(ceLastWeekModel.get(position).getTotalenquiry());
                holder.mEnrolledLastWeek.setText(ceLastWeekModel.get(position).getTotalenrolled());
                totalEnquiryWeek += Integer.parseInt(ceLastWeekModel.get(position).getTotalenquiry());
                totalEnrolledWeek += Integer.parseInt(ceLastWeekModel.get(position).getTotalenrolled());
            }
            catch (Exception e){
                holder.mEnquiryToday.setText("0");
                holder.mEnrolledToday.setText("0");
                holder.mEnquiryLastWeek.setText("0");
                holder.mEnrolledLastWeek.setText("0");
            }
        }
        else{
            setTotal(holder);
        }
    }

    private void setTotal(ViewHolder holder) {
        holder.mCourseName.setText("Total");

        holder.mEnquiry.setText(String.valueOf(totalEnquiry));
        holder.mEnquiry.setTypeface(Typeface.DEFAULT_BOLD);

        holder.mEnrolled.setText(String.valueOf(totalEnrolled));
        holder.mEnrolled.setTypeface(Typeface.DEFAULT_BOLD);

        holder.mEnquiryToday.setText(String.valueOf(totalEnquiryToday));
        holder.mEnquiryToday.setTypeface(Typeface.DEFAULT_BOLD);

        holder.mEnrolledToday.setText(String.valueOf(totalEnrolledtoday));
        holder.mEnrolledToday.setTypeface(Typeface.DEFAULT_BOLD);

        holder.mEnquiryLastWeek.setText(String.valueOf(totalEnquiryWeek));
        holder.mEnquiryLastWeek.setTypeface(Typeface.DEFAULT_BOLD);

        holder.mEnrolledLastWeek.setText(String.valueOf(totalEnrolledWeek));
        holder.mEnrolledLastWeek.setTypeface(Typeface.DEFAULT_BOLD);
    }

    @Override
    public int getItemCount() {
        return ceModel.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mCourseName;
        private TextView mEnquiry;
        private TextView mEnrolled;
        private TextView mEnquiryToday;
        private TextView mEnrolledToday;
        private TextView mEnquiryLastWeek;
        private TextView mEnrolledLastWeek;

        ViewHolder(View itemView) {
            super(itemView);
            mCourseName = (TextView) itemView.findViewById(R.id.ce_adapter_name);
            mEnquiry = (TextView) itemView.findViewById(R.id.ce_adapter_total_enquiry);
            mEnrolled = (TextView) itemView.findViewById(R.id.ce_adapter_total_enrolled);
            mEnquiryToday = (TextView) itemView.findViewById(R.id.ce_adapter_total_enquiry_today);
            mEnrolledToday = (TextView) itemView.findViewById(R.id.ce_adapter_total_enrolled_today);
            mEnquiryLastWeek = (TextView) itemView.findViewById(R.id
                    .ce_adapter_total_enquiry_last_week);
            mEnrolledLastWeek = (TextView) itemView.findViewById(R.id
                    .ce_adapter_total_enrolled_last_week);
        }
    }
}

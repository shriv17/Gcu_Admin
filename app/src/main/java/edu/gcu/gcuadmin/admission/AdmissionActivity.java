package edu.gcu.gcuadmin.admission;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.admission.adapter.AdmissionAdapter;
import edu.gcu.gcuadmin.util.MyViewPager;

public class AdmissionActivity extends AppCompatActivity {

    private MyViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);
        initUI();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.admission_toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbar_title);
        title.setText("Admissions Report");
        setSupportActionBar(toolbar);
        try{
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_action_back);
            ab.setDisplayShowTitleEnabled(false);
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        mViewPager = (MyViewPager) findViewById(R.id.admission_viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.admission_tab_layout);
        mViewPager.setPagingEnabled(false);

        AdmissionAdapter admissionAdapter = new AdmissionAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(admissionAdapter);
        admissionAdapter.notifyDataSetChanged();

        mTabLayout.setupWithViewPager(mViewPager);
        try{
            mViewPager.setCurrentItem(getIntent().getExtras().getInt("pager-position", 0));
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if(mViewPager.getCurrentItem() != 0){
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
        }
        else {
            super.onBackPressed();
        }
    }
}

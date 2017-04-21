package edu.gcu.gcuadmin.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.adapters.ExpandableMenuAdapter;
import edu.gcu.gcuadmin.admission.AdmissionActivity;
import edu.gcu.gcuadmin.constants.AppConstants;
import edu.gcu.gcuadmin.login.LoginActivity;
import edu.gcu.gcuadmin.models.ExpandableMenuModel;
import edu.gcu.gcuadmin.util.GenerateMenuItems;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, ExpandableListView.OnGroupClickListener, ExpandableListView
        .OnChildClickListener, TabLayout.OnTabSelectedListener{

    private DrawerLayout mDrawerLayout;
    private FragmentManager fragmentManager;
    private TextView mNavHeaderName;
    private TextView mNavHeaderDesignation;
    private SharedPreferences sPref;
    private TextView mTitle;
    private FrameLayout mFragmentContainer;
    Toolbar toolbar;
    private int BACK_PRESSED_COUNT = 0;
    private List<ExpandableMenuModel> mListDataHeader;
    private HashMap<ExpandableMenuModel,List<String>> mListDataChild;
    private TextView mLabelUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState)throws NullPointerException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeUI();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    private void initializeUI(){
        toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeAsUpIndicator(R.drawable.ic_toolbar_menu);
            ab.setTitle("");
        }

        mFragmentContainer = (FrameLayout) findViewById(R.id.home_fragment_container);
        ExpandableListView mExpandableMenu = (ExpandableListView) findViewById(R.id.home_expandable_list);
        NavigationView nav_drawer = (NavigationView) findViewById(R.id.home_navigation_drawer);
        if(nav_drawer != null){
            nav_drawer.setNavigationItemSelectedListener(this);
        }
        prepareListData();
        ExpandableMenuAdapter mExpandableAdapter = new ExpandableMenuAdapter(this, mListDataHeader, mListDataChild,
                mExpandableMenu);
        mExpandableMenu.setAdapter(mExpandableAdapter);

        mExpandableMenu.setOnGroupClickListener(this);
        mExpandableMenu.setOnChildClickListener(this);



        mDrawerLayout = (DrawerLayout) findViewById(R.id.home_drawer);
        View mNavHeader = nav_drawer != null ? nav_drawer.getHeaderView(0) : null;
        mNavHeaderName = (TextView) (mNavHeader != null ? mNavHeader.findViewById(R.id.drawer_header_name) : null);
        mNavHeaderDesignation = (TextView) (mNavHeader != null ? mNavHeader.findViewById(R.id.drawer_header_designation) : null);
        ImageView mNavHeaderPic = (ImageView) (mNavHeader != null ? mNavHeader.findViewById(R.id.drawer_header_pic) : null);
        if (mNavHeaderPic != null) {
            mNavHeaderPic.setOnClickListener(this);
        }

        LinearLayout mAdmissionsCard = (LinearLayout) findViewById(R.id.home_menu_admissions);
        mAdmissionsCard.setOnClickListener(this);
        LinearLayout mHostelCard = (LinearLayout) findViewById(R.id.home_menu_hostel);
        mHostelCard.setOnClickListener(this);
        mLabelUserName = (TextView) findViewById(R.id.home_user_name);

    }

    private void prepareListData() {
        mListDataHeader = new ArrayList<>();
        mListDataChild = new HashMap<>();
        GenerateMenuItems menuItems = new GenerateMenuItems();
        mListDataHeader = menuItems.getMenuModel();
        mListDataChild = menuItems.getSubMenuMap();
    }

    @Override
    protected void onStart()throws NullPointerException {
        super.onStart();
        runOnUiThread(getSharedPrefDetails);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.toolbar_sign_out:
                logout();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.drawer_header_pic:
                mDrawerLayout.closeDrawers();
                //setHomeFragment(ViewProfileFragment.newInstance("", ""));
                break;
            case R.id.home_menu_admissions:
                startActivity(new Intent(this, AdmissionActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.home_menu_hostel:
                Toast.makeText(this, "Under construction", Toast.LENGTH_SHORT).show();
                break;
        }
    }


     public void setHomeFragment(Fragment frag) {
         mFragmentContainer.setVisibility(View.VISIBLE);
         FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
         fragmentTransaction.add(R.id.home_fragment_container, frag);
         fragmentTransaction.addToBackStack(null);
         fragmentTransaction.commit();

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    Runnable getSharedPrefDetails = new Runnable() {
        @Override
        public void run() {
            sPref = AppConstants.getUserSession(getApplicationContext());
            initSession();
        }
    };

    private void initSession() {
        //todo set user details
    }

    private void logout(){
        AlertDialog.Builder lCD = new AlertDialog.Builder(this);
        lCD.setIcon(R.drawable.ic_action_save_alt);
        lCD.setTitle("Logout Confirmation");
        lCD.setMessage("Are you sure you want to logout ?");
        lCD.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor spEdit = sPref.edit();
                spEdit.clear();
                spEdit.commit();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        lCD.setNegativeButton("NO", null);
        lCD.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        switch (BACK_PRESSED_COUNT){
            case 0:
                Toast.makeText(this, "Press again to exit.", Toast.LENGTH_LONG).show();
                BACK_PRESSED_COUNT++;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        BACK_PRESSED_COUNT = 0;
                    }
                }, 2500);
                break;
            case 1:
                BACK_PRESSED_COUNT = 0;
                super.onBackPressed();
                finish();
                System.exit(0);
                break;
        }
        /*switch (BACK_PRESSED_COUNT){
            case 0:

                if(getSupportFragmentManager().getBackStackEntryCount() > 0){
                    getSupportFragmentManager().popBackStackImmediate();
                    }

                else if(mViewPager.getCurrentItem() != 0){
                    mFragmentContainer.setVisibility(View.GONE);
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem()-1);
                    }
                else{
                    mFragmentContainer.setVisibility(View.GONE);
                    *//*adapter = new AdmissionAdapter(this,
                            getSupportFragmentManager());*//*
                    mViewPager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    ++BACK_PRESSED_COUNT;
                }
                break;
            case 1:
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                ++BACK_PRESSED_COUNT;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                         BACK_PRESSED_COUNT = 0;
                    }
                }, 2000);
                break;
            case 2:
                super.onBackPressed();
                BACK_PRESSED_COUNT = 0;
                finish();
                System.exit(0);
                break;
        }*/
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        switch(groupPosition){
            case 0:
                startActivity(new Intent(this, HomeActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                break;
        }
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        mDrawerLayout.closeDrawers();
        switch (String.valueOf(groupPosition) + String.valueOf(childPosition)){
            case "10":
                startActivity(new Intent(HomeActivity.this, AdmissionActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case "11":
                startActivity(new Intent(HomeActivity.this, AdmissionActivity.class).putExtra
                        ("pager-position", 1));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case "12":
                startActivity(new Intent(HomeActivity.this, AdmissionActivity.class).putExtra
                        ("pager-position", 2));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                break;
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                getSupportActionBar().show();
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                getSupportActionBar().hide();
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}


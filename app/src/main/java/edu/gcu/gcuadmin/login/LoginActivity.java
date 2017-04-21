package edu.gcu.gcuadmin.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import edu.gcu.gcuadmin.R;
import edu.gcu.gcuadmin.constants.AppConstants;
import edu.gcu.gcuadmin.home.HomeActivity;
import edu.gcu.gcuadmin.models.login.LoginResponseModel;
import edu.gcu.gcuadmin.login.presenter.LoginPresenter;
import edu.gcu.gcuadmin.login.presenter.LoginPresenterImpl;
import edu.gcu.gcuadmin.login.view.LoginView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private LoginPresenter presenter;
    private TextInputLayout mID;
    private TextInputLayout mPassword;
    private ProgressDialog pDialog;
    SharedPreferences sPref;
    int pagerPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(getIntent() != null){
            pagerPosition = getIntent().getIntExtra("pager-position",0);
        }
        checkLogin();
        mID = (TextInputLayout) findViewById(R.id.login_edit_id);
        mID.setPasswordVisibilityToggleEnabled(false);
        mID.setPasswordVisibilityToggleDrawable(R.drawable.ic_login_user);

        mPassword = (TextInputLayout) findViewById(R.id.login_edit_password);
        mPassword.setPasswordVisibilityToggleDrawable(R.drawable.ic_show_password);
        mPassword.setPasswordVisibilityToggleEnabled(true);
        Button submit = (Button) findViewById(R.id.login_button_submit);
        submit.setOnClickListener(this);
        presenter = new LoginPresenterImpl(this);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Logging In...");
        pDialog.setMessage("Please wait while we check your credentials.");
        pDialog.setIcon(R.drawable.ic_icon_wait);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_button_submit:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                /*pDialog.show();
                startLogin(mID.getEditText().getText().toString(), mPassword.getEditText()
                        .getText().toString());*/
        }
    }
    private void startLogin(String id, String password){
            int isOk = validateInput(id, password);
        // If validation is successful initiate login
        if(isOk != 0){
            presenter.validateUserCredentials(id, password, "password");
        }
        else{
            if(pDialog.isShowing())
                pDialog.dismiss();
        }
    }
    private int validateInput(String id, String password){

            if(id.isEmpty()){
                mID.getEditText().setError("Cannot be empty");
                return 0;
            }
            else if (password.isEmpty()){
                mPassword.setError("Cannot be empty");
                return 0;
            }


        return 1;
    }

    @Override
    public void onCredentialError() {
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
        Snackbar.make(mID, "Can't connect to server. Check your internet or try after some time.",
                2500).show();
    }

    @Override
    public void onLoginSuccessful(LoginResponseModel responseModel) {
        AppConstants.setAccessToken(this, responseModel.getAccessToken());
        if(responseModel.getGroupId().equals("1")){
            //todo start login
        }
        else{
            //todo not authorized
        }
    }


    @Override
    public void onLoginFail() {
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Wrong credentials");
        dialog.setMessage("Please check your username or password and try again");
        dialog.setIcon(R.drawable.ic_prompt_error);
        dialog.show();
        mID.getEditText().setError("Check your staff id");
        mPassword.setError("Check your password");
    }

    private void setError(String s) {
        Snackbar.make(mID, "Can't log you in! Please try again.", 1500).show();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void setSharedPref(){

        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
        startActivity(new Intent(this, HomeActivity.class).setFlags(Intent
                .FLAG_ACTIVITY_NO_ANIMATION).putExtra("pager-position", pagerPosition));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
        finish();
    }

    @Override
    protected void onDestroy() {
        if(pDialog != null){
            pDialog = null;
        }
        super.onDestroy();
    }

    private void checkLogin(){
        sPref = AppConstants.getUserSession(this);
        if(sPref.getBoolean("session_active", false)){
            startActivity(new Intent(this, HomeActivity.class).addFlags(Intent
                    .FLAG_ACTIVITY_NO_ANIMATION).putExtra("pager-position", pagerPosition));
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}

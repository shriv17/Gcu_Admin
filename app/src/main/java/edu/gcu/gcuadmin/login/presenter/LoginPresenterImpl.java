package edu.gcu.gcuadmin.login.presenter;

import android.util.Log;

import edu.gcu.gcuadmin.models.login.LoginRequestModel;
import edu.gcu.gcuadmin.models.login.LoginResponseModel;
import edu.gcu.gcuadmin.login.view.LoginView;

/**
 * Created by Shrivats on 3/24/2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginInteractor.LoginFinishedListener {

    private LoginView loginView;
    private LoginInteractorImpl interactor;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        interactor = new LoginInteractorImpl();
    }

    @Override
    public void validateUserCredentials(String id, String password, String grant_type) {
        LoginRequestModel requestModel = new LoginRequestModel(id, password, grant_type);
        Log.i("startedLogin", "Login process started"+requestModel.toString());
        interactor.login(requestModel, this);
    }

    @Override
    public void onSuccess(LoginResponseModel responseModel) {
            Log.i("success", "Successful login"+responseModel.toString());
            loginView.onLoginSuccessful(responseModel);
    }

    @Override
    public void onError(LoginResponseModel model) {
            loginView.onLoginFail();
    }

    @Override
    public void onServerError() {
        loginView.onCredentialError();
    }

}

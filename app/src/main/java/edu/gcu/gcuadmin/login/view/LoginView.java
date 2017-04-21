package edu.gcu.gcuadmin.login.view;

import edu.gcu.gcuadmin.models.login.LoginResponseModel;

/**
 * Created by Shrivats on 3/24/2017.
 */

public interface LoginView {
    void onCredentialError();
    void onLoginSuccessful(LoginResponseModel responseModel);
    void onLoginFail();
}

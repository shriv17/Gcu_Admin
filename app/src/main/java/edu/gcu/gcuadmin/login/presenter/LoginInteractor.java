package edu.gcu.gcuadmin.login.presenter;

import edu.gcu.gcuadmin.models.login.LoginRequestModel;
import edu.gcu.gcuadmin.models.login.LoginResponseModel;

/**
 * Created by Shrivats on 3/24/2017.
 */

public interface LoginInteractor {

    interface LoginFinishedListener{
        void onSuccess(LoginResponseModel responseModel);
        void onError(LoginResponseModel model);
        void onServerError();
    }
    void login(LoginRequestModel request, LoginFinishedListener finishedListener);
}

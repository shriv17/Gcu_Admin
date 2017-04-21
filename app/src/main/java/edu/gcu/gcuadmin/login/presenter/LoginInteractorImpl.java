package edu.gcu.gcuadmin.login.presenter;

import android.os.AsyncTask;

import edu.gcu.gcuadmin.models.login.LoginRequestModel;
import edu.gcu.gcuadmin.models.login.LoginResponseModel;
import edu.gcu.gcuadmin.z_request_manager.RequestManager;
import edu.gcu.gcuadmin.z_request_manager.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Shrivats on 3/24/2017.
 */

class LoginInteractorImpl implements LoginInteractor{

    @Override
    public void login(LoginRequestModel request, LoginFinishedListener finishedListener) {
        LoginCall loginCall = new LoginCall(request, finishedListener);
        loginCall.execute();
    }

    private class LoginCall extends AsyncTask<Void, Void, Void>{

        LoginRequestModel request;
        LoginInteractor.LoginFinishedListener loginFinishedListener;

        LoginCall(LoginRequestModel request, LoginInteractor.LoginFinishedListener loginFinishedListener) {
            this.request = request;

            this.loginFinishedListener = loginFinishedListener;
        }

        @Override
        protected Void doInBackground(Void... params) {
            RequestManager manager = ServiceGenerator.getLoginWebService();

            Call<LoginResponseModel> call = manager.login(request.getUsername(), request
                    .getPassword(), "password");

            call.enqueue(new Callback<LoginResponseModel>() {
                @Override
                public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                    LoginResponseModel responseModel = response.body();

                    if(responseModel != null){
                        loginFinishedListener.onSuccess(responseModel);
                    }
                    else{
                        loginFinishedListener.onError(responseModel);
                    }
                }

                @Override
                public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                        loginFinishedListener.onServerError();
                }
            });
            return null;
        }
    }


}

package edu.gcu.gcuadmin.login.presenter;

/**
 * Created by Shrivats on 3/24/2017.
 */

public interface LoginPresenter {
    void validateUserCredentials(String id, String password, String grant_type);

}

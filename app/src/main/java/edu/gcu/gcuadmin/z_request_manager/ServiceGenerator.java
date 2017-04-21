package edu.gcu.gcuadmin.z_request_manager;

import java.util.concurrent.TimeUnit;

import edu.gcu.gcuadmin.constants.UrlConstants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shrivats on 3/24/2017.
 */

public class ServiceGenerator {

    private static RequestManager getRestService(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);

        Retrofit.Builder restBuilder = new Retrofit.Builder().baseUrl(UrlConstants.BASE_URL).client(httpClient
                .build()).addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = restBuilder.build();

        return retrofit.create(RequestManager.class);
    }
    private static RequestManager getLoginService(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);
        Retrofit.Builder restBuilder = new Retrofit.Builder().baseUrl("http://111.93.128.70:8031/")
                .client(httpClient
                .build()).addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = restBuilder.build();

        return retrofit.create(RequestManager.class);
    }

    public static RequestManager getRestWebService(){
        return getRestService();
    }

    public static RequestManager getLoginWebService(){
        return getLoginService();
    }
}

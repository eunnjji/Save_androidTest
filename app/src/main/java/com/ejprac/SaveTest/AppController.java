package com.ejprac.SaveTest;

import android.app.Application;
import android.util.Log;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.ejprac.SaveTest.NetworkService;
/**
 * 네트워크 코드 작성
 * */

public class AppController extends Application {

    private static AppController instance;
    public static AppController getInstance(){return instance;};


    @Override
    public void onCreate(){
        super.onCreate();
        AppController.instance = this;
    }

    private  NetworkService networkService;
    public NetworkService getNetworkService() {return networkService;}

    private  String baseUrl;

    public void buildNetworkService(String url){
        synchronized (AppController.class){
            if(networkService==null){
                baseUrl = url;
                //baseUrl = "155.230.235.198:8000";
                Log.d("tag","연결!");
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                networkService = retrofit.create(NetworkService.class);
            }
        }
    }


}

package com.example.spacexships;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.spacexships.logger.AndroidLogger;
import com.example.spacexships.logger.Logger;
import com.example.spacexships.model.SpaceXService;
import com.example.spacexships.model.db.AppDB;
import com.example.spacexships.model.db.ShipDao;
import com.example.spacexships.model.network.SpaceXApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class App extends Application {
    private static final String BASE_URL = "https://api.spacexdata.com";
    private ViewModelProvider.Factory viewModelFactory;
    @Override
    public void onCreate() {
        super.onCreate();
        Logger logger = new AndroidLogger();
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        SpaceXApi spaceXApi =  retrofit.create(SpaceXApi.class);

        ExecutorService  executorService = Executors.newCachedThreadPool();

        AppDB appDB = Room.databaseBuilder(this, AppDB.class, "database.db")
                .build();
        ShipDao shipDao = appDB.getShipDao();
        SpaceXService spaceXService = new SpaceXService(spaceXApi,shipDao,executorService,logger);
        viewModelFactory = new ViewModelFactory(spaceXService);
    }

    public ViewModelProvider.Factory getViewModelFactory(){
        return  viewModelFactory;
    }
}

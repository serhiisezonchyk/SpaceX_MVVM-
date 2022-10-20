package com.example.spacexships.details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spacexships.BaseViewModel;
import com.example.spacexships.model.Callback;
import com.example.spacexships.model.Cancellable;
import com.example.spacexships.model.Result;
import com.example.spacexships.model.Ship;
import com.example.spacexships.model.SpaceXService;

public class DetailsViewModel extends BaseViewModel {
    private MutableLiveData<Result<Ship>> shipLiveData = new MutableLiveData<>();
    {
        shipLiveData.setValue(Result.empty());
    }
    private Cancellable cancellable;
    public DetailsViewModel(SpaceXService spaceXService) {
        super(spaceXService);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if(cancellable!=null){
            cancellable.cancel();
        }
    }

    public void loadShipById(String ship_id){
        shipLiveData.setValue(Result.loading());
        cancellable = getSpaceXService().getShipById(ship_id, new Callback<Ship>() {
            @Override
            public void onError(Throwable error) {
                shipLiveData.postValue(Result.error(error));
            }

            @Override
            public void onResult(Ship data) {
                shipLiveData.postValue(Result.success(data));
            }
        });
    }

    public LiveData<Result<Ship>> getResults(){
        return  shipLiveData;
    }
}

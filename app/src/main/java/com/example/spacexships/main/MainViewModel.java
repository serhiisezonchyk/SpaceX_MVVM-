package com.example.spacexships.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.spacexships.BaseViewModel;
import com.example.spacexships.model.Callback;
import com.example.spacexships.model.Cancellable;
import com.example.spacexships.model.Ship;
import com.example.spacexships.model.Result;
import com.example.spacexships.model.SpaceXService;

import java.util.List;

public class MainViewModel extends BaseViewModel {
    private Result<List<Ship>> shipsResult = Result.empty();

    private MutableLiveData<Result<Ship>> shipLiveData = new MutableLiveData<>();
    {
        shipLiveData.setValue(Result.empty());
    }

    private MutableLiveData<ViewState> stateLiveData  = new MutableLiveData<>();
    {
        updateViewState(Result.empty());
    }

    private Cancellable cancellable;

    @Override
    protected void onCleared() {
        super.onCleared();
        if(cancellable!=null) cancellable.cancel();
    }

    public MainViewModel(SpaceXService spaceXService) {
        super(spaceXService);
    }

    public LiveData<ViewState> getViewState(){
        return stateLiveData;
    }
    public  void getShips(){
            updateViewState(Result.loading());
            cancellable = getSpaceXService().getShips("", new Callback<List<Ship>>() {
                @Override
                public void onError(Throwable error) {
                    if (shipsResult.getStatus() != Result.Status.SUCCESS)
                        updateViewState(Result.error(error));
                }
                @Override
                public void onResult(List<Ship> data) {
                    updateViewState(Result.success(data));
                }
            });

    }
    private void updateViewState(Result<List<Ship>> shipsResult ){
        this.shipsResult = shipsResult;
        ViewState state = new ViewState();
        state.enableSearchButton = shipsResult.getStatus()!=Result.Status.LOADING;
        state.showList = shipsResult.getStatus()== Result.Status.SUCCESS;
        state.showEmptyHint = shipsResult.getStatus()== Result.Status.EMPTY;
        state.showError = shipsResult.getStatus() == Result.Status.ERROR;
        state.showProgress = shipsResult.getStatus() == Result.Status.LOADING;
        state.ships = shipsResult.getData();

        stateLiveData.postValue(state);
    }
    static class ViewState{
        private boolean enableSearchButton;
        private boolean showList;
        private boolean showError;
        private boolean showEmptyHint;
        private boolean showProgress;
        private List<Ship> ships;
        public boolean isEnableSearchButton() {
            return enableSearchButton;
        }

        public boolean isShowList() {
            return showList;
        }

        public boolean isShowError() {
            return showError;
        }

        public boolean isShowEmptyHint() {
            return showEmptyHint;
        }

        public boolean isShowProgress() {
            return showProgress;
        }

        public List<Ship> getShips() {
            return ships;
        }

    }

}

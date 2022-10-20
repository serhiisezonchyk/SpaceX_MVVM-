package com.example.spacexships;

import androidx.lifecycle.ViewModel;

import com.example.spacexships.model.SpaceXService;

public class BaseViewModel extends ViewModel {
    private SpaceXService spaceXService;

    protected final SpaceXService getSpaceXService() {
        return spaceXService;
    }

    public BaseViewModel(SpaceXService spaceXService) {
        this.spaceXService = spaceXService;
    }
}

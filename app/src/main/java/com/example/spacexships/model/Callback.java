package com.example.spacexships.model;

public interface Callback<T> {
    void onError(Throwable error);
    void onResult(T data);
}

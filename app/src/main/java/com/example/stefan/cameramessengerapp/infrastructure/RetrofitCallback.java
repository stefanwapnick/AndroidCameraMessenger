package com.example.stefan.cameramessengerapp.infrastructure;

import android.util.Log;

import com.example.stefan.cameramessengerapp.services.ServiceResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class RetrofitCallback<T extends ServiceResponse> implements Callback<T> {

    private static final String TAG = "RetrofitCallback";
    protected final Class<T> resultType;

    public RetrofitCallback(Class<T> resultType){
        this.resultType = resultType;
    }

    protected abstract void onResponse(T t);

    @Override
    public void success(T t, Response response) {
        onResponse(t);
    }

    @Override
    public void failure(RetrofitError error) {
        Log.e(TAG, "Error sending request with " + resultType.getName() + " response", error);

        ServiceResponse errorResult;

        try{
            errorResult = resultType.newInstance();
        }catch(Exception e){
            throw new RuntimeException("Error creating result type " + resultType.getName(), e);
        }

        if(error.getKind() == RetrofitError.Kind.NETWORK){
            errorResult.setCriticalError("Unknown error. Please try again");
            onResponse((T) errorResult);
            return;
        }

        try{
            if(error.getBody() instanceof ServiceResponse){
                ServiceResponse result = (ServiceResponse) error.getBody();

                if(result.isSuccess()){
                    result.setCriticalError("Unknown error. Please try again");
                }

                onResponse((T) result);
            }else{
                throw new RuntimeException("Result class " + resultType.getName() + " does not extend ServiceResponse");
            }

        }catch (Exception e){
            Log.e(TAG, "Unknown error", e);
            errorResult.setCriticalError("Unknown error. Please try again");
            onResponse((T)errorResult);
        }
    }

}

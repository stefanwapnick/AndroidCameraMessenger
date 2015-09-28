package com.example.stefan.cameramessengerapp.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * Contains error messages transmitted back with OTTO service response
 */
public abstract class ServiceResponse {

    private static final String TAG = "ServiceResponse";

    // Error on operation wide scale (ex: improper format image)
    @SerializedName("operationError")
    private String operationError;

    // Validation error per property (email too long, etc)
    @SerializedName("propertyErrors")
    private HashMap<String, String > propertyErrors;

    // Indicates if operationError is critical (API is down, code contains a bug)
    // If web server down: operationError = "Server down", isCritical = true
    private boolean isCritical;

    public ServiceResponse(){
        propertyErrors = new HashMap<>();
    }
    public ServiceResponse(String operationError){
        this.operationError = operationError;
    }

    public ServiceResponse(String operationError, boolean isCritical){
        this.operationError = operationError;
        this.isCritical = isCritical;
    }

    public String getOperationError() {
        return operationError;
    }
    public void setOperationError(String operationError) {
        this.operationError = operationError;
    }
    public boolean isCritical() {
        return isCritical;
    }
    public void setIsCritical(boolean isCritical) {
        this.isCritical = isCritical;
    }
    public void setPropertyError(String property, String error){
        this.propertyErrors.put(property, error);
    }
    public void setCriticalError(String criticalError){
        this.operationError = criticalError;
        isCritical = true;
    }

    public String getPropertyError(String property){
        return this.propertyErrors.get(property);
    }
    public boolean isSuccess(){
        return (operationError == null || operationError.isEmpty()) && (propertyErrors.size() == 0);
    }

    public void showErrorToast(Context context){

        if(context == null || operationError == null || propertyErrors == null)
            return;

        try{
            Toast.makeText(context, operationError, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Log.e(TAG, "Can't create error toast", e);
        }

    }

}

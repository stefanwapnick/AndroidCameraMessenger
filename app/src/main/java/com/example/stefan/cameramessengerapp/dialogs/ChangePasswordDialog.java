package com.example.stefan.cameramessengerapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.services.Account;
import com.squareup.otto.Subscribe;

public class ChangePasswordDialog extends BaseDialogFragment implements View.OnClickListener {

    private EditText currentPassword;
    private EditText newPassword;
    private EditText connfirmNewPassword;
    private Dialog progressDialog;


    @Override
    public Dialog onCreateDialog(Bundle savedState){
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_password, null, false);

        currentPassword = (EditText)dialogView.findViewById(R.id.dialog_change_password_currentPassword);
        newPassword = (EditText)dialogView.findViewById(R.id.dialog_change_password_newPassword);
        connfirmNewPassword = (EditText)dialogView.findViewById(R.id.dialog_change_password_confirmNewPassword);

        if(!application.getAuth().getUser().isHasPassword()){
            currentPassword.setVisibility(View.GONE);
        }


        // Alert Dialog Builder used to create dialog
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(dialogView)
                .setPositiveButton("Update", null)
                .setNegativeButton("Cancel", null)
                .setTitle("Change Password")
                .show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(this);

        return dialog;
    }

    @Override
    public void onClick(View v) {

        progressDialog = new ProgressDialog.Builder(getActivity())
                .setTitle("Changing Password")
                .setCancelable(false)
                .show();

        bus.post(new Account.ChangePasswordRequest(
                currentPassword.getText().toString(),
                newPassword.getText().toString(),
                connfirmNewPassword.getText().toString()));

    }

    @Subscribe
    public void onPasswordChanged(Account.ChangePasswordResponse response){

        progressDialog.dismiss();
        progressDialog = null;

        if(response.isSuccess()){
            Toast.makeText(getActivity(), "Password Updated", Toast.LENGTH_LONG).show();
            dismiss();
            application.getAuth().getUser().setHasPassword(true);
            return;
        }

        currentPassword.setError(response.getPropertyError("currentPassword"));
        newPassword.setError(response.getPropertyError("newPassword"));
        connfirmNewPassword.setError(response.getPropertyError("confirmNewPassword"));
    }
}

package com.example.stefan.cameramessengerapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.stefan.cameramessengerapp.R;

/**
 * Created by Stefan on 2015-09-13.
 */
public class ChangePasswordDialog extends BaseDialogFragment implements View.OnClickListener {

    private EditText currentPassword;
    private EditText newPassword;
    private EditText connfirmNewPassword;

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
        // TODO: Send new password to server
        Toast.makeText(getActivity(), "Password updated", Toast.LENGTH_SHORT).show();
        dismiss();  // dismiss this dialog fragment
    }
}

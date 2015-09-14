package com.example.stefan.cameramessengerapp.activities;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;



import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.dialogs.ChangePasswordDialog;
import com.example.stefan.cameramessengerapp.infrastructure.User;
import com.example.stefan.cameramessengerapp.views.NavDrawer;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseAuthenticatedActivity implements View.OnClickListener{

    private static final int REQUEST_SELECT_IMAGE = 100;

    private static final int STATE_VIEWING = 1;
    private static final int STATE_EDITING = 2;
    private static final String BUNDLE_STATE = "BUNDLE_STATE";
    private int currentState;
    private ActionMode editProfileActionMode;

    private EditText displayNameEdit;
    private EditText emailEdit;

    private View changeAvatarButton;
    private ImageView avatarView;
    private View avatarProgressFrame;       // Used for temporary storage of pictures taken by phone for profile image
    private File tempOutputFile;

    @Override
    protected void onCreateAuth(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        setNavDrawer(new NavDrawer(this));

        avatarView = (ImageView)findViewById(R.id.activity_profile_avatar);
        avatarProgressFrame = findViewById(R.id.activity_profile_avatarProgressFrame);
        changeAvatarButton = findViewById(R.id.activity_profile_changeAvatar);
        displayNameEdit = (EditText)findViewById(R.id.activity_profile_displayName);
        emailEdit = (EditText)findViewById(R.id.activity_profile_email);


        // getExternalCacheDir() = returns special temp directory
        // arguments = directory, file name
        tempOutputFile = new File(getExternalCacheDir(), "temp-image.jpg");

        avatarView.setOnClickListener(this);
        changeAvatarButton.setOnClickListener(this);

        avatarProgressFrame.setVisibility(View.GONE);           // Hide progress frame

        User user = application.getAuth().getUser();
        setToolbarTitle(user.getDisplayName());

        if(savedState == null){
            displayNameEdit.setText(user.getDisplayName());
            emailEdit.setText(user.getEmail());
            changeState(STATE_VIEWING);
        }else{
            changeState(savedState.getInt(BUNDLE_STATE));
        }
    }

    // Save current state on screen rotation
    @Override
    public void onSaveInstanceState(Bundle savedState){
        super.onSaveInstanceState(savedState);
        savedState.putInt(BUNDLE_STATE, currentState);
    }


    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if(viewId == R.id.activity_profile_changeAvatar || viewId == R.id.activity_profile_avatar){
            changeAvatar();
        }

    }

    private void changeAvatar() {

        // Get list of all intents capable of taking an image
        List<Intent> otherImageCaptureIntents = new ArrayList<>();

        List<ResolveInfo> otherImageCaptureActivities = getPackageManager()
                .queryIntentActivities(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);

        for(ResolveInfo info : otherImageCaptureActivities){
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Setup intents of other activities which can take an image
            captureIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempOutputFile));

            // Place the queried intent into our collection
            otherImageCaptureIntents.add(captureIntent);
        }

        Intent selectImageIntent = new Intent(Intent.ACTION_PICK);

        // Tell OS to go find activities can pick images out of
        selectImageIntent.setType("image/*");

        Intent chooser = Intent.createChooser(selectImageIntent, "Chooser Avatar");
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, otherImageCaptureIntents.toArray(new Parcelable[otherImageCaptureActivities.size()]));

        startActivityForResult(chooser, REQUEST_SELECT_IMAGE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        if(resultCode != RESULT_OK){
            tempOutputFile.delete();
            return;
        }

        // Image selection activity return
        //-----------------------------------------
        if(requestCode == REQUEST_SELECT_IMAGE){
            Uri outputFile;

            // Load temp file contents
            Uri tempFileUri = Uri.fromFile(tempOutputFile);

            if(data != null && (data.getAction() != null || !data.getAction().equals(MediaStore.ACTION_IMAGE_CAPTURE))){
                // If user selected image off device
                outputFile = data.getData();
            }else{
                // Otherwise user took a picture which is in our temp file
                outputFile = tempFileUri;
            }

            new Crop(outputFile).asSquare().output(tempFileUri).start(this);


        // Cropping Image activity return
        //-----------------------------------------
        }else if (requestCode == Crop.REQUEST_CROP){
            //TODO: Send tempFIleUri to server as new avatar
            avatarView.setImageResource(0);         // set image resource to 0 first to make smooth transition to new image when setting ImageView
            avatarView.setImageURI(Uri.fromFile(tempOutputFile));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();

        if(itemId == R.id.activity_profile_menuEdit){
            changeState(STATE_EDITING);
            return true;
        }else if(itemId == R.id.activity_profile_changePassword){
            FragmentTransaction transaction = getFragmentManager().beginTransaction().addToBackStack(null);

            ChangePasswordDialog dialog = new ChangePasswordDialog();
            dialog.show(transaction, null);
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if (state == currentState)
            return;

        currentState = state;

        if (state == STATE_VIEWING) {
            displayNameEdit.setEnabled(false);
            emailEdit.setEnabled(false);
            changeAvatarButton.setVisibility(View.VISIBLE);

            if (editProfileActionMode != null) {
                editProfileActionMode.finish();
                editProfileActionMode = null;
            }
        } else if (state == STATE_EDITING) {
            displayNameEdit.setEnabled(true);
            emailEdit.setEnabled(true);
            changeAvatarButton.setVisibility(View.GONE);

            editProfileActionMode = toolbar.startActionMode(new EditProfileActionCallback());
        } else {
            throw new IllegalArgumentException("Invalid state " + state);
        }

    }

    private class EditProfileActionCallback implements ActionMode.Callback{
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.activity_profile_edit, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int itemId = item.getItemId();

            if(itemId == R.id.activity_profile_edit_menuDone){
                // TODO: Send request to update display name and email
                User user = application.getAuth().getUser();
                user.setDisplayName(displayNameEdit.getText().toString());
                user.setEmail(emailEdit.getText().toString());

                changeState(STATE_VIEWING);
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(currentState != STATE_VIEWING){
                changeState(STATE_VIEWING);
            }
        }
    }


}

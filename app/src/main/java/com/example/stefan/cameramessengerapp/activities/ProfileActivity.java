package com.example.stefan.cameramessengerapp.activities;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.stefan.cameramessengerapp.R;
import com.example.stefan.cameramessengerapp.views.NavDrawer;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends BaseAuthenticatedActivity implements View.OnClickListener{

    private static final int REQUEST_SELECT_IMAGE = 100;

    private ImageView avatarView;
    private View avatarProgressFrame;
    // Used for temporary storage of pictures taken by phone for profile image
    private File tempOutputFile;

    @Override
    protected void onCreateAuth(Bundle savedState) {
        setContentView(R.layout.activity_profile);
        setNavDrawer(new NavDrawer(this));

        avatarView = (ImageView)findViewById(R.id.activity_profile_avatar);
        avatarProgressFrame = findViewById(R.id.activity_profile_avatarProgressFrame);

        // getExternalCacheDir() = returns special temp directory
        // arguments = directory, file name
        tempOutputFile = new File(getExternalCacheDir(), "temp-image.jpg");

        avatarView.setOnClickListener(this);
        findViewById(R.id.activity_profile_changeAvatar).setOnClickListener(this);

        // Hide progress frame
        avatarProgressFrame.setVisibility(View.GONE);
    }


    @Override
    public void onClick(View view) {

        int viewId = view.getId();

        if(viewId == R.id.activity_profile_changeAvatar || viewId == R.id.activity_profile_avatar){
            changeAvatar();
        }else{

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
        }else if (requestCode == Crop.REQUEST_CROP){
            //TODO: Send tempFIleUri to server as new avatar
            avatarView.setImageResource(0);         // set image resource to 0 first to make smooth transition to new image when setting ImageView
            avatarView.setImageURI(Uri.fromFile(tempOutputFile));
        }

    }
}

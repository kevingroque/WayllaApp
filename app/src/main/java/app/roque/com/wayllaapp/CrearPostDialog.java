package app.roque.com.wayllaapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;

import static android.app.Activity.RESULT_OK;

public class CrearPostDialog extends DialogFragment implements View.OnClickListener{

    private static final int RC_PHOTO_PICKED = 1;
    private ProgressDialog mProgressDialog;
    private Uri mSelectedUri;
    private ImageView mPostDisplay;
    private View mRootView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mProgressDialog = new ProgressDialog(getContext());

        mRootView = getActivity().getLayoutInflater().inflate(R.layout.crear_post_dialog, null);
        mPostDisplay = (ImageView)mRootView.findViewById(R.id.img_post_display_dialog);
        mRootView.findViewById(R.id.img_post_send_dialog).setOnClickListener(this);
        mRootView.findViewById(R.id.img_post_select_image_dialog).setOnClickListener(this);
        builder.setView(mRootView);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_post_send_dialog:
                sendPost();
                break;
            case R.id.img_post_select_image_dialog:
                selectImage();
                break;

        }
    }

    private void sendPost() {
        mProgressDialog.setMessage("Enviando Post...");
        mProgressDialog.setCancelable(true);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Completa la accioón usando"),RC_PHOTO_PICKED);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKED && resultCode == RESULT_OK){
            mSelectedUri = data.getData();
            mPostDisplay.setImageURI(mSelectedUri);
        }
    }
}

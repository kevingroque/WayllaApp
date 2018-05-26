package app.roque.com.wayllaapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.Utils.Permissions;

public class CrearPostActivity extends AppCompatActivity {

    private static final String TAG = "CrearPostActivity";
    private static final int GALLERY_REQUEST_CODE = 1;
    private static final int VERIFY_PERMISSIONS_REQUEST = 1;

    private ImageView mSelectImage;
    private EditText mPostTitle;
    private EditText mPostDescripcion;
    private Button mBTNPublicar;
    private Uri mImageUri = null;
    private ProgressDialog mProgress;

    private StorageReference mStorage;
    private DatabaseReference mDatabaseReference;

    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_post);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("carrete");


        mSelectImage = (ImageView) findViewById(R.id.imageView_photo_crear_post);
        mPostTitle = (EditText)findViewById(R.id.editText_title_crear_post);
        mPostDescripcion = (EditText)findViewById(R.id.editText_descripcion_crear_post);
        mBTNPublicar = (Button)findViewById(R.id.btn_crear_post);

        mProgress = new ProgressDialog(this);

        if(checkPermissionsArray(Permissions.PERMISSIONS)){

            mSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent galleryInten = new Intent(Intent.ACTION_GET_CONTENT);
                    galleryInten.setType("image/*");
                    startActivityForResult(galleryInten,GALLERY_REQUEST_CODE);
                }
            });

            mBTNPublicar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startPosting();
                }
            });
        }else{
            verifyPermissions(Permissions.PERMISSIONS);
        }
    }

    private void startPosting() {

        mProgress.setMessage("Publicando...");
        mProgress.show();
        mProgress.setCancelable(false);

        String title = mPostTitle.getText().toString().trim();
        final String descripcion = mPostDescripcion.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(descripcion) && mImageUri != null){

            FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
            final FirebaseUser user = mFirebaseAuth.getCurrentUser();

            StorageReference filePath = mStorage.child("posts/"+mImageUri.getLastPathSegment());
            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    final Uri  downloadUrl = taskSnapshot.getDownloadUrl();
                    final DatabaseReference post = mDatabaseReference.push();

                    Date date = new Date();
                    String stringTime = DateFormat.getTimeInstance().format(date);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
                    String dateInString = "31-08-1982";
                    String stringDate =  dateFormat.format(date);


                    post.child("user_uid").setValue(user.getUid());
                    post.child("descripcion").setValue(descripcion);
                    post.child("image").setValue(downloadUrl.toString());
                    post.child("username").setValue(user.getDisplayName());
                    post.child("avatar_user").setValue(user.getPhotoUrl().toString());
                    post.child("hora").setValue(stringTime);
                    post.child("fecha").setValue(stringDate);
                    mProgress.dismiss();

                    goPosts();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mProgress.dismiss();
                    Toast.makeText(CrearPostActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            mProgress.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                mSelectImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void verifyPermissions(String[] permissions){
        Log.d(TAG, "verifyPermissions: verifying permissions.");

        ActivityCompat.requestPermissions(
                CrearPostActivity.this,
                permissions,
                VERIFY_PERMISSIONS_REQUEST
        );
    }

    public boolean checkPermissionsArray(String[] permissions){
        Log.d(TAG, "checkPermissionsArray: checking permissions array.");

        for(int i = 0; i< permissions.length; i++){
            String check = permissions[i];
            if(!checkPermissions(check)){
                return false;
            }
        }
        return true;
    }

    public boolean checkPermissions(String permission){
        Log.d(TAG, "checkPermissions: checking permission: " + permission);

        int permissionRequest = ActivityCompat.checkSelfPermission(CrearPostActivity.this, permission);

        if(permissionRequest != PackageManager.PERMISSION_GRANTED){
            Log.d(TAG, "checkPermissions: \n Permission was not granted for: " + permission);
            return false;
        }
        else{
            Log.d(TAG, "checkPermissions: \n Permission was granted for: " + permission);
            return true;
        }
    }

    private void goPosts() {
        Intent intent = new Intent(this, PostActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

package app.roque.com.wayllaapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import app.roque.com.wayllaapp.R;

public class CrearPostActivity extends AppCompatActivity {

    private ImageView mPhoto;
    private ImageButton mSelectPhoto, mSendPhoto;
    private EditText mDescripcion;
    private Uri uri = null;
    private static final int GALLERY_REQUEST_CODE = 2;

    private StorageReference storageReference;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDBReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_post);

        mPhoto = (ImageView)findViewById(R.id.img_post);
        mSelectPhoto = (ImageButton)findViewById(R.id.btn_post_select_image);
        mSendPhoto = (ImageButton)findViewById(R.id.btn_post_send);
        mDescripcion= (EditText)findViewById(R.id.edittext_post_descripcion);

        storageReference = FirebaseStorage.getInstance().getReference();

        mSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(galleryIntent, "Seleccionar la aplicacion"), GALLERY_REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && requestCode == RESULT_OK){
            uri = data.getData();
            mPhoto.setImageURI(uri);
        }
    }
}

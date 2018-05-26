package app.roque.com.wayllaapp.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.models.Usuario;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private TextView nombreToolbar;
    private TextView nombreAppbar;
    private ImageView profilePhoto;
    private GoogleApiClient mGoogleApiClient;
    private FloatingActionButton mBtnSingOut;
    private RoundCornerProgressBar mLevel;
    private TextView mNivel;
    private TextView mMonedas;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDBReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreAppbar = (TextView)findViewById(R.id.txt_collapsing_nombre);
        nombreToolbar = (TextView)findViewById(R.id.txt_nombre_main);
        mNivel = (TextView) findViewById(R.id.txt_nivel_user_main);
        mMonedas = (TextView) findViewById(R.id.txt_monedas_user_main);
        profilePhoto = (ImageView) findViewById(R.id.img_profile_main);
        mBtnSingOut = (FloatingActionButton) findViewById(R.id.fab_logout_main_ac);
        mLevel = (RoundCornerProgressBar) findViewById(R.id.progress_bar_level);


        //#ed3b27 -- rojo
        mLevel.setProgressColor(Color.parseColor("#F6BD00"));
        mLevel.setProgressBackgroundColor(Color.parseColor("#FAFAFA"));
        mLevel.setMax(100);
        mLevel.setProgress(70);

        mDBReference = FirebaseDatabase.getInstance().getReference();

        mBtnSingOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showDialog(v);
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Facebook Login
        if (AccessToken.getCurrentAccessToken() == null){
            goLoginScreen();
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){

                    //Obtener datos del usuario
                    DatabaseReference mUser = mDBReference.child("usuarios").child(mFirebaseAuth.getCurrentUser().getUid());

                    mUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Usuario usuario = dataSnapshot.getValue(Usuario.class);

                            //Mostrar datos del usuario en la interfaz principal
                            mNivel.setText(String.valueOf(usuario.getNivel()));
                            mMonedas.setText(String.valueOf(usuario.getCoins()));
                            nombreToolbar.setText(usuario.getNombre());
                            nombreAppbar.setText(usuario.getNombre());
                            Glide.with(getApplicationContext()).load(usuario.getAvatar()).into(profilePhoto);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //setUserData(user);

                }else {
                    goLoginScreen();
                }
            }
        };


        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("");
        }
    }

    private void setUserData(FirebaseUser user) {
        nombreToolbar.setText(user.getDisplayName());
        nombreAppbar.setText(user.getDisplayName());
        Glide.with(this).load(user.getPhotoUrl()).into(profilePhoto);
    }

    private void goLoginScreen() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthListener);
        }
    }

    //funcion para ir a las secciones
    public void goLomas(View view){
        Intent intent = new Intent(this, LomasInfoActivity.class);
        startActivity(intent);
    }

    public void goPosts(View view){
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void showDialog(final View view){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Cerrar Sesión");
        alertDialog.setMessage("Esta seguro de que quiere cerrar sesion??");
        // Alert dialog button
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OKEY",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mFirebaseAuth.signOut();
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                if(status.isSuccess()){
                                    goLoginScreen();
                                }else{
                                    Toast.makeText(getApplicationContext(), "No se pudo cerrar sesión", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Alert dialog action goes here
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}

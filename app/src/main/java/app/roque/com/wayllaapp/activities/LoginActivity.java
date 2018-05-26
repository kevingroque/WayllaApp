package app.roque.com.wayllaapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import app.roque.com.wayllaapp.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    public static final int SIGN_IN_CODE = 777;

    private GoogleApiClient mGoogleApiClient;
    private SignInButton mSignInButton;

    private CallbackManager mCallbackManager;
    private LoginButton mFacebookButton;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgress = new ProgressDialog(this);

        mCallbackManager = CallbackManager.Factory.create();

        mSignInButton = (SignInButton)findViewById(R.id.btn_signIn_google);
        mFacebookButton = (LoginButton)findViewById(R.id.btn_signIn_facebook);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, LoginActivity.this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();


        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, SIGN_IN_CODE);
            }
        });

        //Login con Facebook
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Inicio de session cancelada", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Ocurrió un error al iniciar sesión", Toast.LENGTH_SHORT).show();
            }
        });


        mFirebaseAuth = FirebaseAuth.getInstance();

        mReference = FirebaseDatabase.getInstance().getReference().child("usuarios");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    checkUserExist();
                }
            }
        };
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Session iniciada ", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_CODE){
            GoogleSignInResult mGoogleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(mGoogleSignInResult);
        }
    }

    private void handleSignInResult(GoogleSignInResult mGoogleSignInResult) {
        if(mGoogleSignInResult.isSuccess()){
            firebaseAuthWithGoogle(mGoogleSignInResult.getSignInAccount());
        }else{
            Toast.makeText(this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount signInAccount) {

        final AuthCredential credential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
        mFirebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Session iniciada ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void checkUserExist(){
        final String user_id = mFirebaseAuth.getCurrentUser().getUid();
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id)){
                    goMainScreen();
                }else {
                    FirebaseUser user = mFirebaseAuth.getCurrentUser();

                    mReference.child(user_id).child("nombre").setValue(user.getDisplayName());
                    mReference.child(user_id).child("avatar").setValue(user.getPhotoUrl().toString());
                    mReference.child(user_id).child("nivel").setValue(0);
                    mReference.child(user_id).child("descripcion").setValue("...");
                    mReference.child(user_id).child("distancia_recorrida").setValue(0);
                    mReference.child(user_id).child("puntaje").setValue(0);
                    mReference.child(user_id).child("coins").setValue(0);
                    goMainScreen();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

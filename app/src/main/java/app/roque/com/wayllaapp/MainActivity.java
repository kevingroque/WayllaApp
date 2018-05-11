package app.roque.com.wayllaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

}

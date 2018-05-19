package app.roque.com.wayllaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;

import app.roque.com.wayllaapp.R;

public class PostActivity extends AppCompatActivity {

    private FloatingActionButton mFabCrearPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mFabCrearPost = (FloatingActionButton)findViewById(R.id.fab_crear_post_dialog);
        mFabCrearPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CrearPostDialog dialog = new CrearPostDialog();
                //dialog.show(getSupportFragmentManager(), null);
                Intent intent = new Intent(PostActivity.this, CrearPostActivity.class);
                startActivity(intent);
            }
        });
    }
}

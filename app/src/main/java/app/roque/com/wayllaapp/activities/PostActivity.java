package app.roque.com.wayllaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.adapters.PostAdapter;
import app.roque.com.wayllaapp.models.Post;

public class PostActivity extends AppCompatActivity {

    private FloatingActionButton mFabCrearPost;
    private RecyclerView recyclerView;
    private List<Post> mPostList;
    private PostAdapter mAdapter;
    private DatabaseReference mReference, mLikeReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_post);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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

        mPostList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference().child("carrete");
        mLikeReference = FirebaseDatabase.getInstance().getReference().child("likes");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPostList.removeAll(mPostList);

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    mPostList.add(post);
                }
                mAdapter = new PostAdapter(getApplicationContext(),mPostList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

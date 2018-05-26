package app.roque.com.wayllaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.models.Post;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder>{

    private Context mContext;
    private List<Post> mPostList;

    public PostAdapter(Context mContext, List<Post> mPostList) {
        this.mContext = mContext;
        this.mPostList = mPostList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post_item, parent,false);
        PostAdapter.PostHolder postHolder = new PostAdapter.PostHolder(v);
        return postHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final PostHolder holder, final int position) {
        final Post post = this.mPostList.get(position);

        holder.descripcion.setText(post.getDescripcion());
        holder.nombre.setText(post.getUsername());
        holder.hora.setText(post.getHora());
        holder.fecha.setText(post.getFecha());
        Glide.with(holder.itemView.getContext()).load(post.getImage()).into(holder.imagenPost);
        Glide.with(holder.itemView.getContext()).load(post.getAvatar_user()).into(holder.imagenPerfil);
    }

    @Override
    public int getItemCount() { 
        return mPostList.size();
    }


    public class PostHolder extends RecyclerView.ViewHolder{

        public TextView descripcion,nombre, fecha, hora, likeCounter, commentsCounter;
        public ImageView imagenPost;
        public ImageView imagenPerfil;
        public ImageView like, comments;

        public DatabaseReference mRef;

        public PostHolder(View itemView) {
            super(itemView);

           nombre = (TextView)itemView.findViewById(R.id.txt_nombre_post_list);
           descripcion = (TextView)itemView.findViewById(R.id.txt_descripcion_post_list);
           fecha = (TextView)itemView.findViewById(R.id.timeTextView);
           hora = (TextView)itemView.findViewById(R.id.dateTextView);
           likeCounter = (TextView)itemView.findViewById(R.id.likeCounterTextView);
           commentsCounter = (TextClock)itemView.findViewById(R.id.commentsCountTextView);
           imagenPost = (ImageView)itemView.findViewById(R.id.img_foto_post_list);
           imagenPerfil = (ImageView)itemView.findViewById(R.id.img_perfil_user_post_list);
           like = (ImageView)itemView.findViewById(R.id.likesImageView);
           comments = (ImageView) itemView.findViewById(R.id.commentsCountImageView);
        }
    }
}

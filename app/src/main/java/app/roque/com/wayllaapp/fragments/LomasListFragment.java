package app.roque.com.wayllaapp.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import app.roque.com.wayllaapp.R;
import app.roque.com.wayllaapp.adapters.LomasListAdapter;
import app.roque.com.wayllaapp.models.Lomas;

public class LomasListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Lomas> mLomasList;
    private LomasListAdapter mAdapter;
    private DatabaseReference mReference;

    public LomasListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lomas_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerLomas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mLomasList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference().child("lomas");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mLomasList.removeAll(mLomasList);
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Lomas lomas = snapshot.getValue(Lomas.class);
                    mLomasList.add(lomas);
                }
                mAdapter = new LomasListAdapter(getContext(),mLomasList);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}

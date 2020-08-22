package com.myhealth.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import com.myhealth.R;
import com.myhealth.ui.home.history.AdaptaMensaje;
import com.myhealth.ui.home.history.History;

import java.time.LocalDateTime;

public class HomeFragment extends AppCompatActivity {

    private HomeViewModel homeViewModel;

    private RecyclerView vistaHistorial;
    private ImageButton btnDown;
    public AdaptaMensaje adapter;

    private FirebaseDatabase database;
    private static FirebaseAuth mAuth;
    private DatabaseReference DBReference;
    private DatabaseReference databaseReference;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private LocalDateTime localDateTime;
    private DatabaseReference mDatabase;
    private static final int PHOTO_SEND = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_home);
        final RecyclerView vistaHistorial = root.findViewById(R.id.vistaHistorial);
        final RecyclerView btnDown = root.findViewById(R.id.btnDown);

        database = FirebaseDatabase.getInstance();
        DBReference = database.getReference("Chats");
        final String id = mAuth.getCurrentUser().getUid();
        databaseReference = DBReference.child(id);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        adapter = new com.myhealth.ui.home.history.AdaptaMensaje(this);
        LinearLayoutManager l = new LinearLayoutManager(this);

        vistaHistorial.setLayoutManager(l);
        vistaHistorial.setAdapter(adapter);

        btnDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vistaHistorial.scrollToPosition(adapter.getItemCount()-1);
            }
        });



        scrollAdapter();



        //////
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                History m = dataSnapshot.getValue(History.class);
                adapter.addMessage(m);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //*/
        scrollSend();

        return root;
    }

    private void scrollAdapter(){
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                scrollSend();
            }
        });
    }

    private void scrollSend(){
        vistaHistorial.scrollToPosition(adapter.getItemCount()-1);
    }
}
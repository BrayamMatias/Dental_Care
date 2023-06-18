package com.example.dentalcare;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dentalcare.modelo.RecyclerViewInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;


import java.util.ArrayList;


public class DiagnosticsFragment extends Fragment implements RecyclerViewInterface {

    ArrayList<DiagUser> newsArrayList;
    Source source = Source.CACHE;

    ArrayList<String> keyList;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference diagnosticosRef = db.collection("Diagnosticos");
    FirebaseAuth auth = FirebaseAuth.getInstance();
    String uid = auth.getUid().toString();
    String documentKey;
    RecyclerView recyclerView;
    Adapter mAdapter;
    public DiagnosticsFragment() {
        // Required empty public constructor
    }

    public static DiagnosticsFragment newInstance() {
        DiagnosticsFragment fragment = new DiagnosticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagnostics, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = getView().findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        keyList = new ArrayList<>();

        newsArrayList = new ArrayList<DiagUser>();
        mAdapter = new Adapter(getActivity(), newsArrayList, this);
        recyclerView.setAdapter(mAdapter);
        mostrarListado();

    }

    public void mostrarListado(){
        diagnosticosRef.whereEqualTo("uid", uid)
                .orderBy(FieldPath.documentId(), Query.Direction.DESCENDING)
                .get(source).addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            DiagUser diagUser = documentSnapshot.toObject(DiagUser.class);
                            String Key = documentSnapshot.getId();
                            keyList.add(Key);
                            diagUser.setUid(diagUser.getUid());
                            newsArrayList.add(diagUser);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

//    private void EventChangeListener() {
//        String uid = auth.getUid().toString();
//        diagnosticosRef.whereEqualTo("nameImg", "64651").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if(error != null){
//                    Log.e("Error en base de datos", error.getMessage());
//                }
//
//                for (DocumentChange dc: value.getDocumentChanges()){
//                    if (dc.getType() == DocumentChange.Type.ADDED) {
//
//                        newsArrayList.add(dc.getDocument().toObject(DiagUser.class));
//
//                    }
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//        });
//    }
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(int position) {
            String selectedKey = keyList.get(position);
            Intent intent = new Intent(getActivity(), activity_VerDiagnostico.class);
            intent.putExtra("key",selectedKey);
            startActivity(intent);
    }
}
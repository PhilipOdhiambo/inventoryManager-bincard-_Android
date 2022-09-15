package com.example.bincard;

import static android.widget.Toast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bincard.model.Bincard;
import com.example.bincard.model.FireStoreDao;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageButton btAdd;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    List<Bincard> bincardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btAdd = findViewById(R.id.main_ibt_add);

        loadData();

        // Set On click listener on Add Image button
        btAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(MainActivity.this,AddEditBincard.class));
             }
         });


    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bincard_list_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bincardsList_aToZ:
                // Sort name ascending
                Collections.sort(bincardList,Bincard.bincardNameAZComparator);
                Toast.makeText(getApplicationContext(),
                        "Sorted inventory A to Z", LENGTH_LONG).show();
                adapter.notifyDataSetChanged();
                return true;


            case R.id.bincardsList_zToa:
                // sort name descending
                Collections.sort(bincardList,Bincard.bincardNameZAComparator);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sort_code_aToz:
                // sort code ascending
                Collections.sort(bincardList,Bincard.bincardCodeAZComparator);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sort_code_zToa:
                // sort code descending
                Collections.sort(bincardList,Bincard.bincardCodeZAComparator);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sort_qty_aToz:
                // sort quantity ascending
                Collections.sort(bincardList,Bincard.bincardQuantityAZComparator);
                adapter.notifyDataSetChanged();
                return true;

            case R.id.sort_qty_zToa:
                // sort quantity descending
                Collections.sort(bincardList,Bincard.bincardQuantityZAComparator);
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

private void loadData() {
        FireStoreDao.getBincardList(this, new FireStoreDao.BicardListCallBack() {
            @Override
            public void onCallback(List<Bincard> list) {
                bincardList = list;
                layoutManager = new LinearLayoutManager(MainActivity.this);
                adapter = new BincardAdapter(MainActivity.this,bincardList);
                recyclerView = findViewById(R.id.main_rv_bincardList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }
        });
}



}
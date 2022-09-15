package com.example.bincard;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bincard.databinding.ActivityAddEditBincardBinding;
import com.example.bincard.model.Bincard;
import com.example.bincard.model.FireStoreDao;
import com.example.bincard.model.StorageDao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.Inflater;

public class AddEditBincard extends AppCompatActivity {

    private Uri imageUri;
    private Bincard mBincard = new Bincard();
    private final String docPath = "inventory/meds1";
    private ActivityAddEditBincardBinding binding;
    private ImageView picture;
    private Button btnAddPicture, btnSaveEdit;
    private FloatingActionButton btnFloating;
    private ProgressBar progressBar;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String imageStorageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEditBincardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        picture = binding.addeditbincardIv;
        btnSaveEdit = binding.addEditBtSave;
        btnFloating = binding.btnFloating;


        btnFloating.setOnClickListener(onUploadImage);


        if (getIntent().hasExtra("bincard")) {
            // Document exists
            Gson gson = new Gson();
            mBincard = gson.fromJson(getIntent().getStringExtra("bincard"),Bincard.class);
            loadDocument();
        }


        binding.addEditBtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String docPath = "inventory/meds1";

                mBincard.setItemCode(binding.addeditbincardEtCode.getText().toString());
                mBincard.setDescription(binding.addeditbincardEtDescription.getText().toString());
                mBincard.setUnit(binding.addeditbincardEtUnit.getText().toString());
                mBincard.setBuying(binding.addeditbincardEtBuying.getText().toString());
                mBincard.setSelling(binding.addeditbincardEtSelling.getText().toString());
                mBincard.setQuantity(binding.addeditbincardEtQuantity.getText().toString());

                if(mBincard.getId().equals("0")) {               // brand new bincard
                    // Get next id
                    FireStoreDao.getNextId(docPath,nextId -> {
                        mBincard.setId(nextId);
                       db.document(docPath).update(mBincard.getId(), mBincard);
                    });
                }else {
                    // Saving with the current id
                    db.document(docPath).update(mBincard.getId(), mBincard);
                }

                // Upload image if any
                if (imageUri != null) {
                    savePicture(mBincard.getId());
                } else {
                    finish();
                }

            }
        });

    }

    private void savePicture(String id) {
        String location = "bincardsimage/" + id;
        ProgressDialog progressDialog = new ProgressDialog(AddEditBincard.this);
        progressDialog.setMessage("Saving image...");
        progressDialog.show();
        // Compress image and upload
        try {
            Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG,25,baos);
            byte[] data = baos.toByteArray();
            storage.getReference(location).putBytes(data)
                    .addOnCompleteListener(task -> {
                        storage.getReference(location).getDownloadUrl()
                                .addOnSuccessListener(uri -> {
                                    progressDialog.dismiss();
                                    FirebaseFirestore.getInstance().document(docPath)
                                            .update(id + ".imageUrl",uri.toString()).addOnCompleteListener(task1 -> {
                                                progressDialog.dismiss();
                                                finish();
                                            });
                                });
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void loadDocument() {
        binding.addeditbincardEtCode.setText(mBincard.getItemCode());
        binding.addeditbincardEtDescription.setText(mBincard.getDescription());
        binding.addeditbincardEtUnit.setText(mBincard.getUnit());
        binding.addeditbincardEtBuying.setText(mBincard.getBuying());
        binding.addeditbincardEtSelling.setText(mBincard.getSelling());
        binding.addeditbincardEtQuantity.setText(mBincard.getQuantity());
        Glide.with(this).load(mBincard.getImageUrl()).centerCrop().into(binding.addeditbincardIv);


    }


    private final View.OnClickListener onUploadImage = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            getImageLauncher.launch(intent);

        }
    };

    private final ActivityResultLauncher<Intent> getImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode()== Activity.RESULT_OK) {
                        imageUri = result.getData().getData();
                        Glide.with(AddEditBincard.this).load(imageUri).centerCrop().into(picture);
                    }
                }
            }
    );



}

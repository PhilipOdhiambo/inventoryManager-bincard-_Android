package com.example.bincard.model;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class StorageDao {
    static FirebaseStorage storage = FirebaseStorage.getInstance();

    public interface OnStorage {
        void onStorage (String uri);
    }
    public static void getStoragePath(String path, String id, Uri imageUri, OnStorage callback) {
        StorageReference bincardpicsRef = storage.getReference(path + id + ".png");
        UploadTask uploadTask = bincardpicsRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageReference storageReference = taskSnapshot.getStorage();
                Task<Uri> task = storageReference.getDownloadUrl();
                Uri downloadUrl = task.getResult();
                callback.onStorage(downloadUrl.toString());
            }
        });

    }
}



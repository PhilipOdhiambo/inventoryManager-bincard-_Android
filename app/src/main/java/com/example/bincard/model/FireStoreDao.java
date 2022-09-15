package com.example.bincard.model;
import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FireStoreDao {
    public static List<Bincard> bincardList;

    public FireStoreDao() {
    }


    public interface BicardListCallBack {
        void onCallback(List<Bincard> bincardList);
    }
    public static void getBincardList(Context context, BicardListCallBack callBack) {
        FirebaseFirestore.getInstance().document("inventory/meds1").addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException error) {
                        if(document.exists()) {
                            Map<String,Object> dataset = document.getData();
                            List<Bincard> list = new ArrayList<>();

                            for( Object value: dataset.values()) {

                                Bincard bincard = new Bincard();
                                Map<String,String> hash = (Map<String, String>) value;

                                bincard.setBuying(hash.get("buying"));
                                bincard.setItemCode(hash.get("code"));
                                bincard.setDescription(hash.get("description"));
                                bincard.setId(hash.get("id"));
                                bincard.setImageUrl(hash.get("imageUrl"));
                                bincard.setMarkup(hash.get("markup"));
                                bincard.setSelling(hash.get("selling"));
                                bincard.setUnit(hash.get("unit"));
                                bincard.setQuantity(hash.get("quantity"));
                                list.add(bincard);
                            }
                            callBack.onCallback(list);
                        }
                        if(error != null) {
                            Toast.makeText(context,"Please check your Network",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    public interface  OneBincardCallBack {
        void onCallback(Bincard bincard);
    }
    public static void getOneBincard(String path,String id, OneBincardCallBack callBack) {
        FirebaseFirestore.getInstance().document(path).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc.exists()) {
                                Gson gson = new Gson();
                                Bincard data = gson.fromJson(gson.toJson(doc.get(id)),Bincard.class);
                                callBack.onCallback(data);
                            } else {
                                callBack.onCallback(null);
                            }

                        }else {
                            callBack.onCallback(null);
                        }
                    }
                });
    }


    public  interface NextIdCallback {
        void onCallback (String nextId);
    }
    public static void getNextId(String docPath, NextIdCallback callback) {
        FirebaseFirestore.getInstance().document("inventory/meds1").get()
                .addOnCompleteListener(task -> {
                    Map<String,Object> doc = task.getResult().getData();
                    List<String> keysArr = new ArrayList<>();
                    for (String key : doc.keySet()) {
                        keysArr.add(key);
                    }
                    Collections.sort(keysArr, Collections.reverseOrder());
                    Integer intNextId = Integer.valueOf(keysArr.get(0)) + 1;
                    callback.onCallback(String.valueOf(intNextId));

                });
    }






    public interface med1Callback {
        void onMed1(Task<DocumentSnapshot> task);
    }

    public static void getMed1(Context context, med1Callback callBack) {
        FirebaseFirestore.getInstance().document("inventory/meds1").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        callBack.onMed1(task);
                    }
                });
    };


}

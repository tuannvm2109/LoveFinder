package com.example.manhtuan.lovefinder.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseConnection {
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference databaseRef = database.getReference();
    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference storageRef = storage.getReference();


}

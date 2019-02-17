package com.kcirqueit.spinadmin.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kcirqueit.spinadmin.model.User;

import androidx.lifecycle.LiveData;

public class UserRepository {

    private DatabaseReference mRootRef;
    private DatabaseReference mUserRef;

    private static UserRepository userRepository;

    FirebaseQueryLiveData firebaseQueryLiveData;

    private UserRepository() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mUserRef = mRootRef.child("Users");
        firebaseQueryLiveData = new FirebaseQueryLiveData(mUserRef);
    }


    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
            return userRepository;
        }
        return userRepository;
    }

    public LiveData<DataSnapshot> getAllUser() {
        return firebaseQueryLiveData;
    }





}

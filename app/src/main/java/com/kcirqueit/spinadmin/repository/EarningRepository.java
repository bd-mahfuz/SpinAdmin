package com.kcirqueit.spinadmin.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kcirqueit.spinadmin.model.Earning;

import androidx.lifecycle.LiveData;

public class EarningRepository {

    private DatabaseReference mRootRef;
    private DatabaseReference mUserRef;
    private DatabaseReference mEarningRef;

    private static EarningRepository earningRepository;

    FirebaseQueryLiveData firebaseQueryLiveData;


    private EarningRepository() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mEarningRef = mRootRef.child("Earnings");
        firebaseQueryLiveData = new FirebaseQueryLiveData(mEarningRef);
    }

    public static EarningRepository getInstance() {
        if (earningRepository == null) {
            earningRepository = new EarningRepository();
            return earningRepository;
        }

        return earningRepository;
    }


    public Task addEarning(Earning earning) {
        return mEarningRef.child(earning.getUserId()).setValue(earning);
    }


    public LiveData<DataSnapshot> getAllUser() {
        return firebaseQueryLiveData;
    }

}

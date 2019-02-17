package com.kcirqueit.spinadmin.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kcirqueit.spinadmin.model.PaymentRequest;

import java.util.Map;

import androidx.lifecycle.LiveData;

public class PaymentRequestsRepository {

    private static PaymentRequestsRepository paymentRequestsRepository;

    private FirebaseQueryLiveData firebaseQueryLiveData;

    private DatabaseReference mRootRef;
    private DatabaseReference mPaymentRef;

    private PaymentRequestsRepository() {
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mPaymentRef = mRootRef.child("PaymentRequests");
        firebaseQueryLiveData = new FirebaseQueryLiveData(mPaymentRef);
    }

    public static PaymentRequestsRepository getInstance() {

        if (paymentRequestsRepository == null) {
            paymentRequestsRepository = new PaymentRequestsRepository();
            return paymentRequestsRepository;
        }

        return paymentRequestsRepository;
    }


    public Task addPaymentRequest(PaymentRequest paymentRequest) {
        String key = mPaymentRef.push().getKey();
        return mPaymentRef.child(paymentRequest.getUserId()).child(key).setValue(paymentRequest);
    }

    public Task pay(PaymentRequest paymentRequest) {
        return mPaymentRef.child(paymentRequest.getUserId()).child(paymentRequest.getId())
        .child("transactionStatus").setValue("completed");
    }

    public LiveData<DataSnapshot> getAllPayment() {
        return firebaseQueryLiveData;
    }

    public LiveData<DataSnapshot> getPaymentRequestByUserId(String userId) {
        mPaymentRef = mPaymentRef.child(userId);
        return new FirebaseQueryLiveData(mPaymentRef);
    }

}

package com.kcirqueit.spinadmin.viewModel;

import android.app.Application;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.kcirqueit.spinadmin.model.PaymentRequest;
import com.kcirqueit.spinadmin.repository.PaymentRequestsRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class PaymentRequestViewModel extends AndroidViewModel {

    private PaymentRequestsRepository paymentRequestsRepository;

    public PaymentRequestViewModel(@NonNull Application application) {
        super(application);
        paymentRequestsRepository = PaymentRequestsRepository.getInstance();
    }

    public Task addPaymentRequest(PaymentRequest paymentRequest) {
        return paymentRequestsRepository.addPaymentRequest(paymentRequest);
    }

    public LiveData<DataSnapshot> getPaymentRequestByUserId(String userId) {
        return paymentRequestsRepository.getPaymentRequestByUserId(userId);
    }

    public LiveData<DataSnapshot> getAllPaymentRequest() {
        return paymentRequestsRepository.getAllPayment();
    }


    public Task Pay (PaymentRequest paymentRequest) {
        return paymentRequestsRepository.pay(paymentRequest);
    }


}

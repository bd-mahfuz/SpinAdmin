package com.kcirqueit.spinadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.kcirqueit.spinadmin.adapter.PaymentAdapter;
import com.kcirqueit.spinadmin.model.PaymentRequest;
import com.kcirqueit.spinadmin.viewModel.PaymentRequestViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentRequestActivity extends AppCompatActivity {

    @BindView(R.id.payment_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.payment_rv)
    RecyclerView mPaymentRv;

    @BindView(R.id.no_request_msg_tv)
    TextView mNoRequestMsg;

    private PaymentRequestViewModel mPaymentRequestViewModel;
    private List<PaymentRequest> paymentRequestList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);

        mPaymentRequestViewModel = ViewModelProviders.of(this).get(PaymentRequestViewModel.class);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Payment Requests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mPaymentRv.setLayoutManager(new LinearLayoutManager(this));
        mPaymentRv.setHasFixedSize(true);


    }


    @Override
    protected void onStart() {
        super.onStart();

        mPaymentRequestViewModel.getAllPaymentRequest().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                paymentRequestList.clear();
                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        for (DataSnapshot paymentSnapshot: dataSnapshot1.getChildren()) {
                            PaymentRequest paymentRequest = paymentSnapshot.getValue(PaymentRequest.class);
                            paymentRequestList.add(paymentRequest);
                        }
                    }

                    PaymentAdapter adapter = new PaymentAdapter(PaymentRequestActivity.this,
                            paymentRequestList);
                    mPaymentRv.setAdapter(adapter);

                    mNoRequestMsg.setVisibility(View.GONE);
                } else {
                    mNoRequestMsg.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}

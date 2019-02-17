package com.kcirqueit.spinadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.kcirqueit.spinadmin.model.PaymentRequest;
import com.kcirqueit.spinadmin.viewModel.PaymentRequestViewModel;

public class PaymentDetailsActivity extends AppCompatActivity {

    private PaymentRequest paymentRequest;


    @BindView(R.id.withdraw_point_tv)
    TextView mWithdrawTv;

    @BindView(R.id.pm_name_et)
    TextView mNameTv;

    @BindView(R.id.pay_type)
    TextView mPayType;

    @BindView(R.id.mobile_tv)
    TextView mMobileEt;

    @BindView(R.id.p_email_tv)
    TextView mEmail;

    @BindView(R.id.account_type_tv)
    TextView mAccountType;

    @BindView(R.id.pay_bt)
    AppCompatButton mPayBt;

    @BindView(R.id.payment_details_toolbar)
    Toolbar mToolbar;

    private PaymentRequestViewModel paymentRequestViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        ButterKnife.bind(this);

        paymentRequestViewModel = ViewModelProviders.of(this).get(PaymentRequestViewModel.class);
        
        paymentRequest = (PaymentRequest) getIntent().getSerializableExtra("paymentRequest");


        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Payment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (paymentRequest != null) {
            setValue();
        }




    }

    private void setValue() {

        mWithdrawTv.setText(paymentRequest.getWithdrawPoint()+"");
        mNameTv.setText(paymentRequest.getName());
        mEmail.setText(paymentRequest.getEmail());
        mMobileEt.setText(paymentRequest.getMobileNo());
        mPayType.setText(paymentRequest.getPaymentType());
        mAccountType.setText(paymentRequest.getAccountType());

        if (paymentRequest.getTransactionStatus().equals("completed")) {
            mPayBt.setEnabled(false);
        }

    }

    @OnClick(R.id.pay_bt)
    public void payClick() {

        paymentRequestViewModel.Pay(paymentRequest).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {

                    Toast.makeText(PaymentDetailsActivity.this, "Payment is completed",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    
                } else {

                    Toast.makeText(PaymentDetailsActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                    
                }
            }
        });


    }
}

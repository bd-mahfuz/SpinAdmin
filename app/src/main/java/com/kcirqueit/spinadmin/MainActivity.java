package com.kcirqueit.spinadmin;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.kcirqueit.spinadmin.adapter.UserAdapter;
import com.kcirqueit.spinadmin.model.PaymentRequest;
import com.kcirqueit.spinadmin.model.User;
import com.kcirqueit.spinadmin.viewModel.EarningViewModel;
import com.kcirqueit.spinadmin.viewModel.PaymentRequestViewModel;
import com.kcirqueit.spinadmin.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_rv)
    RecyclerView mUserRv;

    @BindView(R.id.no_user_message)
    TextView mNoUserMsg;

    TextView mBadge;

    private UserViewModel mUserViewModel;
    private PaymentRequestViewModel mPaymentRequestViewModel;

    private List<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mPaymentRequestViewModel = ViewModelProviders.of(this).get(PaymentRequestViewModel.class);

        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            View view = LayoutInflater.from(this).inflate(R.layout.notification_layout, null);
            actionBar.setCustomView(view);
            actionBar.setDisplayShowCustomEnabled(true);
            mBadge = view.findViewById(R.id.badge);

            RelativeLayout notificationLayout = view.findViewById(R.id.notification_layout);

            notificationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, PaymentRequestActivity.class));
                }
            });

        }



        mUserRv.setLayoutManager(new LinearLayoutManager(this));
        mUserRv.setHasFixedSize(true);


    }


    @Override
    protected void onStart() {
        super.onStart();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        mUserViewModel.getAllUsers().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                userList.clear();
                if (dataSnapshot.getValue() != null) {
                    mNoUserMsg.setVisibility(View.GONE);

                    for (DataSnapshot userSnapShot : dataSnapshot.getChildren()) {
                        User user = userSnapShot.getValue(User.class);
                        userList.add(user);
                    }

                    UserAdapter adapter = new UserAdapter(MainActivity.this, userList);
                    mUserRv.setAdapter(adapter);

                    progressDialog.dismiss();

                } else {
                   mNoUserMsg.setVisibility(View.VISIBLE);
                   progressDialog.dismiss();
                }

            }
        });


        mPaymentRequestViewModel.getAllPaymentRequest().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {

                    int count =0;
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        for (DataSnapshot paymentSnapshot: dataSnapshot1.getChildren()) {
                            PaymentRequest paymentRequest = paymentSnapshot.getValue(PaymentRequest.class);
                            if (paymentRequest.getTransactionStatus().equals("pending")) {
                                count++;
                            }
                        }
                    }
                    mBadge.setText(count+"");
                } else {
                    mBadge.setText("0");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}

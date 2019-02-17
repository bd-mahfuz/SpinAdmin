package com.kcirqueit.spinadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import android.os.Bundle;
import android.widget.TextView;

import com.kcirqueit.spinadmin.model.User;
import com.squareup.picasso.Picasso;

public class UserDetailsActivty extends AppCompatActivity {


    @BindView(R.id.user_iv)
    CircleImageView mUserIv;

    @BindView(R.id.m_name_et)
    TextView mNameEt;

    @BindView(R.id.gender_tv)
    TextView mGenderTv;

    @BindView(R.id.dob_tv)
    TextView mDobTv;

    @BindView(R.id.phone_tv)
    TextView mPhonetv;

    @BindView(R.id.p_email_tv)
    TextView mEmailTv;

    @BindView(R.id.country_Name_tv)
    TextView mCountryNameTv;

    @BindView(R.id.user_details_toolbar)
    Toolbar mUserDetailsToolbar;


    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_activty);

        ButterKnife.bind(this);

        user = (User) getIntent().getSerializableExtra("user");

        setSupportActionBar(mUserDetailsToolbar);
        getSupportActionBar().setTitle("User Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (user != null) {
            setValue();
        }

    }

    private void setValue() {

        if (!user.getPhotoUrl().equals("default")) {
            Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.user_avater)
                    .into(mUserIv);
        }

        mNameEt.setText(user.getUserName());
        mEmailTv.setText(user.getEmail());
        mGenderTv.setText(user.getGender());
        mDobTv.setText(user.getDateOfBirth());
        mPhonetv.setText(user.getPhoneNumber());
        mCountryNameTv.setText(user.getCountry());
    }
}

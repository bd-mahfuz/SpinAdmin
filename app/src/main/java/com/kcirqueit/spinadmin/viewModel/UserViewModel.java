package com.kcirqueit.spinadmin.viewModel;

import android.app.Application;

import com.google.firebase.database.DataSnapshot;
import com.kcirqueit.spinadmin.repository.UserRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class UserViewModel extends AndroidViewModel {

    private UserRepository mUserRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mUserRepository = UserRepository.getInstance();

    }

    public LiveData<DataSnapshot> getAllUsers() {
        return mUserRepository.getAllUser();
    }



}

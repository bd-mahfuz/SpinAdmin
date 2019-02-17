package com.kcirqueit.spinadmin.viewModel;

import android.app.Application;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.kcirqueit.spinadmin.model.Earning;
import com.kcirqueit.spinadmin.repository.EarningRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EarningViewModel extends AndroidViewModel {


    private EarningRepository earningRepository;

    public EarningViewModel(@NonNull Application application) {
        super(application);

        earningRepository = EarningRepository.getInstance();

    }


    public Task addEarning(Earning earning) {
        return earningRepository.addEarning(earning);
    }

    public LiveData<DataSnapshot> getAllUser() {
        return earningRepository.getAllUser();
    }


}

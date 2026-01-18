package com.example.sra.ui.home;
//package com.example.myapplication;

// HomeViewModel.java
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sra.Job;
import com.example.sra.R;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<List<Job>> jobsLiveData;

    public HomeViewModel() {
        jobsLiveData = new MutableLiveData<>();
        loadJobs();
    }

    public LiveData<List<Job>> getJobs() {
        return jobsLiveData;
    }

    private void loadJobs() {
        // Sample data for now
        List<Job> jobList = new ArrayList<>();
        jobList.add(new Job("Afro Message Technologies", "Junior Software Engineer",
                "20,000 – 30,000 birr", "Addis Ababa, Ethiopia", R.drawable.facebook));
        jobList.add(new Job("Minab Technologies", "Database Engineer",
                "50,000 – 60,000 birr", "Addis Ababa, Ethiopia", R.drawable.facebook));
        jobList.add(new Job("Leul Technologies", "Database Engineer",
                "50,000 – 60,000 birr", "Addis Ababa, Ethiopia", R.drawable.facebook));

        jobsLiveData.setValue(jobList);
    }
}

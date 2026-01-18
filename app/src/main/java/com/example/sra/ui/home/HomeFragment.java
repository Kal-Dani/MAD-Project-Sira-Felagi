package com.example.sra.ui.home;

// HomeFragment.java
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sra.JobsAdapter;
import com.example.sra.R;
//import com.example.sra.ui.home.HomeViewModel;
//
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView jobsRecyclerView;
    private JobsAdapter jobsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        jobsRecyclerView = root.findViewById(R.id.jobsRecyclerView);
        jobsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Observe jobs list from ViewModel
        homeViewModel.getJobs().observe(getViewLifecycleOwner(), jobs -> {
            jobsAdapter = new JobsAdapter(getContext(), jobs);
            jobsRecyclerView.setAdapter(jobsAdapter);
        });

        return root;
    }
}

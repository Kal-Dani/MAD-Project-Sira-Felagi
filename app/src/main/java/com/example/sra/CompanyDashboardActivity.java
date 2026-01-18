package com.example.sra;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CompanyDashboardActivity extends AppCompatActivity {

    private TextInputEditText edtCompanyName, edtCompanyPhone, edtCompanyAddress, edtAboutCompany;
    private TextInputEditText edtJobTitle, edtJobSalary, edtJobDescription, edtRequirementInput;
    private ChipGroup chipGroupRequirements;

    private FloatingActionButton btnAddRequirement;
    private Button btnPostJob;


    private FirebaseFirestore db;
    private FirebaseAuth auth;

    private ArrayList<String> requirementsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_dashboard);

        // Firebase
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Initialize UI
        edtCompanyName = findViewById(R.id.edtCompanyName);
        edtCompanyPhone = findViewById(R.id.edtCompanyPhone);
        edtCompanyAddress = findViewById(R.id.edtCompanyAddress);
        edtAboutCompany = findViewById(R.id.edtAboutCompany);

        edtJobTitle = findViewById(R.id.edtJobTitle);
        edtJobSalary = findViewById(R.id.edtJobSalary);
        edtJobDescription = findViewById(R.id.edtJobDescription);
        edtRequirementInput = findViewById(R.id.edtRequirementInput);

        chipGroupRequirements = findViewById(R.id.chipGroupRequirements);
        btnAddRequirement = findViewById(R.id.btnAddRequirement);
        btnPostJob = findViewById(R.id.btnPostJob);

        requirementsList = new ArrayList<>();

        // Add requirement chip
        btnAddRequirement.setOnClickListener(v -> {
            String req = edtRequirementInput.getText().toString().trim();
            if (!TextUtils.isEmpty(req)) {
                addRequirementChip(req);
                edtRequirementInput.setText("");
            } else {
                Toast.makeText(this, "Enter a requirement", Toast.LENGTH_SHORT).show();
            }
        });

        // Post job
        btnPostJob.setOnClickListener(v -> postJob());
    }

    // Add a chip to the group
    private void addRequirementChip(String requirement) {
        Chip chip = new Chip(this);
        chip.setText(requirement);
        chip.setCloseIconVisible(true);
        chip.setOnCloseIconClickListener(v -> {
            chipGroupRequirements.removeView(chip);
            requirementsList.remove(requirement);
        });
        chipGroupRequirements.addView(chip);
        requirementsList.add(requirement);
    }

    // Post job to Firestore
    private void postJob() {
        String companyName = edtCompanyName.getText().toString().trim();
        String phone = edtCompanyPhone.getText().toString().trim();
        String address = edtCompanyAddress.getText().toString().trim();
        String about = edtAboutCompany.getText().toString().trim();

        String title = edtJobTitle.getText().toString().trim();
        String salary = edtJobSalary.getText().toString().trim();
        String description = edtJobDescription.getText().toString().trim();

        if (companyName.isEmpty() || phone.isEmpty() || address.isEmpty() || about.isEmpty()
                || title.isEmpty() || salary.isEmpty() || description.isEmpty() || requirementsList.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and add at least one requirement", Toast.LENGTH_SHORT).show();
            return;
        }

        // Prepare job data
        Map<String, Object> job = new HashMap<>();
        job.put("company", companyName);
        job.put("phone", phone);
        job.put("location", address);
        job.put("aboutCompany", about);
        job.put("position", title);
        job.put("salary", salary);
        job.put("description", description);
        job.put("requirements", requirementsList);
        job.put("postedBy", auth.getCurrentUser().getUid());

        // Save to Firestore under "jobs" collection
        db.collection("jobs")
                .add(job)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Job posted successfully!", Toast.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    // Clear all inputs
    private void clearFields() {
        edtCompanyName.setText("");
        edtCompanyPhone.setText("");
        edtCompanyAddress.setText("");
        edtAboutCompany.setText("");
        edtJobTitle.setText("");
        edtJobSalary.setText("");
        edtJobDescription.setText("");
        edtRequirementInput.setText("");
        chipGroupRequirements.removeAllViews();
        requirementsList.clear();
    }
}

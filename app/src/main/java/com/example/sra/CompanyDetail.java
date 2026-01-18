package com.example.sra;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CompanyDetail extends AppCompatActivity {

    private TextView txtPhone, txtEmail, txtCompanyName, txtCompanyLocation, txtAbout;
    private ImageView companyLogo, headerImage;
    private Button btnAvailableJobs;

    private Job job; // received job

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        initializeViews();
        receiveJobData();
        populateCompanyDetails();
        setupClickListeners();
    }

    private void initializeViews() {
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtCompanyName = findViewById(R.id.companyName);
        txtCompanyLocation = findViewById(R.id.companyLocation);
        txtAbout = findViewById(R.id.aboutText);
        companyLogo = findViewById(R.id.companyLogo);
        headerImage = findViewById(R.id.headerImage);
        btnAvailableJobs = findViewById(R.id.btnAvailableJobs);
    }

    private void receiveJobData() {
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("jobObject")) {
            job = (Job) intent.getSerializableExtra("jobObject");
        }
    }

    private void populateCompanyDetails() {
        if (job != null) {
            txtCompanyName.setText(job.getCompanyName());
            txtCompanyLocation.setText(job.getLocation());
            companyLogo.setImageResource(job.getCompanyLogo());
            // Example placeholders, replace with real data if you have it
            txtPhone.setText("+251 912345678");
            txtEmail.setText(job.getCompanyName().replaceAll("\\s+", "") + "@gmail.com");
            txtAbout.setText("This is about " + job.getCompanyName() + ". Lorem ipsum dolor sit amet...");
            headerImage.setImageResource(job.getCompanyLogo()); // or a banner drawable
            btnAvailableJobs.setText("View Jobs at " + job.getCompanyName());
        }
    }

    private void setupClickListeners() {
        txtPhone.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + txtPhone.getText().toString()));
            startActivity(intent);
        });

        txtEmail.setOnClickListener(v -> {
            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setData(Uri.parse("mailto:" + txtEmail.getText().toString()));
            startActivity(email);
        });

        btnAvailableJobs.setOnClickListener(v -> {
            Intent intent = new Intent(this, JobDetailActivity.class);
            intent.putExtra("jobObject", job);
            startActivity(intent);
        });
    }
}

package com.lingdian.jobservicedemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn);
        button.setText(getClass().getSimpleName());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
                    JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobDaemonService.class.getName()))
                            .setPeriodic(2000)
                            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                            .setRequiresCharging(true)
                            .setRequiresDeviceIdle(true)
                            .build();
                    jobScheduler.schedule(jobInfo);
                }
            }
        });
    }
}

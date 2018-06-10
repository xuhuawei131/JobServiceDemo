package com.lingdian.jobservicedemo;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyJobDaemonService extends JobService {
    private int kJobId = 0;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("xhw", "onStartCommand jobService启动");

        enToast("jobService启动");
//        scheduleJob(getJobInfo());
        return START_NOT_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.i("xhw", "执行了onStartJob方法");

        enToast("jobService启动");
        scheduleJob(getJobInfo());
        boolean isLocalServiceWork = isServiceWork(this, "com.lingdian.jobservicedemo");
        if(!isLocalServiceWork){
            this.startService(new Intent(this,CommonService.class));
            Log.i("onStartJob", "CommonService");
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i("xhw", "执行了onStopJob方法");
        scheduleJob(getJobInfo());
        return false;
    }

    //将任务作业发送到作业调度中去
    public void scheduleJob(JobInfo t) {
        Log.i("xhw", "调度job id:"+t.getId());
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }

    public JobInfo getJobInfo(){
//        JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, MyJobDaemonService.class));
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE);
//        builder.setPersisted(true);
//        builder.setRequiresCharging(false);
//        builder.setRequiresDeviceIdle(false);
//        //间隔1000毫秒
//        builder.setPeriodic(1000);
//        return builder.build();

        JobInfo jobInfo = new JobInfo.Builder(1, new ComponentName(getPackageName(), MyJobDaemonService.class.getName()))
                .setMinimumLatency(10000L)
                .setOverrideDeadline(10000L)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(true)
                .setRequiresDeviceIdle(true)
                .setPersisted(true)
                .build();
       return jobInfo;
    }

    // 判断服务是否正在运行
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }


    private void enToast(String content){
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}

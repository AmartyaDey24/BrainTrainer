package com.example.braintrainerfinal;

import android.app.Application;

import io.branch.referral.Branch;

public class CustomApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Branch.getAutoInstance(this);
        //Branch.enableLogging();
    }
}

package com.recargapay.orchestrator.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface TsActivity {

    @ActivityMethod
    void save();
}

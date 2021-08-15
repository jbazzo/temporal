package com.recargapay.orchestrator.workflows;

import com.recargapay.orchestrator.activities.CheckoutActivity;
import com.recargapay.orchestrator.activities.FraudActivity;
import com.recargapay.orchestrator.activities.TsActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class BasicOrdersWorkflowImpl implements BasicOrdersWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofSeconds(15))
            .build();

    private final CheckoutActivity checkout = Workflow.newActivityStub(CheckoutActivity.class, options);

    ActivityOptions options2 = ActivityOptions.newBuilder()
            .setScheduleToCloseTimeout(Duration.ofSeconds(15))
            .setRetryOptions(RetryOptions.newBuilder().setMaximumAttempts(5).build())
            .build();

    private final FraudActivity fraud = Workflow.newActivityStub(FraudActivity.class, options2);

    private final TsActivity ts = Workflow.newActivityStub(TsActivity.class, options2);

    @Override
    public String start() {
        Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
        Saga saga = new Saga(sagaOptions);
        try {
            fraud.verify();
            saga.addCompensation(fraud::compensateVerify);
            ts.save();
            checkout.giveProduct();
            return "READY";
        } catch (ActivityFailure e) {
            saga.compensate();
            throw e;
        }
    }
}

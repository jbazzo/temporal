package com.recargapay.orchestrator.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface BasicOrdersWorkflow {

    @WorkflowMethod
    String start();
}

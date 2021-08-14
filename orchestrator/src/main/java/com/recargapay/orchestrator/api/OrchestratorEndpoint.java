package com.recargapay.orchestrator.api;

import com.recargapay.orchestrator.workflows.BasicOrdersWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrchestratorEndpoint {

    @PostMapping("/api/v1/workflow/{workflowName}")
    public ResponseEntity<?> startWorkflow(@PathVariable("workflowName") final String workflowName){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder().setTarget("172.19.0.3:7233").build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("ordersFullfilmentQueue").setWorkflowId("ordersFullfilmentWorkflow")
                .build();

        ResponseEntity response=ResponseEntity.status(404).build();
        switch (workflowName){
            case "BasicOrdersWorkflow":{
                BasicOrdersWorkflow workflow = client.newWorkflowStub(BasicOrdersWorkflow.class, options);
                WorkflowExecution workflowExecution = WorkflowClient.start(workflow::start);

                response=ResponseEntity.ok("{'workflowId':'"+workflowExecution.getWorkflowId()+"','workflowRunId':'"+workflowExecution.getRunId()+"'}");

                break;
            }
            default: break;
        }

        return response;

    }
}

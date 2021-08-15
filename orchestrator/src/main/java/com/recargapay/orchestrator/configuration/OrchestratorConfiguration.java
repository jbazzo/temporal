package com.recargapay.orchestrator.configuration;

import com.recargapay.orchestrator.activities.CheckoutActivityImpl;
import com.recargapay.orchestrator.activities.FraudActivityImpl;
import com.recargapay.orchestrator.activities.TsActivityImpl;
import com.recargapay.orchestrator.workflows.BasicOrdersWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrchestratorConfiguration {

    @Bean
    @Qualifier("orderFullfilmentWorker")
    public WorkerFactory configureFactory(){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder().setTarget("172.19.0.3:7233").build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("ordersFullfilmentQueue");
        worker.registerWorkflowImplementationTypes(BasicOrdersWorkflowImpl.class);
        factory.start();

        return factory;
    }

    @Bean
    @Qualifier("fraudWorker")
    public Worker configureFraudWorker(){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder().setTarget("172.19.0.3:7233").build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("ordersFullfilmentQueue");
        worker.registerActivitiesImplementations(new FraudActivityImpl());
        factory.start();

        return worker;
    }

    @Bean
    @Qualifier("tsWorker")
    public Worker configureTsWorker(){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder().setTarget("172.19.0.3:7233").build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("ordersFullfilmentQueue");
        worker.registerActivitiesImplementations(new TsActivityImpl());
        factory.start();

        return worker;
    }

    @Bean
    @Qualifier("checkoutWorker")
    public Worker configureCheckoutWorker(){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder().setTarget("172.19.0.3:7233").build());
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker("ordersFullfilmentQueue");
        worker.registerActivitiesImplementations(new CheckoutActivityImpl());
        factory.start();

        return worker;
    }
}

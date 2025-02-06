package io.flowsquad.camunda.test;


import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@ExtendWith(ProcessEngineCoverageExtension.class)
@Deployment(resources = "order-process-new.bpmn")
public class OrderProcessTest {

//    @RegisterExtension
//    public static ProcessEngineCoverageExtension extension = ProcessEngineExtensionProvider.extension;

    @Test
    public void shouldExecuteHappyPath() {
        final ProcessInstance instance = this.startProcess();

        assertThat(instance).isWaitingAt("Task_ProcessOrder");

        complete(task(), withVariables("orderOk", true));

        assertThat(instance).isWaitingAt("Task_DeliverOrder");

        complete(task());

        assertThat(instance)
                .hasPassed("Event_OrderProcessed")
                .isEnded();
    }

//    @Test
//    public void shouldCancelOrder() {
//        final ProcessInstance instance = this.startProcess();
//
//        assertThat(instance).isWaitingAt("Task_ProcessOrder");
//
//        complete(task(), withVariables("orderOk", false));
//
//        assertThat(instance)
//                .hasPassed("Event_OrderCancelled")
//                .isEnded();
//    }


    private ProcessInstance startProcess() {
        return runtimeService().startProcessInstanceByKey("order-process-new");
    }

}

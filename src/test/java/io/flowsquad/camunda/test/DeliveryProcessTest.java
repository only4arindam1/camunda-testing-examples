package io.flowsquad.camunda.test;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.junit5.test.ProcessEngineExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
//import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRule;
//import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
//import org.camunda.bpm.scenario.ProcessScenario;
//import org.camunda.bpm.scenario.Scenario;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.taskService;
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
//import static org.mockito.Mockito.*;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;

@Deployment(resources = "deliver-process.bpmn")
public class DeliveryProcessTest {

    public static final String PROCESS_KEY = "deliveryprocess";
    public static final String TASK_DELIVER_ORDER = "Task_DeliverOrder";
    public static final String VAR_ORDER_DELIVERED = "orderDelivered";
    public static final String END_EVENT_DELIVERY_COMPLETED = "EndEvent_DeliveryCompleted";
    public static final String END_EVENT_DELIVERY_CANCELLED = "EndEvent_DeliveryCancelled";


    @RegisterExtension
    public static ProcessEngineCoverageExtension extension = ProcessEngineExtensionProvider.extension;

//
//    @Rule
//    @ClassRule
//    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
//            .assertClassCoverageAtLeast(0.9)
//            .build();
//
//    @Mock
//    private ProcessScenario testDeliveryProcess;
//
//    @Before
//    public void defaultScenario() {
//        MockitoAnnotations.initMocks(this);
//
//        //Happy-Path
//        when(testDeliveryProcess.waitsAtUserTask(TASK_DELIVER_ORDER))
//                .thenReturn(task -> {
//                    task.complete(withVariables(VAR_ORDER_DELIVERED, true));
//                });
//    }




    @Test
    public void shouldExecuteHappyPath() {

        final ProcessInstance instance = this.startProcess();

//        verify(testDeliveryProcess)
//                .hasFinished(END_EVENT_DELIVERY_COMPLETED);

        assertThat(instance).isWaitingAt(TASK_DELIVER_ORDER);

        complete(task(), withVariables(VAR_ORDER_DELIVERED, true));


        assertThat(instance)
                .hasPassed(END_EVENT_DELIVERY_COMPLETED)
                .isEnded();
    }
//
//    @Test
//    public void shouldExecuteOrderCancelled() {
//        when(testDeliveryProcess.waitsAtUserTask(TASK_DELIVER_ORDER)).thenReturn(task -> {
//            taskService().handleBpmnError(task.getId(), "DeliveryCancelled");
//        });
//
//        Scenario.run(testDeliveryProcess)
//                .startByKey(PROCESS_KEY)
//                .execute();
//
//        verify(testDeliveryProcess)
//                .hasFinished(END_EVENT_DELIVERY_CANCELLED);
//    }
//
//    @Test
//    public void shouldExecuteDeliverTwice() {
//        when(testDeliveryProcess.waitsAtUserTask(TASK_DELIVER_ORDER)).thenReturn(task -> {
//            task.complete(withVariables(VAR_ORDER_DELIVERED, false));
//        }, task -> {
//            task.complete(withVariables(VAR_ORDER_DELIVERED, true));
//        });
//
//        Scenario.run(testDeliveryProcess)
//                .startByKey(PROCESS_KEY)
//                .execute();
//
//        verify(testDeliveryProcess, times(2))
//                .hasCompleted(TASK_DELIVER_ORDER);
//        verify(testDeliveryProcess)
//                .hasFinished(END_EVENT_DELIVERY_COMPLETED);
//    }

    private ProcessInstance startProcess() {
        return runtimeService().startProcessInstanceByKey(PROCESS_KEY);
    }

}

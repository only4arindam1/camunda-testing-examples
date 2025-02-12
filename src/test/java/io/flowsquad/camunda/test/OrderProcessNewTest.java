package io.flowsquad.camunda.test;


import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
//import static org.camunda.community.mockito.MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration;
//import org.camunda.bpm.model.bpmn.Bpmn;
//import org.camunda.community.mockito.function.DeployProcess;

@ExtendWith(ProcessEngineCoverageExtension.class)
@Deployment(resources = {"order-process.bpmn","deliver-process.bpmn"})
public class OrderProcessNewTest {

    private static final String PROCESS_ID = "order-process";
    private static final String SUB_PROCESS_ID = "delivery-process";

//    @RegisterExtension
//    public static ProcessEngineCoverageExtension extension = ProcessEngineExtensionProvider.extension;

//    @Rule
//    public final ProcessEngineRule camunda = new ProcessEngineRule(mostUsefulProcessEngineConfiguration().buildProcessEngine());


//    private void prepareProcessWithOneSubprocess() {
//        final BpmnModelInstance processWithSubProcess = Bpmn.createExecutableProcess(PROCESS_ID)
//                .camundaHistoryTimeToLive(1)
//                .startEvent("start")
//                .callActivity("Task_DeliverOrder")
//                .camundaOut("foo", "foo")
//                .calledElement(SUB_PROCESS_ID)
//                .endEvent("end")
//                .done();
//
//        camunda.manageDeployment(new DeployProcess(camunda).apply(PROCESS_ID, processWithSubProcess));
//    }

    @Test
    public void shouldExecuteHappyPath() {
        final ProcessInstance instance = this.startProcess();

        assertThat(instance).isWaitingAt("Task_CheckAvailability");

        complete(task(), withVariables("productsAvailable", true));

        assertThat(instance).isWaitingAt("Task_PrepareOrder");

        complete(task());
        ProcessInstance deliveryProcessInstance = calledProcessInstance("delivery-process");
        assertThat(deliveryProcessInstance).isActive();
        assertThat(deliveryProcessInstance).isWaitingAt("Task_DeliverOrder");
        complete(task(), withVariables("orderDelivered",true));
        assertThat(deliveryProcessInstance).isEnded();
        assertThat(instance).isEnded();
//        assertThat(instance).isWaitingAt("Task_DeliverOrder");
//        assertThat(instance)
//                .hasPassed("end")
//                .isEnded();
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
        return runtimeService()
                .startProcessInstanceByKey("order-process")
                ;
    }

}

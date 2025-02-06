package io.flowsquad.camunda.test;

import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.mock.Mocks;

//import org.camunda.bpm.scenario.ProcessScenario;

//import org.camunda.bpm.extension.mockito.ProcessExpressions;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.scenario.ProcessScenario;
import org.camunda.bpm.scenario.Scenario;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;

//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.;
//import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRule;
//import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
//import org.camunda.bpm.scenario.ProcessScenario;
//import org.camunda.bpm.scenario.Scenario;
//import org.camunda.bpm.scenario.delegate.TaskDelegate;
//import org.junit.Before;
//import org.junit.ClassRule;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.Mock;

//
//import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;
//import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
//import static org.camunda.community.mockito.MostUsefulProcessEngineConfiguration.mostUsefulProcessEngineConfiguration;


@Deployment(resources = "order-process.bpmn")
public class WorkflowTest {


    @RegisterExtension
    public static ProcessEngineCoverageExtension extension = ProcessEngineExtensionProvider.extension;


    public static final String PROCESS_KEY = "orderprocess";
    public static final String DELIVERY_PROCESS_KEY = "deliveryprocess";

    public static final String TASK_CHECK_AVAILABILITY = "Task_CheckAvailability";
    public static final String VAR_PRODUCTS_AVAILABLE = "productsAvailable";
    public static final String TASK_PREPARE_ORDER = "Task_PrepareOrder";
    public static final String TASK_DELIVER_ORDER = "Task_DeliverOrder1";
    public static final String VAR_ORDER_DELIVERED = "orderDelivered";
    public static final String TASK_CANCEL_ORDER = "Task_CancelOrder";
    public static final String TASK_SEND_CANCELLATION = "Task_SendCancellation";
    public static final String END_EVENT_ORDER_FULLFILLED = "EndEvent_OrderFullfilled";
    public static final String END_EVENT_ORDER_CANCELLED = "EndEvent_OrderCancelled";
    public static final String END_EVENT_CANCELLATION_SENT = "EndEvent_CancellationSent";
    public static final String TASK_DELIVER_ORDER1 = "Task_DeliverOrder";
    public static final String VAR_CUSTOMER = "customer";

   // private ProcessScenario insuranceApplication = mock(ProcessScenario.class);
//
   @Rule
   public final ProcessEngineRule rule = new ProcessEngineRule();


//    @Rule
//    @ClassRule
//    public static TestCoverageProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create()
//            .excludeProcessDefinitionKeys(DELIVERY_PROCESS_KEY)
//            .assertClassCoverageAtLeast(0.9)
//            .build();


    private static final String PROCESS_ID = "myProcess";
    private static final String SUB_PROCESS_ID = "mySubProcess";
    private static final String SUB_PROCESS2_ID = "mySubProcess2";
    private static final String MESSAGE_DOIT = "DOIT";
    private static final String SIGNAL_ALLDOIT = "ALLDOIT";
    private static final String TASK_USERTASK = "user_task";

//    @BeforeEach
//    public void setUp() {
//        prepareProcessWithOneSubprocess();
//    }
//
//    private void prepareProcessWithOneSubprocess() {
//        final BpmnModelInstance processWithSubProcess = Bpmn.createExecutableProcess(PROCESS_ID)
//                .camundaHistoryTimeToLive(1)
//                .startEvent("start")
//                .callActivity("call_subprocess")
//                .camundaOut("foo", "foo")
//                .calledElement(SUB_PROCESS_ID)
//                .userTask(TASK_USERTASK)
//                .endEvent("end")
//                .done();
//
//        camunda.manageDeployment(new DeployProcess(camunda)
//                .apply(PROCESS_ID, processWithSubProcess));
//    }
    //
     @Mock
     private ProcessScenario testOrderProcess;
//
    @Mock
   private ProcessScenario deliveryRequest;
//   private final ProcessScenario processScenario = mock(ProcessScenario.class);
//
//    @Mock
//    private MailingService mailingService;
//
    @BeforeEach
    public void defaultScenario() {


        MockitoAnnotations.initMocks(this);
//        Mocks.register("sendCancellationDelegate", new SendCancellationDelegate(mailingService));
//
//        doNothing().when(mailingService).sendMail(any());
//
//        ProcessExpressions.registerCallActivityMock(DELIVERY_PROCESS_KEY)
//                .deploy(rule);
        //testOrderProcess

        when(testOrderProcess.runsCallActivity(TASK_DELIVER_ORDER1)).thenReturn(
                Scenario.use(deliveryRequest)
        );

//
//        //Happy-Path
//        when(testOrderProcess.waitsAtUserTask(TASK_CHECK_AVAILABILITY))
//                .thenReturn(task -> {
//                    task.complete(withVariables(VAR_PRODUCTS_AVAILABLE, true));
//                });
//
//        when(testOrderProcess.waitsAtUserTask(TASK_PREPARE_ORDER))
//                .thenReturn(TaskDelegate::complete);
//
//        when(testOrderProcess.waitsAtUserTask(TASK_DELIVER_ORDER))
//                .thenReturn(task -> {
//                    task.complete(withVariables(VAR_ORDER_DELIVERED, true));
//                });
//
//        //Further Activities
//        when(testOrderProcess.waitsAtUserTask(TASK_CANCEL_ORDER))
//                .thenReturn(TaskDelegate::complete);
//
//
    }

  //  @Test
    public void shouldExecuteHappyPath() {
        //ProcessExpressions.registerCallActivityMock(DELIVERY_PROCESS_KEY);


        final ProcessInstance instance = this.startProcess();

       assertThat(instance).isWaitingAt(TASK_CHECK_AVAILABILITY);
        complete(task(),withVariables(VAR_PRODUCTS_AVAILABLE,true));
        assertThat(instance).isWaitingAt(TASK_PREPARE_ORDER);
       // assertThat(instance).calledProcessInstance("deliveryprocess").isWaitingAt("Task_DeliverOrder");
       // complete(task());
      //  assertThat(instance).isWaitingAt(TASK_DELIVER_ORDER);
     //   complete(task(),withVariables(VAR_ORDER_DELIVERED, true));
        assertThat(instance).hasPassed(END_EVENT_ORDER_FULLFILLED);




        }

    private ProcessInstance startProcess(){
        Map variables = new HashMap<>();
        variables.put(VAR_CUSTOMER,"john");

        return runtimeService().startProcessInstanceByKey(PROCESS_KEY,variables);
    }
//
//    @Test
//    public void shouldExecuteCancellationSent() {
//        when(testOrderProcess.waitsAtUserTask(TASK_CHECK_AVAILABILITY)).thenReturn(task -> {
//            task.complete(withVariables(VAR_PRODUCTS_AVAILABLE, false));
//        });
//
//        Scenario.run(testOrderProcess)
//                .startByKey(PROCESS_KEY, withVariables(VAR_CUSTOMER, "john"))
//                .execute();
//
//        verify(testOrderProcess)
//                .hasFinished(END_EVENT_CANCELLATION_SENT);
//
//        verify(mailingService, (times(1))).sendMail(any());
//        verifyNoMoreInteractions(mailingService);
//    }
//
//    @Test
//    public void shouldExecuteOrderCancelled() {
//        ProcessExpressions.registerCallActivityMock(DELIVERY_PROCESS_KEY)
//                .onExecutionDo(execution -> {
//                    throw new BpmnError("deliveryFailed");
//                })
//                .deploy(rule);
//
//        Scenario.run(testOrderProcess)
//                .startByKey(PROCESS_KEY, withVariables(VAR_CUSTOMER, "john"))
//                .execute();
//
//        verify(testOrderProcess)
//                .hasCompleted(TASK_CANCEL_ORDER);
//        verify(testOrderProcess)
//                .hasFinished(END_EVENT_ORDER_CANCELLED);
//    }
}

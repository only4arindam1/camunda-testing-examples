package io.flowsquad.camunda.test;

import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.camunda.community.process_test_coverage.junit5.platform7.ProcessEngineCoverageExtension;

import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.*;
import static org.assertj.core.api.Assertions.*;
import java.util.Map;


@ExtendWith(ProcessEngineCoverageExtension.class)
@Deployment(resources = "simple.dmn")
public class Eligibility_Dmn_test {

    @Test
    public void testSimpleDmn() {
        Map<String, Object> variables = withVariables(
                "userStatus", "VIP",
                "CalculateEngagementScore", 30,
                "under18",true,
                "isStudent",true);

        DmnDecisionRuleResult result = decisionService()
                .evaluateDecisionTableByKey("Decision_eghnpkc")
                .variables(variables)
                .evaluate()
                .getFirstResult();

        System.out.println("result"+result);
        assertThat(result).containsEntry("eligibleForUpgrade", true);
    }

}

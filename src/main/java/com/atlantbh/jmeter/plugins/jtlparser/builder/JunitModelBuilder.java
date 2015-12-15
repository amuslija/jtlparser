package com.atlantbh.jmeter.plugins.jtlparser.builder;

import com.atlantbh.jmeter.plugins.jtlparser.model.jtl.*;
import com.atlantbh.jmeter.plugins.jtlparser.model.jtl.ThreadGroup;
import com.atlantbh.jmeter.plugins.jtlparser.model.junit.TestCase;
import com.atlantbh.jmeter.plugins.jtlparser.model.junit.TestStep;
import com.atlantbh.jmeter.plugins.jtlparser.model.junit.TestSuite;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by adnan on 12/12/15.
 */
public class JunitModelBuilder {

    private static JunitModelBuilder builder = null;
    private int totalFailedTestSteps = 0;
    private int totalTestSteps = 0;
    private double totalTestStepsExecutionTime = 0;

    private JunitModelBuilder() {
        super();
    }


    public static JunitModelBuilder newInstance(){
        if(builder == null) {
            builder = new JunitModelBuilder();
        }
        return builder;
    }

    private TestStep createTestStep(Sampler sampler) {
        TestStep testStep = new TestStep();
        testStep.setName(sampler.getSamplerName() + ": " +sampler.getName());
        for (AssertionResult assertion: sampler.getAssertionResults()) {
            if (assertion.isFailure()) {
                testStep.setAssertionFailures(assertion.getName(),assertion.getFailureMessage());
            }
        }
        if (sampler.hasFailedAssertions())
            totalFailedTestSteps++;
        testStep.setTime(sampler.getTime());
        totalTestStepsExecutionTime += Double.parseDouble(sampler.getTime());

        return testStep;
    }

    private TestCase createTestCase(ThreadGroup threadGroup){
        TestCase testCase = new TestCase();
        testCase.setClassName(threadGroup.getThreadName());

        for(Sampler sampler: threadGroup.getSamplers()) {
            TestStep testStep = createTestStep(sampler);
            testCase.addTestStep(testStep);
            totalTestSteps++;
        }
        return testCase;
    }

    public TestSuite generateTestSuite(ArrayList<ThreadGroup> threadGroups){
        TestSuite testSuite = new TestSuite();
        testSuite.setName("Test Plan");
        totalFailedTestSteps = 0;
        totalTestSteps = 0;
        totalTestStepsExecutionTime = 0;


        for (ThreadGroup threadGroup: threadGroups) {
            TestCase testCase = createTestCase(threadGroup);
            testSuite.addTestCase(testCase);
        }

        testSuite.setTime(String.valueOf(totalTestStepsExecutionTime));
        testSuite.setTests(String.valueOf(totalTestSteps));
        testSuite.setFailures(String.valueOf(totalFailedTestSteps));
        return testSuite;
    }
}

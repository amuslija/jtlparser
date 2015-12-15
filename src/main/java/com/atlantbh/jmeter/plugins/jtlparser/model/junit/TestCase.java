package com.atlantbh.jmeter.plugins.jtlparser.model.junit;

import java.util.ArrayList;

/*
 * This class serves as container of test steps and gives additional info on test case level
 */
public class TestCase {
	private String className;
	private ArrayList<TestStep> testSteps = new ArrayList<TestStep>();

	public TestCase() {
		super();
	}

	public ArrayList<TestStep> getTestSteps() {
		return testSteps;
	}

	public void addTestStep(TestStep testStep) {
		this.testSteps.add(testStep);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}

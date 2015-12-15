package com.atlantbh.jmeter.plugins.jtlparser.model.junit;

import java.util.ArrayList;

/*
 * This class serves as container of test cases and gives additional info on test suite level
 */
public class TestSuite {

	private String name;
	private String tests;
	private String failures;
	private String time;
	private ArrayList<TestCase> testCases = new ArrayList<TestCase>();

	public TestSuite() {
		super();
	}

	public ArrayList<TestCase> getTestCases() {
		return testCases;
	}

	public void addTestCase(TestCase testCase) {
		this.testCases.add(testCase);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTests() {
		return tests;
	}

	public void setTests(String tests) {
		this.tests = tests;
	}

	public String getFailures() {
		return failures;
	}

	public void setFailures(String failures) {
		this.failures = failures;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}

package com.atlantbh.jmeter.plugins.jtlparser.model.jtl;

public class AssertionResult {

	private String name;
	private String failure;
	private String error;
	private String failureMessage;
	private boolean failedAssertion;

	public AssertionResult(){
		super();
		failureMessage="";
	}

	public boolean isFailure(){
		return failedAssertion;
	}

	public String getName() {
		return name;
	}

	public String getFailure() {
		return failure;
	}

	public String getError() {
		return error;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFailure(String failure) {
		this.failure = failure;
		if(failure.equals("true"))
			failedAssertion = true;
	}

	public void setError(String error) {
		this.error = error;
		if(error.equals("true"))
			failedAssertion = true;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
		if(failure.equals("true"))
			failedAssertion = true;
	}
}

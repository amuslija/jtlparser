package com.atlantbh.jmeter.plugins.jtlparser.model.jtl;

import java.util.ArrayList;

public class Sampler {

	private String samplerName;
	private String name;
	private String time;
	private StringBuilder failureMessage = new StringBuilder();
	private String responseCode;
	private String responseMessage;
	private ArrayList<AssertionResult> assertionResults = new ArrayList<AssertionResult>();
	private boolean failedAssertions;

	public Sampler() {
		super();
		failedAssertions = false;
	}
	public String getFailureMessage(){
		return failureMessage.toString();
	}
	public String getSamplerName() {
		return samplerName;
	}

	public String getName() {
		return name;
	}

	public String getTime() {
		return time;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setSamplerName(String samplerName) {
		this.samplerName = samplerName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
		if(!responseCode.matches("2\\d\\d")) {
			failedAssertions = true;
			failureMessage.append("Response Code: " + responseCode + "; ");
		}
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public ArrayList<AssertionResult> getAssertionResults() {
		return assertionResults;
	}

	public void addAssertionResult(AssertionResult assertionResult){
		assertionResults.add(assertionResult);
		boolean addAssertionName = false;
		if(assertionResult.isFailure())
			failureMessage.append("|" + assertionResult.getName() + ": ");
		if(assertionResult.getFailure().equals("true")){
			failureMessage.append("Failure: true; ");
			failedAssertions = true;
		}
		if(assertionResult.getError().equals("true")){

			failureMessage.append("Error: true; ");
			failedAssertions = true;
		}
		if(!assertionResult.getFailureMessage().equals("")){
			failureMessage.append("Failure message: " + assertionResult.getFailureMessage() + "; ");
			failedAssertions = true;
		}
	}

	public boolean hasFailedAssertions(){
		return failedAssertions;
	}
}

package com.atlantbh.jmeter.plugins.jtlparser.model.jtl;

import java.util.ArrayList;

public class ThreadGroup {

	private String threadName;
	private int time;
	private ArrayList<Sampler> samplers = new ArrayList<Sampler>();

	public ThreadGroup(){
		super();
		time = 0;
	}

	public ArrayList<Sampler> getSamplersWithFailures() {
		ArrayList<Sampler> failures = new ArrayList<Sampler>();
		for(int i = 0; i < samplers.size() ; i++) {
			if(samplers.get(i).hasFailedAssertions())
				failures.add(samplers.get(i));
		}
		return failures;
	}

	public String getThreadName() {
		return threadName;
	}

	public ArrayList<Sampler> getSamplers() {
		return samplers;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public void addSampler(Sampler sampler) {
		this.samplers.add(sampler);
		int samplerTime = Integer.parseInt(sampler.getTime());
		time += samplerTime;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}

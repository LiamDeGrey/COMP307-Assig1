package main;

import logic.Data;

public class DataProcessor {
	private Data trainingData;
	private Data testData;

	public DataProcessor(Data trainingData, Data testData) {
		this.trainingData = trainingData;
		this.testData = testData;
	}

}

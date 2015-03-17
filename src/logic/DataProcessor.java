package logic;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


public class DataProcessor {
	private DataMap trainingData;
	private DataMap testData;

	private double range = 10.0;

	public DataProcessor(DataMap trainingData, DataMap testData) {
		this.trainingData = trainingData;
		this.testData = testData;
	}

	private void classifyIris() {
		SortedMap<String, Double> closestDistances;
		double currentDistance;
		double minDistance;

		for (Map.Entry<String, double[]> testFlower : testData.entrySet()) {

			closestDistances = new TreeMap<String, Double>();

			for (Map.Entry<String, double[]> trainingFlower : trainingData.entrySet()) {

				currentDistance = getDistance(testFlower, trainingFlower);

				if (closestDistances.size() < 4) {
					closestDistances.put(trainingFlower.getKey(), currentDistance);
				} else {
					for (Map.Entry<String, Double> closestDistance : closestDistances.entrySet()) {
						if (currentDistance < closestDistance.getValue()) {
							closestDistances.remove(closestDistance);
							closestDistances.put(trainingFlower.getKey(), currentDistance);
						}
					}
				}

			}
		}
	}

	private Double getDistance(Map.Entry<String, double[]> testFlower, Map.Entry<String, double[]> trainingFlower) {
		double value = 0.0;
		for (int i = 0; i < testFlower.getValue().length; i++) {
			value += calculateIndividualSection(testFlower.getValue()[i], trainingFlower.getValue()[i]);
		}

		return Math.sqrt(value);
	}

	private Double calculateIndividualSection(double testFlowerValue, double trainingFlowerValue) {
		return (Math.pow((testFlowerValue - trainingFlowerValue), 2)) / (Math.pow(range, 2));
	}
}

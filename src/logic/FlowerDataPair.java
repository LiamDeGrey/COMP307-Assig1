package logic;

import java.util.ArrayList;

public class FlowerDataPair<String, Double> {
	private String flower;
	private double[] values;

	public FlowerDataPair(String flower, double[] values) {
		this.flower = flower;
		this.values = values;
	}

	public String getFlower() {
		return flower;
	}

	public double[] getValues() {
		return values;
	}

	public double getDistanceTo(FlowerDataPair comparisonFlower) {
		double value = 0.0;
		ArrayList<double[]> individualRanges = DataProcessor.getRanges();
		for (int i = 0; i < values.length; i++) {
			value += calculateIndividualSection(values[i], comparisonFlower.getValues()[i], individualRanges.get(i)[DataProcessor.RANGE]);
		}

		return Math.sqrt(value);
	}

	private double calculateIndividualSection(double testFlowerValue, double trainingFlowerValue, double range) {
		return (Math.pow((testFlowerValue - trainingFlowerValue), 2)) / (Math.pow(range, 2));
	}
}

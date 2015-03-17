package logic;

public class FlowerDataPair<String, Double> {
	private static final int RANGE = 10;

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
		for (int i = 0; i < values.length; i++) {
			value += calculateIndividualSection(values[i], comparisonFlower.getValues()[i]);
		}

		return Math.sqrt(value);
	}

	private double calculateIndividualSection(double testFlowerValue, double trainingFlowerValue) {
		return (Math.pow((testFlowerValue - trainingFlowerValue), 2)) / (Math.pow(RANGE, 2));
	}
}

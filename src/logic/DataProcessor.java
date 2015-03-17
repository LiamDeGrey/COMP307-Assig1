package logic;


public class DataProcessor {
	private DataMap trainingData;
	private DataMap testData;

	private double range = 10.0;

	public DataProcessor(DataMap trainingData, DataMap testData) {
		this.trainingData = trainingData;
		this.testData = testData;
	}

	private void classifyIris() {
		double currentDistance;
		FlowerDistancePair<String, double[]>[] closestFlowers = new FlowerDistancePair[4];


		for (FlowerDataPair<String, double[]> testFlower : testData) {
			for (FlowerDataPair<String, double[]> trainingFlower : trainingData) {
				currentDistance = testFlower.getDistanceTo(trainingFlower);
				for (int i = 0; i < closestFlowers.length; i++) {
					if (closestFlowers[i] == null) {
						closestFlowers[i] = new FlowerDistancePair(trainingFlower.getFlower(), currentDistance);
						break;
					} else if (closestFlowers[i].getDistance() > currentDistance) {
						closestFlowers[i] = new FlowerDistancePair(trainingFlower.getFlower(), currentDistance);
						//TODO reorder flower pairs largest to smallest
						break;
					}
				}
			}
		}
	}
}

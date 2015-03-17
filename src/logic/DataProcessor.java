package logic;


public class DataProcessor {
	private static final int NUMBER_FLOWER_TYPES = 3;
	private static final int K = 5;

	private static final int SETOSA = 0;
	private static final int VERSICOLOR = 1;
	private static final int VIRGINICA = 2;


	private DataMap trainingData;
	private DataMap testData;

	private double range = 10.0;

	public DataProcessor(DataMap trainingData, DataMap testData) {
		this.trainingData = trainingData;
		this.testData = testData;
		classifyIris();
	}

	private void classifyIris() {
		double currentDistance;
		String probableFlower;
		FlowerDistancePair<String, Double>[] closestFlowers;


		for (FlowerDataPair<String, double[]> testFlower : testData) {
			closestFlowers = new FlowerDistancePair[K];
			for (FlowerDataPair<String, double[]> trainingFlower : trainingData) {
				currentDistance = testFlower.getDistanceTo(trainingFlower);
				for (int i = 0; i < closestFlowers.length; i++) {
					if (closestFlowers[i] == null) {
						closestFlowers[i] = new FlowerDistancePair<String, Double>(trainingFlower.getFlower(), currentDistance);
						closestFlowers = reorderFlowers(closestFlowers);
						break;
					} else if (closestFlowers[i].getDistance() > currentDistance) {
						closestFlowers[i] = new FlowerDistancePair<String, Double>(trainingFlower.getFlower(), currentDistance);
						closestFlowers = reorderFlowers(closestFlowers);
						break;
					}
				}
			}
			probableFlower = getMostLikelyFlower(closestFlowers);
			trainingData.add(new FlowerDataPair<String, double[]>(probableFlower, testFlower.getValues()));
			System.out.println(probableFlower);
		}
	}

	private String getMostLikelyFlower(FlowerDistancePair<String, Double>[] closestFlowers) {
		int[] flowerCount = new int[NUMBER_FLOWER_TYPES];
		for (FlowerDistancePair<String, Double> flowerPair : closestFlowers) {
			switch ((String)flowerPair.getFlower()) {
			case "Iris-setosa":
				flowerCount[SETOSA] += 1;
				break;
			case "Iris-versicolor":
				flowerCount[VERSICOLOR] += 1;
				break;
			case "Iris-virginica":
				flowerCount[VIRGINICA] += 1;
				break;
			}
		}
		int max = 0;
		String probableFlower = "UNKNOWN";
		for (int i = 0; i < flowerCount.length; i++) {
			if (flowerCount[i] > max) {
				switch (i) {
				case SETOSA:
					probableFlower = "Iris-setosa";
					break;
				case VERSICOLOR:
					probableFlower = "Iris-versicolor";
					break;
				case VIRGINICA:
					probableFlower = "Iris-virginica";
					break;
				default:
					probableFlower = "UNKNOWN";
					break;
				}
			}
		}

		return probableFlower;
	}

	private FlowerDistancePair<String, Double>[] reorderFlowers(FlowerDistancePair<String, Double>[] closestFlowers) {
		int minimumIndex;
		FlowerDistancePair<String, Double> temp;
		for (int i = 0; i < closestFlowers.length; i++) {
			minimumIndex = i;
			for (int j = i; j < closestFlowers.length; j++) {
				if (closestFlowers[j] != null && closestFlowers[j].getDistance() < closestFlowers[minimumIndex].getDistance()) {
					minimumIndex = j;
				}
			}
			temp = closestFlowers[i];
			closestFlowers[i] = closestFlowers[minimumIndex];
			closestFlowers[minimumIndex] = temp;
		}
		return closestFlowers;
	}

	private void printAllPossibilities(FlowerDistancePair<String, Double>[] closestFlowers) {
		for (FlowerDistancePair<String, Double> flowerPair : closestFlowers) {
			System.out.println(flowerPair.getFlower());
		}
	}
}

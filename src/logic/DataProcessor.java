package logic;

import java.util.ArrayList;


public class DataProcessor {
	public static final int COUNT = 0;
	public static final int TOTAL = 1;
	private static final int NUMBER_FLOWER_TYPES = 3;
	private static final int K = 3;
	private static final int SETOSA = 0;
	private static final int VERSICOLOR = 1;
	private static final int VIRGINICA = 2;

	private static ArrayList<double[]> individualRanges = new ArrayList<double[]>();
	private double amountCorrect = 0.0;
	private DataMap trainingData;
	private DataMap testData;

	public DataProcessor(DataMap trainingData, DataMap testData) {
		this.trainingData = trainingData;
		this.testData = testData;
		getInitialRanges();
		classifyIris();
	}

	private void getInitialRanges() {
		for (FlowerDataPair trainingFlower : trainingData) {
			for (int i = 0; i < trainingFlower.getValues().length; i++) {
				if (individualRanges.size() <= i) {
					individualRanges.add(new double[] {1, trainingFlower.getValues()[i]});
				} else {
					individualRanges.get(i)[COUNT] += 1;
					individualRanges.get(i)[TOTAL] += trainingFlower.getValues()[i];
				}
			}
		}
	}

	private void classifyIris() {
		double currentDistance;
		String probableFlower;
		FlowerDistancePair<String, Double>[] closestFlowers;

		//Iterate through all test flowers
		for (FlowerDataPair<String, double[]> testFlower : testData) {
			closestFlowers = new FlowerDistancePair[K];

			//Iterate through all training flowers for each test flower
			for (FlowerDataPair<String, double[]> trainingFlower : trainingData) {
				currentDistance = testFlower.getDistanceTo(trainingFlower);
				for (int i = 0; i < closestFlowers.length; i++) {
					//find the closest flowers and add them to our closestFlowers array
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
			amountCorrect += (probableFlower.equals(testFlower.getFlower()))? 1 : 0;
			trainingData.add(new FlowerDataPair<String, double[]>(probableFlower, testFlower.getValues()));
			updateIndividualRanges(testFlower.getValues());
			System.out.println("Found : "+probableFlower+", Expected : "+testFlower.getFlower());
		}
		printPercentageCorrect();
	}

	private void printPercentageCorrect() {
		double percentage = amountCorrect / testData.size();
		System.out.println("Classification Accuracy = "+percentage);
	}

	private void updateIndividualRanges(double[] newTrainingFlower) {
		for (int i = 0; i < newTrainingFlower.length; i++) {
			individualRanges.get(i)[COUNT] += 1;
			individualRanges.get(i)[TOTAL] += newTrainingFlower[i];
		}
	}

	public static ArrayList<double[]> getRanges() {
		return individualRanges;
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
}

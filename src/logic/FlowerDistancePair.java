package logic;

public class FlowerDistancePair<String, Double> {
	private String flower;
	private double distance;

	public FlowerDistancePair(String flower, double distance) {
		this.flower = flower;
		this.distance = distance;
	}

	public String getFlower() {
		return flower;
	}

	public double getDistance() {
		return distance;
	}

	public boolean closerThan(FlowerDistancePair<String, Double> pair) {
		if (distance < pair.getDistance()) {
			return true;
		} else {
			return false;
		}
	}
}

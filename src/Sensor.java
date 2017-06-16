
/**
 * An enum with the types of sensors, the number of times they can be applied,
 * and their benefit.
 * 
 * @author thomaskennedy
 */
public enum Sensor {
	F_L_CAMERA(2, 2), F_R_CAMERA(2, 2), F_RADAR(1, 4), B_RADAR(1, 4), LIDAR(3, 1);

	private int benefit;
	private int numberOfTimesPerInterval;

	private Sensor(int benefit, int numberOfTimesPerInterval) {
		this.benefit = benefit;
		this.numberOfTimesPerInterval = numberOfTimesPerInterval;
	}

	public int getBenefit() {
		return benefit;
	}

	public int getNumberOfTimesPerInterval() {
		return numberOfTimesPerInterval;
	}
}
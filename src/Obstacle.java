
/**
 * The obstacle enum contains the type of obstacle, which sensors apply and
 * whether or not the obstacle is a human.
 * 
 * @author thomaskennedy
 */
public enum Obstacle {
	CHILD_ON_LEFT(100, true, Sensor.F_L_CAMERA, Sensor.LIDAR), 
	CHILD_ON_RIGHT(100, true, Sensor.F_R_CAMERA, Sensor.LIDAR), 
	ADULT_ON_LEFT(50, true, Sensor.F_L_CAMERA, Sensor.LIDAR), 
	ADULT_ON_RIGHT(50, true, Sensor.F_R_CAMERA, Sensor.LIDAR), 
	VEHICLE_IN_FRONT(75, false, Sensor.F_RADAR, Sensor.LIDAR), 
	VEHICLE_BEHIND(20, false, Sensor.B_RADAR, Sensor.LIDAR), 
	ONCOMING_VEHICLE(75, false, Sensor.F_RADAR, Sensor.LIDAR), 
	UPCOMING_STOP(80, false, Sensor.F_R_CAMERA), 
	UPCOMING_TRAFFIC_LIGHT(80, false, Sensor.F_L_CAMERA), 
	UPCOMING_POTHOLE(30, false, Sensor.F_L_CAMERA, Sensor.F_R_CAMERA, Sensor.LIDAR), 
	LINES_OF_ROAD(75, false, Sensor.F_L_CAMERA);

	private int value;

	private boolean isHuman;

	private Sensor[] strategies;

	private Obstacle(int value, boolean isHuman, Sensor... strategies) {
		this.value = value;
		this.isHuman = isHuman;
		this.strategies = strategies;
	}

	public int getValue() {
		return value;
	}

	public boolean isHuman() {
		return isHuman;
	}

	public Sensor[] getStrategies() {
		return strategies;
	}
}
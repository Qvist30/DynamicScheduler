
/**
 * An opportunity contains a sensor, a value, an obstacle, and a time.
 * 
 * @author thomaskennedy
 */
public class Opportunity {

	private int time;

	private ObstacleWithDistance obstacleWithDistance;

	private Sensor sensor;

	private int value;

	public Opportunity(int time, ObstacleWithDistance obstacleWithDistance, Sensor sensor, int value) {
		super();
		this.time = time;
		this.obstacleWithDistance = obstacleWithDistance;
		this.sensor = sensor;
		this.value = value;
	}

	public String getSortableName() {
		return "" + obstacleWithDistance.getObstacle() + obstacleWithDistance.getDistance();
	}

	public String getName() {
		return "T" + time + sensor + obstacleWithDistance.getObstacle() + obstacleWithDistance.getDistance();
	}

	public int getTime() {
		return time;
	}

	public ObstacleWithDistance getObstacleWithDistance() {
		return obstacleWithDistance;
	}

	public Sensor getSensor() {
		return sensor;
	}

	public int getValue() {
		return value;
	}

}

import java.util.HashMap;
import java.util.Map;

/**
 * This is just a class to make it easier to print the table at the end of the
 * program demonstrating each of the potential opportunities.
 * 
 * @author thomaskennedy
 */
public class PrintableOpportunity {

	private int distance;
	private Obstacle obstacle;
	private Map<String, Map<Integer, Integer>> opportunityValues = new HashMap<String, Map<Integer, Integer>>();

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setObstacle(Obstacle obstacle) {
		this.obstacle = obstacle;
	}

	public void addTimeWithSensorAndValue(int time, Sensor sensor, int value) {

		Map<Integer, Integer> values;
		if (opportunityValues.containsKey(sensor.toString())) {
			values = opportunityValues.get(sensor.toString());
		} else {
			values = new HashMap<Integer, Integer>();
		}
		values.put(time, value);
		opportunityValues.put(sensor.toString(), values);
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(obstacle + "," + distance + ",");
		boolean firstSensor = true;
		for (String sensor : opportunityValues.keySet()) {
			if (!firstSensor) {
				builder.append("\n,,");
			} else {
				firstSensor = false;
			}
			builder.append(sensor + ",");
			for (int i = 0; i < 5; i++) {
				builder.append(getValue(i, opportunityValues.get(sensor)) + ",");
			}
		}
		return builder.toString();
	}

	private String getValue(int i, Map<Integer, Integer> map) {
		if (map.containsKey(i)) {
			return String.valueOf(map.get(i));
		}
		return "-";
	}

	public int getDistance() {
		// TODO Auto-generated method stub
		return distance;
	}

}

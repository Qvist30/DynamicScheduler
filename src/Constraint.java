
/**
 * Creates a constraint that can be used for Lingo.
 * 
 * @author thomaskennedy
 */
public class Constraint {
	private Opportunity[] opportunities;

	private Sensor sensor;

	private int timeInterval;

	/**
	 * Constructs a constraint
	 * 
	 * @param timeInterval
	 *            The time interval of the constraint.
	 * @param sensor
	 *            The sensor from the constraint.
	 * @param opportunities
	 *            All of the opportunities that are applicable to this
	 *            constraint.
	 */
	public Constraint(int timeInterval, Sensor sensor, Opportunity... opportunities) {
		super();
		this.timeInterval = timeInterval;
		this.opportunities = opportunities;
		this.sensor = sensor;
	}

	/**
	 * Gets the opportunities of the constraint.
	 * 
	 * @return The opportunities of the constraint.
	 */
	public Opportunity[] getOpportunities() {
		return opportunities;
	}

	/**
	 * Gets the sensor from the constraint.
	 * 
	 * @return The sensor.
	 */
	public Sensor getSensor() {
		return sensor;
	}

	/**
	 * Gets the time interval of the constraint.
	 * 
	 * @return The Time interval of the constraint.
	 */
	public int getInterval() {
		return timeInterval;
	}

	/**
	 * Gets the name of the constraint.
	 * 
	 * @return The name of the constraint.
	 */
	public String getName() {
		return sensor + "_T" + timeInterval;
	}

}


/**
 * POJO that contains an obstacle along with its distance from the vehicle at T=0.
 * 
 * @author thomaskennedy
 */
public class ObstacleWithDistance {

	private Obstacle obstacle;

	private int distance;

	public ObstacleWithDistance(Obstacle obstacle, int distance) {
		super();
		this.obstacle = obstacle;
		this.distance = distance;
	}

	public Obstacle getObstacle() {
		return obstacle;
	}

	public int getDistance() {
		return distance;
	}
}

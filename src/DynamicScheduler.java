import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Executing this class will write a random linear program that should be
 * executable in Lingo.
 * 
 * @author thomaskennedy
 *
 */
public class DynamicScheduler {

	/**
	 * The scenario contains the type of weather, the stopping distance, what
	 * type of road the scenario takes place on, and whether or not there are
	 * children in the scenario.
	 */
	public static enum Scenario {
		NICE_20(20, .7, true, false), 
		NICE_30(45, 1.0, true, false), 
		NICE_40(80, 1.4, false, false), 
		NICE_50(125, 1.7, false, false), 
		NICE_60(180, 2.0, false, true), 
		NICE_70(245, 2.4, false, true), 
		BAD_20(40, 1.4, false, false), 
		BAD_30(90, 2, false, false), 
		BAD_40(160, 2.8, false, false), 
		BAD_50(250, 3.4, false, false), 
		BAD_60(360, 4, false, true);

		private int stoppingDistance;

		private double stopTime;

		private boolean hasChildren;

		private boolean isInterstate;

		private Scenario(int stoppingDistance, double stopTime, boolean hasChildren, boolean isInterstate) {
			this.stoppingDistance = stoppingDistance;
			this.stopTime = stopTime;
			this.hasChildren = hasChildren;
			this.isInterstate = isInterstate;
		}

		public int getStoppingDistance() {
			return stoppingDistance;
		}

		public double getStopTime() {
			return stopTime;
		}

		public boolean isHasChildren() {
			return hasChildren;
		}

		public boolean isInterstate() {
			return isInterstate;
		}
	}

	private static Random random = new Random(System.currentTimeMillis());

	public static void main(String[] args) {
		// Pick a Scenario
		Scenario scenario = Scenario.values()[random.nextInt(11)];
		List<ObstacleWithDistance> obstacles = new ArrayList<ObstacleWithDistance>();
		Obstacle obstacle = Obstacle.LINES_OF_ROAD;
		obstacles.add(new ObstacleWithDistance(obstacle, 0));
		while (obstacles.size() < 22) {
			obstacle = Obstacle.values()[random.nextInt(10)];

			if (obstacle == Obstacle.CHILD_ON_LEFT || obstacle == Obstacle.CHILD_ON_RIGHT) {
				if (scenario.hasChildren) {
					obstacles.add(
							new ObstacleWithDistance(obstacle, random.nextInt(5 * scenario.getStoppingDistance())));
				}
			} else if (obstacle == Obstacle.ONCOMING_VEHICLE || obstacle == Obstacle.UPCOMING_STOP
					|| obstacle == Obstacle.ADULT_ON_RIGHT) {
				if (!scenario.isInterstate) {
					obstacles.add(
							new ObstacleWithDistance(obstacle, random.nextInt(5 * scenario.getStoppingDistance())));
				}
			} else {
				obstacles.add(new ObstacleWithDistance(obstacle, random.nextInt(5 * scenario.getStoppingDistance())));
			}
		}
		obstacle = Obstacle.LINES_OF_ROAD;

		obstacles.add(new ObstacleWithDistance(obstacle, scenario.getStoppingDistance()));
		obstacles.add(new ObstacleWithDistance(obstacle, scenario.getStoppingDistance() * 2));
		obstacles.add(new ObstacleWithDistance(obstacle, scenario.getStoppingDistance() * 3));
		obstacles.add(new ObstacleWithDistance(obstacle, scenario.getStoppingDistance() * 4));

		List<Opportunity> opportunities = new ArrayList<Opportunity>();
		List<Constraint> constraints = new ArrayList<Constraint>();

		for (int i = 0; i <= 4; i++) {
			List<Opportunity> opportunitiesForTime = new ArrayList<Opportunity>();
			int stoppingDistance = i * scenario.getStoppingDistance();
			for (ObstacleWithDistance obstacleWithDistance : obstacles) {
				if (obstacleWithDistance.getDistance() >= stoppingDistance) {
					for (Sensor strategy : obstacleWithDistance.getObstacle().getStrategies()) {
						// Double the opportunity value in the last interval.
						Opportunity opportunity;
						if (obstacleWithDistance.getDistance() < stoppingDistance + scenario.getStoppingDistance()) {

							opportunity = new Opportunity(i, obstacleWithDistance, strategy,
									strategy.getBenefit() * obstacleWithDistance.getObstacle().getValue() * 2);
						} else {
							opportunity = new Opportunity(i, obstacleWithDistance, strategy,
									strategy.getBenefit() * obstacleWithDistance.getObstacle().getValue());
						}
						opportunities.add(opportunity);
						opportunitiesForTime.add(opportunity);
					}
				}
			}
			for (Sensor sensor : Sensor.values()) {
				List<Opportunity> opportunitiesForSensor = new ArrayList<Opportunity>();
				for (Opportunity opportunity : opportunitiesForTime) {
					if (opportunity.getSensor() == sensor) {
						opportunitiesForSensor.add(opportunity);
					}
				}
				if (opportunitiesForSensor.size() > 0) {
					constraints.add(new Constraint(i, sensor,
							opportunitiesForSensor.toArray(new Opportunity[opportunitiesForSensor.size()])));
				}
			}
		}
		System.out.print("max ");
		boolean firstTime = true;
		for (Opportunity opportunity : opportunities) {
			if (firstTime) {
				firstTime = false;
			} else {
				System.out.print(" + ");
			}
			System.out.print(opportunity.getValue() + opportunity.getName());

		}
		System.out.println("\nst");

		for (Constraint constraint : constraints) {
			System.out.println("\n");
			boolean firstTimeThrough = true;
			System.out.println(constraint.getName() + ")");
			for (Opportunity opportunity : constraint.getOpportunities()) {
				if (firstTimeThrough) {
					firstTimeThrough = false;
				} else {
					System.out.print(" + ");
				}
				System.out.print(opportunity.getName());

			}

			System.out.print(" < " + constraint.getSensor().getNumberOfTimesPerInterval());

		}

		for (ObstacleWithDistance obstacleWithDistance : obstacles) {
			System.out.println("\n");
			boolean firstTimeThrough = true;
			System.out.print("" + obstacleWithDistance.getObstacle() + obstacleWithDistance.getDistance() + ") ");
			for (Opportunity opportunity : opportunities) {
				if (opportunity.getObstacleWithDistance() == obstacleWithDistance) {
					if (firstTimeThrough) {
						firstTimeThrough = false;
					} else {
						System.out.print(" + ");
					}
					System.out.print(opportunity.getName());
				}

			}

			System.out.print(" < 1");

		}
		System.out.println("");
		for (Opportunity opportunity : opportunities) {

			System.out.println(opportunity.getName() + "0) " + opportunity.getName() + " > 0");

		}
		Map<String, PrintableOpportunity> printableOpportunities = new HashMap<String, PrintableOpportunity>();
		for (Opportunity opportunity : opportunities) {
			PrintableOpportunity opp;
			if (printableOpportunities.get(opportunity.getSortableName()) != null) {
				opp = printableOpportunities.get(opportunity.getSortableName());
			} else {
				opp = new PrintableOpportunity();
				printableOpportunities.put(opportunity.getSortableName(), opp);
			}
			opp.setDistance(opportunity.getObstacleWithDistance().getDistance());
			opp.setObstacle(opportunity.getObstacleWithDistance().getObstacle());
			opp.addTimeWithSensorAndValue(opportunity.getTime(), opportunity.getSensor(), opportunity.getValue());

		}
		System.out.println("Obstacle,Distance,Sensor,T0,T1,T2,T3,T4");
		List<PrintableOpportunity> opps = new ArrayList<PrintableOpportunity>(printableOpportunities.values());
		Comparator<PrintableOpportunity> compator = new Comparator<PrintableOpportunity>() {

			@Override
			public int compare(PrintableOpportunity o1, PrintableOpportunity o2) {

				return o1.getDistance() - o2.getDistance();
			}
		};
		Collections.sort(opps, compator);
		for (PrintableOpportunity opp : opps) {

			System.out.println(opp.toString());
		}
		System.out.println(scenario.toString());
	}

}

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

/**
 * This class runs a simulation of the trains and the people on the trains.
 */
class Simulator {
	
	/**
	 * The scanner to the file containing the train actions.
	 */
	private Scanner trainScanner;

	/**
	 * The scanner to the file containing the person actions.
	 */
	private Scanner peopleScanner;

	/**
	 * A map between car names and car objects.
	 */
	private UniquePairList<String, Car> cars = new UniquePairList<>();

	/**
	 * A map between train names and train objects.
	 */
	private UniquePairList<String, Train> trains = new UniquePairList<>();

	/**
	 * A map between person names and person objects.
	 */
	private UniquePairList<String, Person> people = new UniquePairList<>();

	/**
	 * A set of cars that have been decoupled.
	 */
	private UniqueList<Car> decoupledCars = new UniqueList<>();

	/**
	 * Reads in and sets up the initial train positions.
	 * 
	 * @param trainScanner The scanner to read from.
	 */
	public void setupInitialTrainPositions(Scanner trainScanner) {

		String trainName;
		Train train = null;
		String carName;
		Car c;

		while (trainScanner.hasNextLine()) {

			String line = trainScanner.nextLine();
			if (line.length() == 0) {

				return;
			} else {
				String[] parts = line.split(" ");

				trainName = parts[0];
				train = new Train(trainName);
				trains.append(trainName, train);
				for (String s : parts) {

					if (s.startsWith("C")) {
						carName = s;
						c = new Car(carName);
						cars.append(carName, c);
						train.connectCar(c);
					}
				}

			}

		}
	}

	/**
	 * Initialize the simulator to use specific files for the simulation.
	 * 
	 * @param trainFile  the file containing the train movements.
	 * @param peopleFile the file containing the person movements.
	 * @throws IOException if opening a scanner fails.
	 */
	public Simulator(File trainFile, File peopleFile) throws IOException {
		// open scanners to the files
		this.trainScanner = new Scanner(trainFile);
		this.peopleScanner = new Scanner(peopleFile);
	}

	/**
	 * Runs the entire simulation start to finish.
	 * 
	 * @param murderLocation String representing murder location.
	 * @param murderTimeStr  String representing murder time.
	 * 
	 * @return UniqueList of Person Objects.
	 * @throws IOException      if reading from the files fails.
	 * @throws RuntimeException if anything used by this class throws a runtime
	 *                          exception.
	 */
	public UniqueList<Person> simulate(String murderLocation, String murderTimeStr) throws IOException {
		int nextTrainTime = 0;
		int nextPersonTime = 0;

		String[] parts = murderTimeStr.split(":");
		int murderTime = (Integer.parseInt(parts[0]) * 60) + Integer.parseInt(parts[1]);

		// start the trains and people at their locations
		setupInitialTrainPositions(trainScanner);
		setupInitialPeoplePositions(peopleScanner);

		// both scanners are now past the intialization sections
		// so get the next time trains (and people) need to perform
		// some action
		nextTrainTime = getNextTime(trainScanner); // time in seconds since midnight
		nextPersonTime = getNextTime(peopleScanner); // time in seconds since midnight

		// prints out the world for easy ASCII viewing
		printWorld(0, trains, people, decoupledCars);

		// pause for person
		Scanner input = new Scanner(System.in);
		System.out.println("Hit enter to continue");
		input.nextLine();

		// the scanners have read the times for both files
		while (nextTrainTime != -1 || nextPersonTime != -1) {
			if (nextTrainTime > murderTime && nextPersonTime > murderTime) {
				break;
			}

			if (nextTrainTime != -1 && (nextTrainTime <= nextPersonTime || nextPersonTime == -1)) {
				// do action
				doNextTrainAction(nextTrainTime);

				// print world
				printWorld(nextTrainTime, trains, people, decoupledCars);

				// parse time
				nextTrainTime = getNextTime(trainScanner);
			} else if (nextPersonTime != -1 && (nextPersonTime < nextTrainTime || nextTrainTime == -1)) {
				// do action
				doNextPersonAction(nextPersonTime);

				// print world
				printWorld(nextPersonTime, trains, people, decoupledCars);

				// parse time
				nextPersonTime = getNextTime(peopleScanner);
			}

			System.out.println("Hit enter to continue");
			input.nextLine();
		}
		// close the streams
		trainScanner.close();
		peopleScanner.close();

		return getMurderSuspects(murderLocation);
	}

	/**
	 * Prints the world in beautiful ASCII art.
	 * 
	 * @param time          the time the world is at.
	 * @param trains        the list of trains being simulated.
	 * @param people        the list of people being simulated.
	 * @param decoupledCars the list of cars currently decoupled.
	 */
	public static void printWorld(int time, UniquePairList<String, Train> trains, UniquePairList<String, Person> people,
			UniqueList<Car> decoupledCars) {
		int hour = time / 60;
		int minute = time - (hour * 60);

		System.out.printf("%02d:%02d\n", hour, minute);
		System.out.println();

		System.out.println("Trains:\n");
		for (String name : trains.getKeys()) {
			Train t = trains.getValue(name);
			t.printAscii();
			System.out.println();
		}
		System.out.println();

		System.out.println("Decoupled Cars:\n");
		for (Car c : decoupledCars) {
			c.printAscii();
			System.out.println();
		}
		System.out.println("\n");

		System.out.println("People:");
		for (String name : people.getKeys()) {
			Person p = people.getValue(name);
			System.out.println(p + " on car " + p.getCurrentCar());
		}
		System.out.println();

	}

	/**
	 * Reads in the next train actions from the scanner given.
	 * 
	 * @param time the current time (for use in error messages).
	 */
	public void doNextTrainAction(int time) {
		while (trainScanner.hasNextLine()) {
			String line = trainScanner.nextLine();
			if (line.equals("")) {
				// we're done reading the train actions
				return;
			}

			String[] parts = line.split(" ");

			Train t = trains.getValue(parts[0]);
			// System.out.println(parts[0] + "|" + parts[1] + "|" + parts[2]);
			Car c = cars.getValue(parts[2]);
			// System.out.println("Train " + t + " car " + c);

			if (parts[1].equals("connect")) {
				if (!decoupledCars.contains(c)) {
					int hour = time / 60;
					int minute = time - (hour * 60);
					throw new RuntimeException(
							"[" + hour + ":" + minute + "] Car " + c + " is not in the decoupledCars list");
				}

				t.connectCar(c);
				decoupledCars.remove(c);
			} else {
				t.disconnectCar(c);
				decoupledCars.append(c);
			}
		}
	}

	/**
	 * Reads in the next person actions from the scanner given.
	 * 
	 * @param time the current time (for use in error messages).
	 */
	public void doNextPersonAction(int time) {
		while (peopleScanner.hasNextLine()) {
			String line = peopleScanner.nextLine();
			if (line.equals("")) {
				// we're done reading the people actions.
				return;
			}

			String[] parts = line.split(" ");

			Person p = people.getValue(parts[0]);
			Car c1 = cars.getValue(parts[1]);
			Car c2 = cars.getValue(parts[2]);

			if (!p.getCurrentCar().equals(c1)) {
				int hour = time / 60;
				int minute = time - (hour * 60);
				throw new RuntimeException("[" + hour + ":" + minute + "] Person " + p + " cannot be in car " + c1
						+ " according to the record");
			}

			if (!p.moveToCar(c2)) {
				int hour = time / 60;
				int minute = time - (hour * 60);
				throw new RuntimeException("[" + hour + ":" + minute + "] Person " + p + " cannot move to car " + c2
						+ " according to the record");
			}
		}
	}

	/**
	 * Skips through the file until a time is found and returns that time in seconds
	 * since midnight.
	 * 
	 * @param s the scanner to read from.
	 * @return the next time in seconds since midnight.
	 */
	public int getNextTime(Scanner s) {
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (line.matches("\\d\\d:\\d\\d")) {
				String[] parts = line.split(":");
				return (Integer.parseInt(parts[0]) * 60) + Integer.parseInt(parts[1]);
			}
		}
		return -1;
	}

	/**
	 * Reads in and sets up the initial people positions.
	 * 
	 * @param peopleScanner the scanner to read from.
	 */
	public void setupInitialPeoplePositions(Scanner peopleScanner) {
		// get initial people positions.
		while (peopleScanner.hasNextLine()) {
			// get a line and break it into parts.
			String s = peopleScanner.nextLine();
			if (s.length() == 0) {
				// if the line is blank, we're done reading the people's initial positions.
				return;
			}
			String[] parts = s.split(" ");

			// get the name of the person.
			String personName = parts[0];

			// get the car they start on.
			String carName = parts[1];
			Car c = cars.getValue(carName);

			// create a new person.
			Person p = new Person(personName, c);
			people.append(personName, p); // add to the list of all people.
		}
	}

	/**
	 * Finds the suspects for the murder. You may assume this method is only ever
	 * called after the entire simulation has run (so people and their trains are in
	 * the final positions).
	 * 
	 * @param murderLocation String with the name of the car the murder takes place
	 *                       at.
	 * @return a set of potential murderers.
	 */
	public UniqueList<Person> getMurderSuspects(String murderLocation) {
		// see if anyone was on the murder car.
		Car carObj = cars.getValue(murderLocation);
		UniqueList<Person> peopleOnMurderCar = getPeopleOnCar(carObj);
		if (peopleOnMurderCar.size() > 0) {
			return peopleOnMurderCar;
		}

		UniqueList<Person> peopleNeighborCars = new UniqueList<>();

		// find people on earlier cars.
		Car c = carObj.getPrevious();
		UniqueList<Person> peopleOnPrevCar = null;
		while (c != null) {
			peopleOnPrevCar = getPeopleOnCar(c);
			if (peopleOnPrevCar.size() != 0) {
				break;
			}
			c = c.getPrevious();
		}
		if (peopleOnPrevCar != null) {
			for (Person p : peopleOnPrevCar) {
				peopleNeighborCars.append((Person) p);
			}
		}

		// find people on later cars.
		c = carObj.getNext();
		UniqueList<Person> peopleOnNextCars = null;
		while (c != null) {
			peopleOnNextCars = getPeopleOnCar(c);
			if (peopleOnNextCars.size() != 0) {
				break;
			}
			c = c.getNext();
		}
		if (peopleOnNextCars != null) {
			for (Person p : peopleOnNextCars) {
				peopleNeighborCars.append((Person) p);
			}
		}

		// return combination of each.
		return peopleNeighborCars;
	}

	/**
	 * Returns a set of the people on the car specified.
	 * 
	 * @param carObj Car Object that people are in.
	 * @return UniqueList of Person Objects.
	 */
	@SuppressWarnings("unchecked")
	private UniqueList<Person> getPeopleOnCar(Car carObj) {
		// map people to the cars they are in.
		UniqueList<Person> peopleOnCar = new UniqueList<>();

		for (String name : people.getKeys()) {
			Person p = people.getValue(name);
			if (p.getCurrentCar().equals(carObj)) {
				peopleOnCar.append(p);
			}
		}

		return peopleOnCar;
	}

	/**
	 * This method actually runs the simulator and handles command line args.
	 * 
	 * @param args command line arguments.
	 */
	public static void main(String[] args) {
		if (args.length != 4) {
			System.err.println("Usage: java Simulator [TrainNotes] [PeopleNotes] [CarOfMurder] [TimeOfMurder]");
		}

		String trainFile = args[0];
		String peopleFile = args[1];

		try {
			Simulator s = new Simulator(new File(trainFile), new File(peopleFile));
			UniqueList<Person> suspects = s.simulate(args[2], args[3]);
			if (suspects.size() == 0) {
				System.out.println("No suspects!");
			} else {
				System.out.println("Suspects:");
				for (Person p : suspects) {
					System.out.println(p);
				}
			}
		} catch (IOException e) {
			System.out.println("Invalid file");
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}

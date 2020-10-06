/**
 * Blueprint class to create Person Objects.
 * 
 * @author Jacob Strokus
 *
 */
class Person {

	/**
	 * Private String representing the name of the person.
	 */
	private String name;

	/**
	 * This represents a "has a" relationship. A person has a car that they are in.
	 */
	private Car currentCar;

	/**
	 * Constructor to create Person Objects.
	 * 
	 * @param name       - String representing the name of the person.
	 * @param currentCar - Car Object representing which car the person is in.
	 */
	public Person(String name, Car currentCar) {
		this.name = name;
		this.currentCar = currentCar;

	}

	/**
	 * Method to get the name of the person. This method runs in O(1) time.
	 * 
	 * @return the name set in the constructor.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Method to get the Car that the person is currently in. This method runs in
	 * O(1) time.
	 * 
	 * @return the current car.
	 */
	public Car getCurrentCar() {
		return this.currentCar;
	}

	/**
	 * Method for people to move in between cars. This method runs in O(1) time.
	 * 
	 * @param c Car Object representing the desired Car the person wants to move to.
	 * @return true if person was able to move to the selected Car. Otherwise
	 *         returns false and person stays in current Car.
	 */
	public boolean moveToCar(Car c) {

		if (currentCar.getNext() != null && currentCar.getNext().equals(c)) {

			this.currentCar = c;

		} else if (currentCar.getPrevious() != null && currentCar.getPrevious().equals(c)) {

			this.currentCar = c;

		} else {

			return false;
		}

		return true;
	}

	/**
	 * Method checks to see if passengers have the same name. This method runs in
	 * O(1) time.
	 * 
	 * @param o Object to be compared.
	 * @return true if two people have the same name. Otherwise returns false.
	 */
	public boolean equals(Object o) {
		return this.name.equals(o.toString());
	}

	/**
	 * Method to get the String representation of the person's name. This method
	 * runs in O(1) time.
	 * 
	 * @return String representation of the person's name.
	 */
	public String toString() {
		return this.name;
	}

	/**
	 * Controls the flow of the program. Used for debugging the Person class.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		Car c1 = new Car("C1");
		Car c2 = new Car("C2");
		Car c3 = new Car("C3");

		c1.setNext(c2);
		c2.setPrevious(c1);
		c2.setNext(c3);
		c3.setPrevious(c2);

		Person p1 = new Person("P1", c1);

		if (p1.getCurrentCar().equals(c1) && p1.getName().equals("P1")) {
			System.out.println("Yay 1");
		}

		if (p1.moveToCar(c2) && p1.getCurrentCar().equals(c2) && p1.moveToCar(c1) && p1.getCurrentCar().equals(c1)) {
			System.out.println("Yay 2");
		}

		if (p1.moveToCar(c2) && p1.getCurrentCar().equals(c2) && p1.moveToCar(c3) && p1.getCurrentCar().equals(c3)) {
			System.out.println("Yay 3");
		}
		if (p1.moveToCar(c1)) {
			System.out.println("That should not have happened...go hit buttons.");
		} else {
			System.out.println("Could not move to selected car. It was not adjacent.");
		}

		Person p1b = new Person("P1", c1);
		if (p1.equals(p1b) && !p1.equals(new Person("P2", c1))) {
			System.out.println("Yay 4");
		}
	}
}

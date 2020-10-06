/**
 * Blueprint class to create Car Objects.
 * 
 * @author Jacob Strokus
 *
 */
class Car {

	/**
	 * Pointer to the next Car.
	 */
	private Car next;

	/**
	 * Pointer to the previous Car.
	 */
	private Car prev;

	/**
	 * String representing the data each Car has.
	 */
	private String value;

	/**
	 * Constructor to create Car Objects.
	 * 
	 * @param name - String representing the name of each new Car.
	 */
	public Car(String name) {

		this.value = name;
	}

	/**
	 * Method to get the next Car. This method runs in O(1) time.
	 * 
	 * @return the next car after this one.
	 */
	public Car getNext() {
		return next;

	}

	/**
	 * Method to get the previous Car. This method runs in O(1) time.
	 * 
	 * @return the car before this one.
	 */
	public Car getPrevious() {
		return prev;
	}

	/**
	 * Method to set the next Car. This method runs in O(1) time.
	 * 
	 * @param next sets the car after this one to next (the parameter).
	 */
	public void setNext(Car next) {
		this.next = next;
	}

	/**
	 * Method to set the previous car. This method runs in O(1) time.
	 * 
	 * @param previous sets the car before this one to previous (the parameter).
	 */
	public void setPrevious(Car previous) {
		this.prev = previous;

	}

	/**
	 * Method to get the data in the Car. This method runs in O(1) time.
	 * 
	 * @return the car's name.
	 */
	public String getName() {
		return this.value;
	}

	/**
	 * Method to compare the data in 2 cars to see if they are equal. two cars are
	 * equal if they have the same name. This method runs in O(1) time.
	 * 
	 * @param o Object to be compared.
	 * @return boolean expression if two Car objects are equal or not.
	 */
	public boolean equals(Object o) {
		return this.value.equals(o.toString());
	}

	/**
	 * Method to get the String representation of the data in a Car. This method
	 * runs in O(1) time.
	 * 
	 * @return String representation of the data in a Car.
	 */
	public String toString() {
		return this.value;
	}

	/**
	 * Controls the flow of the program. Used for debugging the Car class.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		Car c1 = new Car("C1");
		Car c2 = new Car("C2");
		Car c3 = new Car("C3");
		Car c4 = new Car("C4");

		c1.setNext(c2);
		c2.setPrevious(c1);
		c2.setNext(c3);
		c3.setPrevious(c2);

		if (c1.getName().equals("C1")) {
			System.out.println("Yay 1");
		}
		if (c3.getName().equals("C3")) {
			System.out.println("C3 named check!");
		}

		if (c1.getNext().equals(c2) && c2.getPrevious().equals(c1)) {
			System.out.println("Yay 2");
		}

		if (c2.getNext().equals(c3) && c3.getPrevious().equals(c2)) {
			System.out.println("C3 linked correctly");
		}

		c3.setNext(c4);
		c4.setPrevious(c3);

		if (c2.getNext().equals(c3) && c3.getPrevious().equals(c2)) {
			System.out.println("C4 linked correctly");
		}

		System.out.println("Linking c4 to c2 and c2 to c4...");
		c4.setPrevious(c2);
		c2.setNext(c4);
		if (c2.getNext().equals(c4) && c4.getPrevious().equals(c2)) {
			System.out.println("linked correctly..");
			System.out.println("C3 is no longer part of the train.");
		} else {
			System.out.println("you messed up. hit buttons until fixed.");
		}

		Car c1b = new Car("C1");
		if (c1.equals(c1b)) {
			System.out.println("Yay 3");
		}

		c1.printAscii();

	}

	/**
	 * Method to print out the Ascii representation of each Car. This method runs in
	 * O(n) time.
	 */
	public void printAscii() {

		Car current = this;
		while (current != null) {
			System.out.print(" _________");
			current = current.getNext();
		}
		System.out.println();

		current = this;
		while (current != null) {
			System.out.print(" | " + String.format("%-5s", current.getName()) + " |");
			current = current.getNext();
		}
		System.out.println();

		current = this;
		while (current != null) {
			System.out.print("-|_______|");
			current = current.getNext();
		}
		System.out.println();

		current = this;
		while (current != null) {
			System.out.print("   o   o  ");
			current = current.getNext();
		}
		System.out.println();
	}
}

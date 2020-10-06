import java.util.Iterator;

/**
 * BLueprint class to create Train Objects that implements the Iterable
 * interface.
 * 
 * @author Jacob Strokus
 *
 */
class Train implements Iterable<Car> {

	/**
	 * String representing the name of the Train.
	 */
	private String name;

	/**
	 * Start of the train.
	 */
	private Car head;

	/**
	 * End of the train.
	 */
	private Car tail;

	/**
	 * Constructor to create Train Objects.
	 * 
	 * @param name String representing the name of the Train.
	 */
	public Train(String name) {
		this.name = name;
	}

	/**
	 * Method returns the name of the train. Method runs in O(1) time.
	 * 
	 * @return returns the train's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to create an Iterator to iterate over the train.
	 * 
	 * @return This method uses an anonymous class to return an Iterator which
	 *         traverses the train from the first car to the last car. Methods run
	 *         in O(1) time.
	 */
	public Iterator<Car> iterator() {

		return new Iterator<Car>() {

			private Car currentCar = head;

			@Override
			public boolean hasNext() {
				return currentCar != null;
			}

			@Override
			public Car next() {
				Car data = currentCar;
				currentCar = currentCar.getNext();
				return data;
			}

		};

	}

	/**
	 * Method checks to see if two Trains are equal. Method runs in O(1) time.
	 * 
	 * @param o Object to be compared.
	 * @return Boolean expression if two Trains have the same name.
	 */
	public boolean equals(Object o) {

		return this.name.equals(o.toString());
	}

	/**
	 * Method to connect a Car to the Train. This method runs in O(n) time.
	 * 
	 * @param c The Car to be connected.
	 */
	public void connectCar(Car c) {

		Car currentCar = tail;
		if (head == null) {
			head = tail = c;
		} else {

			while (currentCar.getNext() != null) {
				currentCar = currentCar.getNext();
			}
			currentCar.setNext(c);
			c.setPrevious(currentCar);

			while (tail.getNext() != null) {
				tail = tail.getNext();
			}

		}

	}

	/**
	 * Method to disconnect a car from the train. Throws RuntimeException if the Car
	 * could not be disconnected. This method runs in O(n) time.
	 * 
	 * @param c Car to be disconnected.
	 * @return The disconnected Car.
	 */
	public Car disconnectCar(Car c) {

		Car current = head;
		if (c.equals(current)) {
			head = tail = null;
		} else {
			while (current != c && current != null) {
				current = current.getNext();
			}

			if (current == null || !current.equals(c)) {
				throw new RuntimeException("Can not disconnect a car that doesn't exist.");
			} else {
				tail = current.getPrevious();
				tail.setNext(null);
			}
		}

		return current;
	}

	/**
	 * Method to reconnect all the cars on the train in the reverse order. This
	 * method runs in O(n) time. (╯°□°）╯︵ ┻━┻
	 */
	public void reverseTrain() {

		Car current = head;
		Car previous = null;
		Car next = null;

		if (head == null || head.getNext() == null) {

		} else {

			while (current != null) {

				next = current.getNext();
				current.setPrevious(current.getNext());
				current.setNext(previous);
				previous = current;
				current = next;

			}
			head = previous;

		}

	}

	/**
	 * Controls the flow of the program. Used for debugging the Train class.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		Car c1 = new Car("C1");
		Car c2 = new Car("C2");

		c1.setNext(c2);
		c2.setPrevious(c1);

		Train t1 = new Train("T1");
		Train t1b = new Train("T1");

		if (t1.getName().equals("T1") && t1.equals(t1b)) {
			System.out.println("Yay 1");
		}

		t1.printAscii();

		t1.connectCar(c1);
		t1.printAscii();

		Car c3 = new Car("C3");
		Car c4 = new Car("C4");
		Car c5 = new Car("C5"); // c5 is never added and is used to check runTimeException.

		c3.setNext(c4);
		c4.setPrevious(c3);

		t1.connectCar(c3);
		t1.printAscii();
		t1.connectCar(c5);
		t1.printAscii();

		System.out.println("\n-------------------\n");
		System.out.println("Disconnecting car c3....");
		t1.disconnectCar(c3);
		t1.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Reconnecting car c3....");
		t1.connectCar(c3);
		t1.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Disconnecting car c2....");
		t1.disconnectCar(c2);
		t1.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Reconnecting car c2....");
		t1.connectCar(c2);
		t1.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Setting t1b to be t1..");
		t1b = t1;
		System.out.println(t1b.toString());
		t1b.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Disconnecting car c1....");
		t1b.disconnectCar(c1);
		t1b.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Reconnecting c1....");
		t1b.connectCar(c1);
		t1b.printAscii();
		System.out.println("\n-------------------\n");
		System.out.println("Reversing train....");
		t1.reverseTrain();
		t1.printAscii();

	}

	/**
	 * toString() method to return the String representation of data.
	 * 
	 * @return String representing the name of the train followed by all the cars
	 *         connected to it. This method runs in O(n) time.
	 */
	public String toString() {
		String s = getName();
		for (Car c : this) {
			s += " " + c;
		}
		return s;
	}

	/**
	 * Method to print out the Ascii representation of each Car. This method runs in
	 * O(n) time.
	 */
	public void printAscii() {
		/*
		 * From: http://www.ascii-art.de/ascii/t/train.txt o O___ _________ _][__|o| |O
		 * O O O| <_______|-|_______| /O-O-O o o
		 */

		System.out.print(String.format("%-4s", getName()) + "o O___");
		for (Car c : this) {
			System.out.print(" _________");
		}
		System.out.println();

		System.out.print("  _][__|o|");
		for (Car c : this) {
			System.out.print(" | " + String.format("%-5s", c.getName()) + " |");
		}
		System.out.println();

		System.out.print(" |_______|");
		for (Car c : this) {
			System.out.print("-|_______|");
		}
		System.out.println();

		System.out.print("  /O-O-O  ");
		for (Car c : this) {
			System.out.print("   o   o  ");
		}
		System.out.println();
	}
}

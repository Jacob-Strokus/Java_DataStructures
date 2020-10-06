import java.util.Iterator;

/**
 * Blueprint class to create generic UniqueList Objects. This class implements
 * the Iterable generic interface.
 * 
 * @author Jacob Strokus
 *
 * @param <T> generic type.
 */
class UniqueList<T> implements Iterable<T> {

	/**
	 * Private nested support class that creates the generic Nodes of the linked
	 * list.
	 * 
	 * @author Jacob Strokus
	 *
	 * @param <T> generic type.
	 */
	private class Node<T> {

		/**
		 * Generic T value
		 */
		T value;
		/**
		 * Reference to the next generic Node.
		 */
		Node<T> next;

		/**
		 * reference to the previous generic Node.
		 */
		Node<T> prev;
	}

	/**
	 * Start of th linked list.
	 */
	private Node<T> head;

	/**
	 * End of the linked list.
	 */
	private Node<T> tail;

	/**
	 * How large the list is. Not needed but nice to have.
	 */
	private int size;

	/**
	 * Method to append items to the end of the linked list. Checks to see if they
	 * item being added would be a duplicate in the list and if so, returns false.
	 * Otherwise returns true. This method runs in O(n) time.
	 * 
	 * @param value The item to be appended.
	 * @return Boolean expression whether the item was added or not.
	 */
	public boolean append(T value) {

		Node<T> temp = new Node<>();
		temp.value = value;

		if (size == 0) {
			head = tail = temp;
			size++;
		} else {
			int counter = 0;
			Node<T> current = head;
			while (current != null) {
				if (current.value.equals(value))
					counter++;
				current = current.next;
			}

			if (counter > 0)
				return false;

			tail.next = temp;
			temp.prev = tail;
			tail = tail.next;
			size++;
		}

		return true;
	}

	/**
	 * Method to remove item from the linked list. This method runs in O(n) time.
	 * 
	 * @param value Item to be removed.
	 * @return Boolean expression whether the item was removed or not.
	 */
	public boolean remove(T value) {

		boolean toReturn = true;
		Node<T> current = head;

		if (size == 0) {
			throw new RuntimeException("No elements in list.");
		} else if (size == 1) {
			head = tail = null;
		} else if (head.value.equals(value)) {
			head = head.next;
			head.prev = null;
		} else if (tail.value.equals(value)) {
			tail = tail.prev;
			tail.next = null;
		} else {

			while (current != null && current.value != value) {
				current = current.next;
			}

			if (current.value == null) { // Couldn't find the item in list.
				toReturn = false;
			} else {
				// sets pointer of car before current to point to the car after current.
				current.prev.next = current.next;
				// sets pointer of car after current to point to car before current.
				current.next.prev = current.prev;

			}

		}
		size--;
		return toReturn;
	}

	/**
	 * Method to get an item in the linked list. This method runs in O(n) time.
	 * 
	 * @param value Item to get.
	 * @return The item. returns null if item could not be found.
	 */
	@SuppressWarnings("unchecked")
	public T get(T value) {

		T itemToReturn = null;
		Node<T> current = head;

		while (current != null && !current.value.equals(value)) {
			current = current.next;
		}

		if (current == null) {
			itemToReturn = null;
		} else if (current.value.equals(value))
			itemToReturn = current.value;

		return itemToReturn;
	}

	/**
	 * Method to check whether an item is in the linked list. This method runs in
	 * O(n) time.
	 * 
	 * @param value The value to be checked.
	 * @return Boolean expression whether the item was in the list.
	 */
	public boolean contains(T value) {

		boolean toReturn = false;
		Node<T> current = head;

		while (current != null && current.value != value) {
			current = current.next;
		}

		if (current == null) {
			toReturn = false;
		} else if (current.value.equals(value)) {
			toReturn = true;
		}

		return toReturn;
	}

	/**
	 * Method to get the size of the list. This method runs in O(1) time.
	 * 
	 * @return Integer representing the size of the linked list.
	 */
	public int size() {
		return size;
	}

	/**
	 * Method to clone the linked list. This method runs in O(n) time.
	 * 
	 * @return Copy of UniqueList.
	 */
	public UniqueList<T> clone() {

		UniqueList<T> copy = new UniqueList<>();

		Node<T> current = head;
		while (current != null) {
			if (copy.size == 0) {
				copy.head = copy.tail = current;
			} else {
				copy.tail.next = current;
				current.prev = copy.tail;
				copy.tail = copy.tail.next;
			}
			current = current.next;
			copy.size++;
		}

		return copy;
	}

	/**
	 * Method creates an Iterator to iterate over the linked list.
	 * 
	 * @return This method uses an anonymous class to return an Iterator which
	 *         traverses the train from the first car to the last car. Methods run
	 *         in O(1) time.
	 */
	public Iterator<T> iterator() {

		return new Iterator<T>() {

			/**
			 * Private Node used as starting point of iterator.
			 */
			private Node<T> current = head;

			/**
			 * Method to check for next node.
			 * 
			 * @return Boolean expression whether there is another node or not.
			 */
			@Override
			public boolean hasNext() {
				return current != null;
			}

			/**
			 * Method to move to the next spot.
			 * 
			 * @return generic value T of the data that was just iterated over.
			 */
			@Override
			public T next() {
				T value = current.value;
				current = current.next;
				return value;
			}

		};

	}

	/**
	 * Helper method for debugging purposes. This method runs in O(1) time.
	 */
	private void clearList() {
		head = tail = null;
		size = 0;
	}

	/**
	 * This method runs in O(n^2) time.
	 * 
	 * @return String representing the data in the linked list.
	 */
	public String toString() {

		String out = "";
		Node<T> current = head;
		while (current != null) {
			out += current.value + " ";
			current = current.next;
		}
		return out;
	}

	/**
	 * Controls the flow of the program.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		UniqueList<String> names = new UniqueList<>();

		if (names.append("Fred") && names.append("Alex") && !names.append("Fred")) {
			System.out.println("Yay 0");
		}

		System.out.println(names.toString());

		if (names.size() == 2 && names.contains("Fred") && names.get("Alex").equals("Alex")) {
			System.out.println("Yay 1");
		}

		if (names.remove("Alex") && names.size() == 1 && names.get("Alex") == null) {
			System.out.println("Yay 2");
		}

		boolean hasEverything = false;
		for (String name : names) {
			if (hasEverything) {
				hasEverything = false;
				break;
			}

			hasEverything = name.equals("Fred");
		}
		if (hasEverything) {
			System.out.println("Yay 3");
		}

		names.append("Carlos");
		names.append("Veronica");
		names.append("Sarah");
		System.out.println(names.toString());
		if (names.remove("Veronica") && !names.contains("Veronica")) {
			System.out.println("Remove works!");
			System.out.println(names.toString());
		} else {
			System.err.println("Removed failed. Go hit more buttons.");
		}

		names.clearList();
		System.out.println("List has been cleared!");
		System.out.println("Current List --> [ " + names.toString() + " ]");

		/**
		 * Blueprint class to create Cat objects.
		 * 
		 * @author Jacob Strokus
		 *
		 */
		class Cat {

			/**
			 * String literal representing the name of a Cat.
			 */
			String name;

			/**
			 * Constructor to create Cat Objects.
			 * 
			 * @param name - name of Cat.
			 */
			public Cat(String name) {
				this.name = name;
			}

			/**
			 * Method to check if two cat's are equal by comparing names.
			 * 
			 * @param o Object to be compared.
			 * @return Boolean expression whether two cats are equal.
			 */
			public boolean equals(Object o) {
				if (o instanceof Cat) {
					Cat c = (Cat) o;
					return c.name.equals(this.name);
				}
				return false;
			}
		}

		UniqueList<Cat> catSet1 = new UniqueList<>();

		Cat c1 = new Cat("Sammy");
		Cat c2 = new Cat("Grouchy");
		catSet1.append(c1);
		catSet1.append(c2);

		if (catSet1.size == 2) {
			System.out.println("Yay 0");
		}

		UniqueList<Cat> catSet2 = catSet1.clone();
		if (catSet1 != catSet2 && catSet1.size() == catSet2.size()) {
			int matched = 0;
			for (Cat c : catSet1) {
				if (catSet2.get(c) == c)
					matched++;
			}
			if (matched == 2) {
				System.out.println("Yay 4");
			}
		}
	}
}

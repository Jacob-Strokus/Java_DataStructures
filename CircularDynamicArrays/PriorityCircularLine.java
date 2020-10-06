/**
 * Blueprint class to create generic PriorityCircularLine Objects. This class
 * extends CircularLine to inherit it's methods, and extends the Comparable<T>
 * Interface to be able to implement natural ordering on all the Objects of each
 * class that implements it.
 * 
 * @author Jacob Strokus
 *
 * @param <T> Generic type.
 */
public class PriorityCircularLine<T extends Comparable<T>> extends CircularLine<T> {

	/**
	 * Default constructor to make Circular line objects with a default capacity of
	 * 50.
	 */
	public PriorityCircularLine() {
		this(50);
	}

	/**
	 * Full constructor to create Circular Line Objects with a variable amount of
	 * spaces. **Subtract one from capacity to always have one empty space**.
	 * 
	 * @param cap - Integer representing the capacity the array will have.
	 */
	@SuppressWarnings("unchecked")
	public PriorityCircularLine(int cap) {
		this.capacity = cap - 1;
		line = (T[]) new Comparable[capacity];
		size = 0;
		start = 0;
		end = capacity;
	}

	/**
	 * Method to insert an element to the end of the array. If size is zero sets end
	 * to -1. Checks to see if the array is full. if so, it doubles the array's
	 * capacity. Checks to see if end has reached the end of the array and if so
	 * sets end to 0. Appends the new element to the end of the array. Loops over
	 * the array and makes necessary "swaps" if needed based on the Priority of the
	 * item in line. Increments size.
	 */
	@Override
	public void insert(T item) {

		if (isEmpty())
			end = -1;
		if (isFull()) {
			doubleCapacity();
		}
		if (end == capacity - 1) {
			end = 0;
		} else {
			end++;
		}

		line[end] = item;
		for (int i = 0; i < capacity - 1; i++) {
			if (line[i] == null || line[i + 1] == null) {
				continue;
			} else if (line[0].compareTo(item) > 0) { // if data in the front of the line is greater than the new value
														// to be added

				T temp = line[i]; // save the item in the front of line
				line[i] = item; // the new item is now in front
				line[end] = temp; // the saved item is now in back

			} else if (line[i].compareTo(line[i + 1]) > 0) { // swaps items if needed
				T temp = line[i];
				line[i] = line[i + 1];
				line[i + 1] = temp;
			}
		}

		size++;

	}

	/**
	 * Method to return the String representation of the data in the waiting line.
	 * 
	 * @return String representation of the data in the waiting line.
	 */
	public String toString() {

		StringBuilder sb = new StringBuilder();

		if (!isEmpty()) {
			sb.append("[");
			for (int i = 0; i < size; i++) {

				if (i < size - 1)
					sb.append(line[i] + ",");
				else {
					sb.append(line[i]);
				}
			} // end for-loop
			sb.append("]");
		} else

			sb.append("[]");

		return sb.toString();
	}

}

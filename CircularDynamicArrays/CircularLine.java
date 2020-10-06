/**
 * Blueprint class that creates generic CircularLine Objects that implement
 * CircularLineInterface.
 * 
 * @author Jacob Strokus
 *
 * @param <T> Generic type.
 */
public class CircularLine<T> implements CircularLineInterface<T> {

	/**
	 * Private Integer representing the arrays actual length.
	 */
	protected int capacity;

	/**
	 * Private generic array called 'line'.
	 */
	protected T[] line;

	/**
	 * Private Integer representing the perceived length of the array by the user.
	 */
	protected int size;

	/**
	 * Private integer representing the start of the line.
	 */
	protected int start;

	/**
	 * Private integer representing the end of the line.
	 */
	protected int end;

	/**
	 * Default constructor to make Circular line objects with a default capacity of
	 * 50.
	 */
	public CircularLine() {
		this(50);
	}

	/**
	 * Full constructor to create Circular Line Objects with a variable amount of
	 * spaces. **Subtract one from capacity to always have one empty space for COVID
	 * social Distancing**.
	 * 
	 * @param cap - Integer representing the capacity the array will have.
	 */
	@SuppressWarnings("unchecked") // deals with annoying generics in Java.
	public CircularLine(int cap) {
		this.capacity = cap - 1;
		line = (T[]) new Comparable[capacity];
		size = 0;
		start = 0;
		end = cap - 1;
	}

	/**
	 * Method to double the capacity if needed by creating a new array twice as big
	 * and populates it with the elements from the current array. Finally sets the
	 * current array to point to the new array.
	 */
	@SuppressWarnings("unchecked") // deals with annoying generics in Java.
	public void doubleCapacity() {
		capacity *= 2;
		capacity += 1;
		T[] newArr = (T[]) (new Comparable[capacity]);

		for (int i = 0; i < size; i++) {
			newArr[i] = line[start];
			start = (start + 1) % line.length;
		}

		start = 0;
		end = size - 1; // sets the end of the line to be at the right index after resizing.
		line = newArr; // sets line to point to the new array.

	}

	/**
	 * Method to insert an element to the end of the array. If size is zero sets end
	 * to -1. Checks to see if the array is full. if so, it doubles the array's
	 * capacity. Checks to see if end has reached the end of the array and if so
	 * sets end to 0. Finally appends the new element to the end of the array.
	 * Increments size.
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
		size++;
	}

	/**
	 * Method to remove the first person waiting in line. Checks to see if the line
	 * is empty and throws a NoElementException if so. Otherwise if start reaches
	 * the end of the array, sets start to 0. Finally saves the first item in the
	 * array and sets that position to null. Increments start and decrements size.
	 */
	@Override
	public T remove() {

		T removedItem;

		if (isEmpty()) {
			// throw NoElementException
			throw new NoElementException("No elements to process.");
		} else {
			if (start >= capacity) {
				start = 0;
			}
			removedItem = line[start];
			line[start] = null; // let the garbage collector do its work.
			start++;
			size--;
		}
		return removedItem;
	}

	/**
	 * Method to clear all the data in the array and reset the size and start to 0.
	 * Loops over every item and sets it to null. Sets the end to be the capacity of
	 * the array.
	 */
	@Override
	public void removeAll() {

		for (int i = 0; i < line.length; i++) {
			line[i] = null;
		}
		size = 0;
		start = 0;
		end = capacity;
	}

	/**
	 * Method to return the String representation of the data in the waiting line.
	 * 
	 * @return String representation of the data in the waiting line.
	 */
	public String toString() {

		int i = 0;
		StringBuilder out = new StringBuilder("");

		if (isEmpty()) {
			out.append("[]").toString();
		} else {
			out.append("[");
			for (i = start; i < capacity + start; i++) {
				if (i >= capacity) {
					i = 0;
				}
				if (line[i] == null) {
					continue;
				} else if (i == end) {
					out.append(line[i]);
					break;
				} else
					out.append(line[i] + ",");
			} // end for-loop
			out.append("]");
		} // end else

		return out.toString();

	}

	/**
	 * Method to get the item in the front of the line. Returns that item.
	 */
	@Override
	public T getFront() {
		if (isEmpty()) {
			// throw NoElementException
			throw new NoElementException("No elements to process.");
		}
		return line[start];
	}

	/**
	 * Method to get the item in the back of the line. Returns that item.
	 */
	@Override
	public T getBack() {
		return line[end];
	}

	/**
	 * Method to get the index of the first person in line.
	 * 
	 * @return Integer representing the first person in line.
	 */
	protected int getStart() {
		return start;
	}

	/**
	 * Method to get the index of the end of the line.
	 * 
	 * @return Integer representing the last spot in line.
	 */
	protected int getEnd() {
		return end;
	}

	/**
	 * Method to get the current capacity of the array.
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Method to get the current size of the array. If the array is empty return
	 * size plus one.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Method to check to see if the array is empty. returns a boolean expression.
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Method to check to see if the array is full. returns a boolean expression.
	 */
	@Override
	public boolean isFull() {
		if (size == capacity) {
			return true;
		}
		return false;
	}
}

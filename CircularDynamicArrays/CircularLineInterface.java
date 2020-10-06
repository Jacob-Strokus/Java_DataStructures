/**
 * Generic Interface for CircularLine class. I can implement this class in any
 * number other of classes to have these methods included and if I want override
 * them for that class's specific purpose.
 * 
 * @author Jacob Strokus
 *
 * @param <T> Generic type.
 */
public interface CircularLineInterface<T> {

	/**
	 * Public method to insert an item of type T into the line.
	 * 
	 * @param t - the item to be inserted.
	 */
	public void insert(T t);

	/**
	 * Public method to remove an item of type T from the line.
	 * 
	 * @return The removed item.
	 */
	public T remove();

	/**
	 * Public method to remove all items from the line..
	 */
	public void removeAll();

	/**
	 * Public method to get the first item in line.
	 * 
	 * @return The first item of type T.
	 */
	public T getFront();

	/**
	 * Public method to get the last item in line.
	 * 
	 * @return The last item of type T.
	 */
	public T getBack();

	/**
	 * Public method to get the total number of items the waiting line can hold.
	 * 
	 * @return Integer representing the line's total capacity.
	 */
	public int getCapacity();

	/**
	 * Public method to get the amount of items in line.
	 * 
	 * @return Integer representing the actual size of the line.
	 */
	public int size();

	/**
	 * Public method to check if the line is empty.
	 * 
	 * @return Boolean expression representing if the line is empty.
	 */
	public boolean isEmpty();

	/**
	 * Public method to check if the line is full.
	 * 
	 * @return Boolean expression representing if the line is full.
	 */
	public boolean isFull();

}

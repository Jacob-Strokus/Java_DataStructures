import java.util.Collection;
import java.util.Set;
import java.util.Iterator;

/**
 * This class represents a Set (an unordered collection with no duplicates).
 * 
 * @author Jacob Strokus
 *
 * @param <E> generic type.
 */
class ThreeTenHashSet<E> implements Set<E> {

	/**
	 * {@inheritDoc}
	 */
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Instance of ThreeTenHashMap to be used in this class.
	 */
	private ThreeTenHashMap<E, E> storage = new ThreeTenHashMap<>(5);

	/**
	 * Add an element to the map.
	 * 
	 * @param e element to be added.
	 * 
	 * @return Boolean whether add was successful.
	 */
	public boolean add(E e) {
		
		return storage.put(e, e) == null;
	}

	/**
	 * Clear the map.
	 */
	public void clear() {
		
		storage = new ThreeTenHashMap<>(5);
	}

	/**
	 * Check if Map contains a specific object.
	 * 
	 * @param o Object to check if in Map.
	 * 
	 * @return Boolean whether object o is in map.
	 */
	public boolean contains(Object o) {
		
		return storage.get(o) != null;
	}

	/**
	 * Check if map is empty.
	 * 
	 * @return Boolean whether map is empty.
	 */
	public boolean isEmpty() {
		
		return size() == 0;
	}

	/**
	 * Remove an object.
	 * 
	 * @param o Object to remove.
	 * 
	 * @return Boolean whether object was removed.
	 */
	public boolean remove(Object o) {
		
		return storage.remove(o) != null;
	}

	/**
	 * Get the size of the map.
	 * 
	 * @return Integer representing the size of the map.
	 */
	public int size() {
		
		return storage.size();
	}

	/**
	 * Method to turn the HashMap into an Array.
	 * 
	 * @return Array of Objects converted from HashMap.
	 */
	public Object[] toArray() {
		
		ThreeTenHashMap.TableEntry[] arr = storage.toArray();
		Object[] ret = new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ret[i] = arr[i].key;
		}
		return ret;
	}

	/**
	 * toString().
	 * 
	 * @return String representation of the data.
	 */
	public String toString() {
		
		return storage.toString();
	}

	/**
	 * Method to create an Iterator to iterate over the set.
	 * 
	 * @return Iterator of generic type E.
	 */
	public Iterator<E> iterator() {
		
		return new Iterator<E>() {
			private Object[] values = toArray();
			private int currentLoc = 0;

			@SuppressWarnings("unchecked")
			public E next() {
				return (E) values[currentLoc++];
			}

			public boolean hasNext() {
				return currentLoc != values.length;
			}
		};
	}
}

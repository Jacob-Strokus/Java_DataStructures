import java.util.Collection; //for returning in the values() function only
import java.util.Map;
import java.util.Set;


//import java.util.ArrayList; //for returning in the values() function only

/**
 * Class to create ThreeTenHashMap objects of generic type that implements the
 * Map interface.
 * 
 * @author Jacob Strokus
 *
 * @param <K> generic type.
 * @param <V> generic type.
 */
class ThreeTenHashMap<K, V> implements Map<K, V> {

	/**
	 * Array of generic Nodes used for table storage.
	 */
	private Node<K, V>[] storage;

	/**
	 * Current number of elements in table.
	 */
	private int numElements = 0;

	/**
	 * Integer representing the original size of the table.
	 */
	private int originalSize;

	/**
	 * Constructor to create the HashMap with given size.
	 * 
	 * @param size Number of slots in the table (storage).
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenHashMap(int size) {

		storage = (Node<K, V>[]) new Node[size];
		originalSize = size;
	}

	/**
	 * Method to reset the table to it's original size it had when constructed. This
	 * method runs in 0(1) time.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {

		storage = (Node<K, V>[]) new Node[originalSize];
	}

	/**
	 * Method to check if the table is empty.
	 * 
	 * @return Boolean expression whether the table is empty or not. This method
	 *         runs in O(1) time.
	 */
	public boolean isEmpty() {

		if (storage == null || numElements == 0 || storage.length < 1) {
			return true;
		}
		return false;
	}

	/**
	 * Method to get the number of slots in the table. This method runs in O(1)
	 * time.
	 * 
	 * @return number of slots.
	 */
	public int getSlots() {

		return storage.length;
	}

	/**
	 * Method to get the size of the table. This method runs in O(1) time.
	 * 
	 * @return Number of elements in the table.
	 */
	public int size() {
		return numElements;
	}

	/**
	 * Method to get a value in the table given a key.
	 * 
	 * @param key The key passed in to to use to get the corresponding value.
	 *
	 * @return V value to be return if matching key. If value not found return null.
	 */
	public V get(Object key) {

		V toReturn = null;

		int index = Math.abs(key.hashCode() % storage.length);

		if (storage[index] == null) {

		} else if (storage[index].entry.key.equals(key)) {

			toReturn = storage[index].entry.value;

		} else {

			Node<K, V> current = storage[index];

			while (current.next != null) {

				current = current.next;

				if (current.entry.key.equals(key)) {

					toReturn = current.entry.value;
				}
			}
		}

		return toReturn;

	}

	/**
	 * Method to get the keys in the table. This method runs in O(n + m) time.
	 * 
	 * @return Set containing all the keys in the table.
	 */
	public Set<K> keySet() {

		ThreeTenHashSet<K> keys = new ThreeTenHashSet<K>();

		for (int i = 0; i < storage.length; i++) {

			Node<K, V> current = storage[i];

			if (current == null) {

				continue;

			} else {

				keys.add(current.entry.key);

				while (current.next != null) {

					current = current.next;
					keys.add(current.entry.key);
				}

			}

		}

		return keys;

	}

	/**
	 * Method to remove a pair from the table.
	 * 
	 * @param key The corresponding key to a value in the map.
	 *
	 * @return V the value of the pair removed. If value not found return null.
	 */
	public V remove(Object key) {

		V valueToReturn = null;

		int index = Math.abs(key.hashCode() % storage.length); // get the index of an item

		if (storage[index] == null) { // null check

		} else if (storage[index].entry.key.equals(key)) { // O(1) check

			valueToReturn = storage[index].entry.value;

			storage[index] = storage[index].next; // correct linked list

			numElements--;

		} else {

			Node<K, V> current = storage[index];

			while (current.next != null) { // O(n) check

				Node<K, V> previous = current;

				current = current.next;

				if (current.entry.key.equals(key)) {

					valueToReturn = current.entry.value;
					previous.next = current.next; // correct linked list
					current = null;
					numElements--;
					break;
				}
			}
		}

		return valueToReturn;
	}

	/**
	 * Method to put elments into the hash table using seperate chaining.
	 * 
	 * @param key   The key associated with a value in a pair.
	 * @param value The value associated with a key in a pair.
	 * @return v The value added to the table.
	 */
	private V putNoExpand(K key, V value) {

		Integer hashedKey = Math.abs(key.hashCode() % storage.length);

		TableEntry<K, V> te = new TableEntry<K, V>(key, value);

		Node<K, V> addToStorage = new Node<K, V>(te);

		V valueToReturn = null;

		if (storage[hashedKey] != null && storage[hashedKey].entry.key.equals(key)) { // if the key already exists

			storage[hashedKey].entry.value = addToStorage.entry.value; // update value

			valueToReturn = storage[hashedKey].entry.value;

		} else {

			if (storage[hashedKey] == null) { // if key does not exist

				storage[hashedKey] = addToStorage; // add new element
				valueToReturn = addToStorage.entry.value;

			} else {

				Node<K, V> current = storage[hashedKey];

				while (current.next != null) { // walk through link list

					current = current.next;

				}
				current.next = addToStorage; // append to the end
			}

			numElements++; // increment counter
			valueToReturn = null;
		}

		return valueToReturn;
	}

	/**
	 * Controls the flow of the program. Used for testing purposes.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		// main method for testing, edit as much as you want
		ThreeTenHashMap<String, String> st1 = new ThreeTenHashMap<>(10);
		ThreeTenHashMap<String, Integer> st2 = new ThreeTenHashMap<>(5);
		ThreeTenHashMap<Integer, Integer> st5 = new ThreeTenHashMap<>(10);

		st1.put("a", "apple");
		st1.put("b", "banana");
		st1.put("banana", "b");
		st1.put("b", "butter");

		if (st1.toString().equals("a:apple\nbanana:b\nb:butter") && st1.toStringDebug().equals(
				"[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: [a:apple]->[banana:b]->null\n[8]: [b:butter]->null\n[9]: null")) {
			System.out.println("Yay 1");
		}

		if (st1.getSlots() == 10 && st1.size() == 3 && st1.get("a").equals("apple")) {
			System.out.println("Yay 2");
		}

		st2.rehash(1);
		st2.put("a", 1);
		st2.put("b", 2);

		if (st2.toString().equals("b:2\na:1") && st2.toStringDebug().equals("[0]: [b:2]->null\n[1]: [a:1]->null")
				&& st2.put("e", 3) == null && st2.put("y", 4) == null && st2.toString().equals("a:1\ne:3\ny:4\nb:2")
				&& st2.toStringDebug()
						.equals("[0]: null\n[1]: [a:1]->[e:3]->[y:4]->null\n[2]: [b:2]->null\n[3]: null")) {
			System.out.println("Yay 3");
		}

		if (st2.remove("e").equals(3) && st2.rehash(8) == true && st2.size() == 3 && st2.getSlots() == 8
				&& st2.toString().equals("a:1\ny:4\nb:2") && st2.toStringDebug().equals(
						"[0]: null\n[1]: [a:1]->[y:4]->null\n[2]: [b:2]->null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: null")) {
			System.out.println("Yay 4");
		}

		ThreeTenHashMap<String, String> st3 = new ThreeTenHashMap<>(2);
		st3.put("a", "a");
		st3.remove("a");

		if (st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: null")) {
			st3.put("a", "a");
			if (st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: [a:a]->null")) {
				System.out.println("Yay 5");
			}
		}

		if (st5.put(Integer.MIN_VALUE, 17) == null && !st5.isEmpty() && st5.getSlots() == 10) {
			System.out.println("Yay 6");
		}

		st5.put(1, 17);
		st5.put(11, 18);
		st5.put(2, 36);
		st5.put(4, 12);
		st5.put(Integer.MIN_VALUE, Integer.MIN_VALUE);

		System.out.println("----------------");
		System.out.println(st5.toStringDebug());
		System.out.println("----------------");

		if (st5.size() == 5 && st5.getSlots() == 10) {
			System.out.println("Yay 7");
		}

		st5.remove(4, 12);
		if (st5.size() == 4) {
			System.out.println("Yay 8");
		}

		Set<Integer> keys = st5.keySet();
		keys.forEach(key -> System.out.println(key));

		if (st5.get(11).equals(18)) {
			System.out.println("Yay 9");
		}

		if (st5.remove(4) == null) {
			System.out.println("Yay 10");
		}

		System.out.println(st5.toStringDebug());

		st5.remove(1);
		st5.put(1, 17);
		System.out.println("\n----------\n" + st5.toStringDebug());

	}

	/**
	 * Returns a Collection view of the values contained in this map. The collection
	 * is backed by the map, so changes to the map are reflected in the collection,
	 * and vice-versa.
	 *
	 * @return Collection of values from the hash table.
	 */
	public Collection<V> values() {

		throw new UnsupportedOperationException();
	}

	/**
	 * Copies all of the mappings from the specified map to this map.
	 *
	 * @param m Map that extends generic types K and V.
	 */
	public void putAll(Map<? extends K, ? extends V> m) {
		System.out.println("Error in put all");
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns true if this map maps one or more keys to the specified value. More
	 * formally, returns true if and only if this map contains at least one mapping
	 * to a value v such that (value==null ? v==null : value.equals(v)). This
	 * operation will probably require time linear in the map size for most
	 * implementations of the Map interface.
	 * 
	 * @param value Object to check if the hash table contains.
	 *
	 * @return Boolean whether map maps one or more keys to the specified value.
	 */
	public boolean containsValue(Object value) {
		System.out.println("Error in containsValue()");
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a Set view of the mappings contained in this map. The set is backed
	 * by the map, so changes to the map are reflected in the set, and vice-versa.
	 * 
	 * @return Set of the mappings contained in the map.
	 */
	public Set<Map.Entry<K, V>> entrySet() {
		System.out.println("Error in entryset()");
		throw new UnsupportedOperationException();
	}

	/**
	 * Compares the specified object with this map for equality. Returns true if the
	 * given object is also a map and the two maps represent the same mappings.
	 * 
	 * @param o Object to be compared to. See if two objects are equal.
	 * 
	 * @return Boolean whether two objects are equal.
	 */
	public boolean equals(Object o) {
		System.out.println("Error in equals");
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the hash code value for this map. The hash code of a map is defined
	 * to be the sum of the hash codes of each entry in the map's entrySet() view.
	 * 
	 * @return Integer representing the hashCode of the map.
	 */
	public int hashCode() {
		System.out.println("Error in hashCode()");
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns true if this map contains a mapping for the specified key.
	 * 
	 * @param key Object to check whether the given key is in the hash table.
	 * 
	 * @return Boolean whether the map contains the key.
	 */
	public boolean containsKey(Object key) {
		System.out.println("Error in containsKey()");
		throw new UnsupportedOperationException();
	}

	/**
	 * Inner Node class used to Create Nodes with two generic types. Helper class to
	 * b used in ThreeTenHashMap.
	 * 
	 * @author Jacob Strokus
	 *
	 * @param <K> generic type.
	 * @param <V> generic type.
	 */
	public static class Node<K, V> {
		/**
		 * TableEntry Object used to create new table entries using a key,value pair.
		 * generic in type.
		 */
		public TableEntry<K, V> entry;

		/**
		 * Node Object to reference the next Node. generic in type.
		 */
		public Node<K, V> next;

		/**
		 * Constructor to create Node Objects given an entry.
		 * 
		 * @param entry TableEntry Object.
		 */
		public Node(TableEntry<K, V> entry) {
			this.entry = entry;
		}

		/**
		 * Constructor to create Node Objects given an entry and a reference to the next
		 * Node.
		 * 
		 * @param entry TableEntry Object.
		 * @param next  Reference to the next Node.
		 */
		public Node(TableEntry<K, V> entry, Node<K, V> next) {
			this(entry);
			this.next = next;
		}

		/**
		 * ToString method.
		 * 
		 * @return TableeEntry Object as type String.
		 */
		public String toString() {
			return "[" + entry.toString() + "]->";
		}
	}

	/**
	 * Class to create TableEntry Objects given a key and a value pair.
	 * 
	 * @author Jacob Strokus
	 *
	 * @param <K> generic type.
	 * @param <V> generic type.
	 */
	public static class TableEntry<K, V> {

		/**
		 * generic type representing a key.
		 */
		public K key;

		/**
		 * generic type representing a value.
		 */
		public V value;

		/**
		 * Constructor to create TableEntry Objects given a generic key and generic
		 * value.
		 * 
		 * @param key   Generic type representing a key.
		 * @param value Generic type representing a value.
		 */
		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Constructor to create TableEntry Objects with specified parameters.
		 * 
		 * @param abs Integer representing the key.
		 * @param value2 The value to be stored.
		 */
		public TableEntry(int abs, V value2) {

			
		}

		/**
		 * toString method.
		 * 
		 * @return Key and Value pair as String literal.
		 */
		public String toString() {
			return key.toString() + ":" + value.toString();
		}
	}

	/**
	 * Method to turn a TableEntry into an Array.
	 * 
	 * @return TableEntry Array.
	 */
	public TableEntry[] toArray() {

		TableEntry[] collection = new TableEntry[this.numElements];
		int index = 0;
		for (int i = 0; i < storage.length; i++) {
			if (storage[i] != null) {
				Node<K, V> curr = storage[i];
				while (curr != null) {
					collection[index] = curr.entry;
					index++;
					curr = curr.next;
				}
			}
		}
		return collection;
	}

	/**
	 * toString method.
	 * 
	 * @return String literal representing the data in the table.
	 */
	public String toString() {

		StringBuilder s = new StringBuilder();
		for (int i = 0; i < storage.length; i++) {
			Node<K, V> curr = storage[i];
			if (curr == null)
				continue;

			while (curr != null) {
				s.append(curr.entry.toString());
				s.append("\n");
				curr = curr.next;
			}
		}
		return s.toString().trim();
	}

	/**
	 * Method to debug the table.
	 * 
	 * @return String literal representing the data in the table. Used for
	 *         debugging.
	 */
	public String toStringDebug() {

		StringBuilder s = new StringBuilder();
		for (int i = 0; i < storage.length; i++) {
			Node<K, V> curr = storage[i];

			s.append("[" + i + "]: ");
			while (curr != null) {
				s.append(curr.toString());
				curr = curr.next;
			}
			s.append("null\n");
		}
		return s.toString().trim();
	}

	/**
	 * Method to rehash the current table.
	 * 
	 * @param size Integer representing the number of elements in the table.
	 * @return Boolean whether rehash was success.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {

		if (size < 1)
			return false;

		Node<K, V>[] oldTable = storage;
		storage = (Node<K, V>[]) new Node[size];
		numElements = 0;

		for (Node<K, V> node : oldTable) {
			while (node != null) {
				putNoExpand(node.entry.key, node.entry.value);
				node = node.next;
			}
		}

		return true;
	}

	/**
	 * Associates the specified value with the specified key in this map. If the map
	 * previously contained a mapping for the key, the old value is replaced by the
	 * specified value.
	 * 
	 * @param key The key.
	 * @param value The value.
	 * 
	 * @return The value returned if put succeeded. Otherwise returns null.
	 * 
	 */
	public V put(K key, V value) {

		V ret = putNoExpand(key, value);
		while ((numElements / (double) storage.length) >= 2) {
			rehash(storage.length * 2);
		}
		return ret;
	}
}

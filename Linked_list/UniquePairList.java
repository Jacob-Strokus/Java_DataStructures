/**
 * Driver class to create UniquePairList Objects to test the Pair class. This
 * inner class uses double generic value. One for the key and one for the value.
 * 
 * @author Jacob Strokus
 *
 * @param <K> generic type for key.
 * @param <V> generic type for value.
 */
class UniquePairList<K, V> {

	/**
	 * Private inner static class to instantiate and create Pair Objects.
	 * 
	 * @author Jacob Strokus
	 *
	 * @param <K> Generic K value representing the key in a Pair.
	 * @param <V> Generic V value representing the Value in a Pair.
	 */
	private static class Pair<K, V> {

		/**
		 * generic type representing the key in a Pair.
		 */
		private K key;

		/**
		 * generic type representing the value in a Pair.
		 */
		private V value;

		/**
		 * Constructor to create Pair Objects.
		 * 
		 * @param key   The key to be set.
		 * @param value The value to be set.
		 */
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Method to check if two keys are equal. This method runs in O(1) time.
		 * 
		 * @param o Object being compared.
		 * @return Boolean expression whether two keys are equal or not.
		 */
		@SuppressWarnings("unchecked")
		public boolean equals(Object o) {
			if (o instanceof Pair) {
				return this.key.equals(((Pair<K, V>) o).key);
			}
			return false;
		}

		/**
		 * String representation of the data in a given pair. This method runs in O(1)
		 * time.
		 * 
		 * @return String literal representing the data.
		 */
		public String toString() {
			return "<" + getKey() + "," + getValue() + ">";
		}

		/**
		 * Method to get the key in a Pair. This method runs in O(1) time.
		 * 
		 * @return The key from the pair.
		 */
		public K getKey() {
			return this.key;
		}

		/**
		 * Method to get the value from a Pair. This method runs in O(1) time.
		 * 
		 * @return The value from the pair.
		 */
		public V getValue() {
			return this.value;
		}
	}

	/**
	 * Controls the flow of the program. Used to debug the Pair class.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	public static void main(String[] args) {
		Pair<String, Integer> p1 = new Pair<>("Fred", 1);
		Pair<String, Integer> p2 = new Pair<>("Alex", 1);
		Pair<String, Integer> p3 = new Pair<>("Fred", 2);

		if (p1.getKey().equals("Fred") && p1.getValue() == 1 && p1.equals(p3)) {
			System.out.println("Yay 1");
		}

		if (!p1.equals(p2)) {
			System.out.println("Yay 2");
		}

		// this is actually a test of UniqueList, not UniquePairList
		UniqueList<Pair<String, Integer>> set = new UniqueList<>();
		set.append(p1);
		set.append(p2);
		set.append(p3);

		// get the value from the set that is _equal to_ p3 (in this case, p1)
		Pair<String, Integer> p1fromSet = set.get(p3);
		if (p1fromSet.getValue() == 1) {
			System.out.println("Yay 3");
		}

		for (Pair<String, Integer> p : set) {
			System.out.println("< " + p.getKey() + ", " + p.getValue() + " >");
		}

		System.out.println(set.size());

	}

	/**
	 * Private list of Pairs.
	 */
	private UniqueList<Pair<K, V>> set = new UniqueList<>();

	/**
	 * Method to add Pairs to the end of a list. This method runs in O(n) time.
	 * 
	 * @param key   The key in a pair.
	 * @param value The value in a pair.
	 * @return Boolean expression whether a new pair was added to the list.
	 */
	public boolean append(K key, V value) {
		Pair<K, V> pair = new Pair<>(key, value);
		return set.append(pair);
	}

	/**
	 * Method to check if the list was updated. This method runs in O(n) time.
	 * 
	 * @param key   The key in a pair.
	 * @param value The value in a pair.
	 * @return Boolean expression whether the list was updated or not.
	 */
	public boolean update(K key, V value) {
		Pair<K, V> pair = new Pair<>(key, value);
		if (!remove(key)) {
			return false;
		}
		return set.append(pair);
	}

	/**
	 * Method to remove a pair from the list. This method runs in O(n) time.
	 * 
	 * @param key The key in a pair.
	 * @return Boolean expression whether the Pair was removed or not.
	 */
	@SuppressWarnings("unchecked")
	public boolean remove(K key) {
		Pair<K, V> pair = new Pair<>(key, null);
		return set.remove(pair);
	}

	/**
	 * Method to get the value in a Pair recursively. This method runs in O(n) time.
	 * 
	 * @param key The key in a pair.
	 * @return The value associated with the provided key.
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K key) {
		Pair<K, V> pair = new Pair<>(key, null);
		return set.get(pair).getValue(); // calls itself at the end.
	}

	/**
	 * Method to get the all the keys in the list. This method runs in O(n^2) time.
	 * 
	 * @return a new list containing all the keys.
	 */
	public UniqueList<K> getKeys() {
		UniqueList<K> keySet = new UniqueList<>();
		for (Pair<K, V> p : set) {
			keySet.append(p.getKey());
		}
		return keySet.clone();
	}

	/**
	 * Method to get the size of the linkedList. This method runs in O(1) time.
	 * 
	 * @return Integer representing the size of the list.
	 */
	public int size() {
		return set.size();
	}
}

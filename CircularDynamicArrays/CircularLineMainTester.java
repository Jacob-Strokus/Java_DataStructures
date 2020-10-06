/**
 * Driver class to test CircularLine.
 * 
 * @author Jacob Strokus
 *
 */
public class CircularLineMainTester {

	/**
	 * Controls the flow of the program.
	 * 
	 * @param args Command-line arguments supplied as an Array of String objects.
	 */
	public static void main(String args[]) {
		CircularLine<String> ins = new CircularLine<>(5);

		try {
			System.out.println(ins.isEmpty()); // true
			System.out.println(ins.isFull()); // false
			System.out.println(ins); // []
			System.out.println(ins.getCapacity()); // 4
			System.out.println(ins.size()); // 0
			System.out.println(ins.getEnd()); // 4
			System.out.println(ins.getStart()); // 0
			ins.remove(); // throws No element to process
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		ins.insert("Adams");
		ins.insert("Quency");
		ins.insert("Lincoln");
		ins.insert("Malcom");
		ins.remove();
		ins.remove();
		ins.insert("Brown");
		ins.insert("King");
		System.out.println(ins.isFull()); // true
		System.out.println(ins); // [Lincoln,Malcom,Brown,King]
		System.out.println(ins.getCapacity()); // 4
		System.out.println(ins.size()); // 4
		System.out.println(ins.getEnd()); // 0
		System.out.println(ins.getStart()); // 2

		ins.insert("Lewis");
		System.out.println(ins.isFull()); // false
		System.out.println(ins); // [Lincoln,Malcom,Brown,King,Lewis]
		System.out.println(ins.getCapacity()); // 9
		System.out.println(ins.size()); // 5
		System.out.println(ins.getEnd()); // 4
		System.out.println(ins.getStart()); // 0
		System.out.println(ins.getFront()); // Lincoln
		System.out.println(ins.getBack()); // Lewis

		ins.insert("Liuzzo");
		System.out.println(ins.getCapacity()); // 9
		System.out.println(ins); // [Lincoln,Malcom,Brown,King,Lewis,Liuzzo]
		ins.removeAll();
		System.out.println(ins); // []
		System.out.println(ins.getCapacity()); // 9
		System.out.println(ins.size()); // 0
		System.out.println(ins.getEnd()); // 9
		System.out.println(ins.getStart()); // 0

		CircularLine<Double> ins2 = new CircularLine<>();
		System.out.println(ins2.getCapacity()); // 49
		System.out.println(ins2.size()); // 0
		System.out.println(ins2 instanceof CircularLineInterface); // true;
	}
}

/**
 * Driver class to test code.
 * 
 * @author Jacob Strokus
 *
 */public class PriorityCircularMainTester {
	
	 /**
	 * Controls the flow of the program.
	 * 
	 * @param args Command-line arguments supplied as an Array of String Objects.
	 */
	 public static void main(String args[]) {
		PriorityCircularLine<Integer> ins = new PriorityCircularLine<Integer>(5);
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

		ins.insert(10);
		ins.insert(-23);
		ins.insert(45);
		ins.insert(23);
		ins.remove();
		ins.remove();
		ins.insert(444);
		ins.insert(10);
		System.out.println(ins.isFull()); // true
		System.out.println(ins); // [10,23,45,444]
		System.out.println(ins.getCapacity()); // 4
		System.out.println(ins.size()); // 4
		System.out.println(ins.getEnd()); // 0
		System.out.println(ins.getStart()); // 2

		ins.insert(-256);
		System.out.println(ins.isFull()); // false
		System.out.println(ins); // [-256,10,23,45,444]
		System.out.println(ins.getCapacity()); // 9
		System.out.println(ins.size()); // 5
		System.out.println(ins.getEnd()); // 4
		System.out.println(ins.getStart()); // 0
		System.out.println(ins.getFront()); // -256
		System.out.println(ins.getBack()); // 444

		ins.insert(488);
		System.out.println(ins.getCapacity()); // 9
		System.out.println(ins); // [-256,10,23,45,444,488]
		ins.removeAll();
		System.out.println(ins); // []
		System.out.println(ins.getCapacity()); // 9
		System.out.println(ins.size()); // 0
		System.out.println(ins.getEnd()); // 9
		System.out.println(ins.getStart()); // 0

		PriorityCircularLine<Double> ins2 = new PriorityCircularLine<>();
		System.out.println(ins2.getCapacity()); // 49
		System.out.println(ins2.size()); // 0
		System.out.println(ins2 instanceof CircularLineInterface); // true;
	}
}

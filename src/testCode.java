/**
 * @author Pob
 * This program is simply to get the run time of java code to compare vs. the interpreter of z+-
 */
public class testCode {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a = 0;
		int b = 0;
		
		long startTime = System.currentTimeMillis();
		long iterations = 100000000;
		
		a = 1;
		for (int i = 0; i < iterations; i++) {
			b += a;
			a *= 2;
		}
		a += 1000;

		for (int j = 0; j < iterations; j++) {
			b -= a;
			a += 2;
		}

		System.out.println("final a = " + a);
		System.out.println("final b = " + b);

		
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println("Time elapsed: " + elapsedTime + " milliseconds");
	}

}

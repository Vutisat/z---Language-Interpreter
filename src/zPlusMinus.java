import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author Pob Vutisalchavakul 2/5/2015
 * 
 *         z+- Interpreter I started the program using HashMap which I realize
 *         is not the may the best. Treemap may work better for my situation
 *         since I am printing them out in a sorted order at the end.
 * 
 *         There are parts of code that can be changed into recursion.
 * 
 *         Overall the program works without a problem
 */
public class zPlusMinus {

	ArrayList<String> commands;
	HashMap<String, Integer> values;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new zPlusMinus();

	}

	public zPlusMinus() {
		commands = new ArrayList<String>();

		//This next line is if you want to time your program
		//long startTime = System.currentTimeMillis();
		
		// test method is used to testing interpretation
		// test();
		readFile();
		interpretCode();
		printValues();
		
		//This next section is if you want to time your program
//		long stopTime = System.currentTimeMillis();
//		long elapsedTime = stopTime - startTime;
//		System.out.println("Time elapsed: " + elapsedTime + " milliseconds");
	}

	/**
	 * This method print out the values for each variable by alphabetical order
	 */
	private void printValues() {
		Map<String, Integer> map = new TreeMap<String, Integer>(values);
		Set set2 = map.entrySet();
		Iterator iterator2 = set2.iterator();
		while (iterator2.hasNext()) {
			Map.Entry me2 = (Map.Entry) iterator2.next();
			System.out.print(me2.getKey() + ": ");
			System.out.println(me2.getValue());
		}
	}

	// This method allow to test the code to be interpreted for the correct
	// output
	private void test() {
		int a = 0;
		int b = 0;
		a = 1;
		for (int i = 0; i < 20; i++) {
			b += a;
			a *= 2;
		}
		a += 1000;

		for (int j = 0; j < 20; j++) {
			b -= a;
			a += 2;
		}
		System.out.println("final a = " + a);
		System.out.println("final b = " + b);

	}

	/**
	 * This part of the program read in the file and put each valuable pieces
	 * into an arraylist.
	 */
	private void readFile() {

		Scanner fileScanner = null;

		try {
			fileScanner = new Scanner(new File("prog6.zpm"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String eachLine;

		while (fileScanner.hasNext()) {
			eachLine = fileScanner.nextLine();

			commands.addAll(Arrays.asList((eachLine.split(" "))));

		}

		// Deleting extra spaces
		commands.removeAll(Arrays.asList("", null));
		commands.add("endingProgram");
		commands.add("ProgramEnds");

		// This section will print each section of the code individually that
		// was divided by a space
		// for (int i = 0; i < commands.size(); i++) {
		// System.out.println(commands.get(i).toString());
		// }

	}

	private void interpretCode() {
		values = new HashMap<String, Integer>();

		for (int i = 0; i < commands.size(); i++) {

			// This code takes care of declarations
			if (commands.get(i).equals("DEF")) {
				values.put(commands.get(i + 1), 0);
			}

			// This following part of the code takes care of assignments

			// ///////////////Assign from a number//////////////
			if (values.containsKey(commands.get(i))
					&& commands.get(i - 1).equals(";")
					&& (!(values.containsKey(commands.get(i + 2))))
					&& !(commands.get(i + 4).equals("ENDFOR"))
					|| values.containsKey(commands.get(i))
					&& (!(values.containsKey(commands.get(i + 2))))
					&& !(commands.get(i + 4).equals("ENDFOR"))
					&& commands.get(i - 1).equals("ENDFOR")) {

				if (commands.get(i + 1).equals("=")) {
					values.put(commands.get(i),
							Integer.parseInt(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("+=")) {
					values.put(commands.get(i), values.get(commands.get(i))
							+ Integer.parseInt(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("-=")) {
					values.put(commands.get(i), values.get(commands.get(i))
							- Integer.parseInt(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("*=")) {
					values.put(commands.get(i), values.get(commands.get(i))
							* Integer.parseInt(commands.get(i + 2)));
				}
			}

			// /////////////////Assign from a variable//////////

			if (values.containsKey(commands.get(i))
					&& commands.get(i - 1).equals(";")
					&& (values.containsKey(commands.get(i + 2)) && !(commands
							.get(i + 4).equals("ENDFOR")))
					|| values.containsKey(commands.get(i))
					&& (values.containsKey(commands.get(i + 2)))
					&& !(commands.get(i + 4).equals("ENDFOR"))
					&& commands.get(i - 1).equals("ENDFOR")) {

				if (commands.get(i + 1).equals("=")) {
					values.put(commands.get(i), values.get(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("+=")) {
					values.put(commands.get(i), values.get(commands.get(i))
							+ values.get(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("-=")) {
					values.put(commands.get(i), values.get(commands.get(i))
							- values.get(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("*=")) {
					values.put(commands.get(i), values.get(commands.get(i))
							* values.get(commands.get(i + 2)));
				}
			}

			// Handling For Loops here////
			if (commands.get(i).equals("FOR")) {
				int iterations = Integer.parseInt(commands.get(i + 1));

				for (int j = 0; j < iterations; j++) {

					if ((!values.containsKey(commands.get(i + 2)) && commands
							.get(i - 2).equals("FOR"))
							|| (!values.containsKey(commands.get(i + 2)) && commands
									.get(i - 6).equals("FOR"))) {

						if (commands.get(i + 1).equals("=")) {
							values.put(commands.get(i),
									Integer.parseInt(commands.get(i + 2)));
						}

						if (commands.get(i + 1).equals("+=")) {
							values.put(
									commands.get(i),
									values.get(commands.get(i))
											+ Integer.parseInt(commands
													.get(i + 2)));
						}

						if (commands.get(i + 1).equals("-=")) {
							values.put(
									commands.get(i),
									values.get(commands.get(i))
											- Integer.parseInt(commands
													.get(i + 2)));
						}

						if (commands.get(i + 1).equals("*=")) {
							values.put(
									commands.get(i),
									values.get(commands.get(i))
											* Integer.parseInt(commands
													.get(i + 2)));
						}
					}

					// /////////////////Assign from a variable//////////

					if ((values.containsKey(commands.get(i + 2)) && commands
							.get(i - 2).equals("FOR"))
							|| (values.containsKey(commands.get(i + 2)) && commands
									.get(i - 6).equals("FOR"))) {

						if (commands.get(i + 1).equals("=")) {
							values.put(commands.get(i),
									values.get(commands.get(i + 2)));
						}

						if (commands.get(i + 1).equals("+=")) {
							values.put(
									commands.get(i),
									values.get(commands.get(i))
											+ values.get(commands.get(i + 2)));
						}

						if (commands.get(i + 1).equals("-=")) {
							values.put(
									commands.get(i),
									values.get(commands.get(i))
											- values.get(commands.get(i + 2)));
						}

						if (commands.get(i + 1).equals("*=")) {
							values.put(
									commands.get(i),
									values.get(commands.get(i))
											* values.get(commands.get(i + 2)));
						}
					}

					if (!commands.get(i).equals("ENDFOR")) {
						j--;
					}

					if (commands.get(i).equals("ENDFOR")) {
						i = i - 9;
					}

					i++;
				}
			}

		}

	}
}
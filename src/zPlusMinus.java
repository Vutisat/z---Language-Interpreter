import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Pob Vutisalchavakul z+- Interpreter
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
		
		test();
		readFile();
		interpretCode();
	}

	//This method allow to test the code to be interpreted for the correct output
	private void test() {
		int a = 0;
		int b = 0 ;
		a = 3 ;
		a *= 30 ;
		b += a ;
		b += b ;
		a = a ;
		a = 4 ;
		b *= 2 ;

		System.out.println("a = " + a);
		System.out.println("b = " + b);
	}

	private void readFile() {

		Scanner fileScanner = null;

		try {
			fileScanner = new Scanner(new File("prog1.zpm"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String eachLine;

		while (fileScanner.hasNext()) {
			eachLine = fileScanner.nextLine();

			commands.addAll(Arrays.asList((eachLine.split(" "))));

		}
		
		//Deleting extra spaces
		commands.removeAll(Arrays.asList("", null));

//		 This section will print each section of the code individually that
//		 was divided by a space
//		 for (int i = 0; i < commands.size(); i++) {
//		 System.out.println(commands.get(i).toString());
//		 }

	}

	private void interpretCode() {
		values = new HashMap<String, Integer>();

		for (int i = 0; i < commands.size(); i++) {

			// This code takes care of declarations
			if (commands.get(i).equals("DEF")) {
				values.put(commands.get(i + 1), 0);
			}
						
			// This following part of the code takes care of assignments
			
			/////////////////Assign from a number//////////////
			if (values.containsKey(commands.get(i)) && commands.get(i - 1).equals(";") && (!(values.containsKey(commands.get(i + 2))))) {

				if (commands.get(i + 1).equals("=")) {
					values.put(commands.get(i), Integer.parseInt(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("+=")) {
					values.put(commands.get(i), values.get(commands.get(i)) + Integer.parseInt(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("-=")) {
					values.put(commands.get(i), values.get(commands.get(i)) - Integer.parseInt(commands.get(i + 2)));
				}

				if (commands.get(i + 1).equals("*=")) {
					values.put(commands.get(i),	values.get(commands.get(i)) * Integer.parseInt(commands.get(i + 2)));
				}
			}
			
			///////////////////Assign from a variable//////////
			
			if (values.containsKey(commands.get(i)) && commands.get(i - 1).equals(";") && (values.containsKey(commands.get(i + 2)))) {
				
				if (commands.get(i + 1).equals("=")) {
					values.put(commands.get(i),
							values.get(commands.get(i + 2)) );
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

		}

		System.out.println(values.toString());
	}
}
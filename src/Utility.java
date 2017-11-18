import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 * Utility class to create random string values
 * 
 * @author Krishna Paudel
 *
 */
class Utility {
	//File where we want to save created csv file
	private static final String FILE = "users.csv";
	//Number of recipeients
	private static final int COUNT = 10000000;

	public void createRandomCSV() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(new File(FILE));
		writer.println("Email;FirstName;LastName");
		for (int i = 0; i < COUNT; i++) {
			String firstName = i + "-" + this.getRandomString(10 + (int) (Math.random() * 10)).toUpperCase();
			String lastName = this.getRandomString(5 + (int) (Math.random() * 5)).toUpperCase();
			String email = this.getRandomString(5 + (int) (Math.random() * 5)) + "@" + this.getRandomString(6) + "."
					+ this.getRandomString(3);
			writer.println(email + ";" + firstName + ";" + lastName);
		}
		writer.close();

		System.out.println("DONE..");

	}

	/**
	 * Generates a random with with specified length
	 * @param length
	 * @return
	 */
	private String getRandomString(int length) {
		final String characters = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder result = new StringBuilder();
		while (length > 0) {
			Random rand = new Random();
			result.append(characters.charAt(rand.nextInt(characters.length())));
			length--;
		}
		return result.toString();
	}

	public static void main(String... args) throws Exception {
		new Utility().createRandomCSV();
	}
}

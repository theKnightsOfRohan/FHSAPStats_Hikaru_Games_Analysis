import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SortCSV {
	public static void main(String[] args) {

	}

	public static void printCSV(String[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}

	private static String[][] readCSV(String string) {
		try (BufferedReader br = new BufferedReader(new FileReader(string))) {
			String line = br.readLine();
			String[] columnNames = line.split(",");
			String[][] data = new String[0][columnNames.length];
			line = br.readLine();
			while (line != null) {
				String[] splitLine = line.split(",");
				data = Arrays.copyOf(data, data.length + 1);
				data[data.length - 1] = splitLine;
				line = br.readLine();
			}
			return data;
		} catch (Exception e) {
			System.out.println("Error reading CSV file");
			e.printStackTrace();
		}
		return null;
	}
}

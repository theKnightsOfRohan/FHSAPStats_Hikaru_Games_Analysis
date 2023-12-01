import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

public class AnalyzeCSV {
	public static void main(String[] args) {
		String[][] data = readCSV("tableDataCleaned.csv");

		// Get the column names
		String[] columnNames = data[0];
		System.out.println("Column names: " + Arrays.toString(columnNames));

		String[] organizers = grabAllDifferentValues(data, "Organizer");

		HashMap<String, String[][]> eventsByOrganizer = getEventsByColumnValue(data, "Organizer");

		for (String organizer : organizers) {
			System.out.println("Events by " + organizer + ":");
			System.out.println(Arrays.toString(eventsByOrganizer.get(organizer)[0]));
			System.out.println();
		}

		writeToSeparateCSVs(eventsByOrganizer);
	}

	public static void writeToSeparateCSVs(HashMap<String, String[][]> eventsByOrganizer) {
		for (String organizer : eventsByOrganizer.keySet()) {
			String[][] events = eventsByOrganizer.get(organizer);
			String[] columnNames = events[0];

			writeToCSV(columnNames, events, "Data/" + organizer + ".csv");
		}
	}

	public static void writeToCSV(String[] columnNamesWithOrganizer, String[][] eventsWithOrganizer, String string) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(string))) {
			for (int i = 0; i < columnNamesWithOrganizer.length; i++) {
				bw.write(columnNamesWithOrganizer[i]);
				if (i != columnNamesWithOrganizer.length - 1) {
					bw.write(",");
				}
			}
			bw.newLine();
			for (int i = 0; i < eventsWithOrganizer.length; i++) {
				for (int j = 0; j < columnNamesWithOrganizer.length; j++) {
					bw.write(eventsWithOrganizer[i][j]);
					if (j != columnNamesWithOrganizer.length - 1) {
						bw.write(",");
					}
				}
				bw.newLine();
			}
		} catch (Exception e) {
			System.out.println("Error writing to CSV file");
			e.printStackTrace();
		}
	}

	public static HashMap<String, String[][]> getEventsByColumnValue(String[][] data, String columnName) {
		String[] uniqueValues = grabAllDifferentValues(data, columnName);

		if (uniqueValues == null) {
			return null;
		}

		HashMap<String, String[][]> eventsByValue = new HashMap<String, String[][]>();

		for (String value : uniqueValues) {
			List<String[]> events = new ArrayList<String[]>();
			for (int i = 0; i < data.length; i++) {
				if (data[i][7].equals(value)) {
					events.add(data[i]);
				}
			}
			eventsByValue.put(value, events.toArray(new String[0][0]));
		}

		return eventsByValue;
	}

	public static String[] grabAllDifferentValues(String[][] data, String string) {
		// Get the column names
		String[] columnNames = data[0];

		// Find the column index
		int columnIndex = -1;
		for (int i = 0; i < columnNames.length; i++) {
			if (columnNames[i].equals(string)) {
				columnIndex = i;
				break;
			}
		}

		// If the column was not found, return null
		if (columnIndex == -1) {
			return null;
		}

		List<String> values = new ArrayList<String>();
		for (int i = 1; i < data.length; i++) {
			if (!values.contains(data[i][columnIndex])) {
				values.add(data[i][columnIndex]);
			}
		}
		return values.toArray(new String[0]);
	}

	public static void printAsTable(String[][] data) {
		// Get the column names
		String[] columnNames = data[0];

		// Get the column widths
		int[] columnWidths = new int[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			columnWidths[i] = columnNames[i].length();
		}
		for (int i = 1; i < data.length; i++) {
			for (int j = 0; j < columnNames.length; j++) {
				if (data[i][j].length() > columnWidths[j]) {
					columnWidths[j] = data[i][j].length();
				}
			}
		}

		// Print the column names
		for (int i = 0; i < columnNames.length; i++) {
			System.out.print(columnNames[i]);
			for (int j = 0; j < columnWidths[i] - columnNames[i].length(); j++) {
				System.out.print(" ");
			}
			System.out.print(" | ");
		}
		System.out.println();

		// Print the column dividers
		for (int i = 0; i < columnNames.length; i++) {
			for (int j = 0; j < columnWidths[i]; j++) {
				System.out.print("-");
			}
			System.out.print("-+-");
		}
		System.out.println();

		// Print the data
		for (int i = 1; i < data.length; i++) {
			for (int j = 0; j < columnNames.length; j++) {
				System.out.print(data[i][j]);
				for (int k = 0; k < columnWidths[j] - data[i][j].length(); k++) {
					System.out.print(" ");
				}
				System.out.print(" | ");
			}
			System.out.println();
		}
	}

	public static String[][] readCSV(String string) {
		String[][] data = new String[0][0];
		try (BufferedReader br = new BufferedReader(new FileReader(string))) {
			String line = br.readLine();
			List<String[]> lines = new ArrayList<String[]>();
			while (line != null) {
				lines.add(line.split(","));
				line = br.readLine();
			}
			data = lines.toArray(new String[0][0]);
		} catch (Exception e) {
			System.out.println("Error reading CSV file");
		}
		return data;
	}
}

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

public class AnalyzeCSV {
	public static void main(String[] args) {
		HashMap<String, List<String>> data = getCSVData("tableDataCleaned.csv");

	}

	private static HashMap<String, List<String>> getCSVData(String filePath) {
		HashMap<String, List<String>> data = new HashMap<>();
		List<String[]> rawData = new ArrayList<String[]>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line = reader.readLine();
			while (line != null) {
				String[] splitLine = line.split(",");
				rawData.add(splitLine);
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (String key : rawData.get(0)) {
			data.put(key, new ArrayList<String>());
		}

		for (int i = 1; i < rawData.size(); i++) {
			String[] row = rawData.get(i);
			for (int j = 0; j < row.length; j++) {
				data.get(rawData.get(0)[j]).add(row[j]);
			}
		}

		return data;
	}
}

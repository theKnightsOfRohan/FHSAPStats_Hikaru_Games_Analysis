import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CleanCSV {
	public static void main(String[] args) {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("tableData.csv"))) {
			String line = reader.readLine();
			while (line != null) {
				String[] splitLine = line.split(",");
				if (splitLine.length == 15) {
					data.add(splitLine);
				}
				line = reader.readLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("Cleaned data size: " + data.size());
		try (PrintWriter writer = new PrintWriter(new FileWriter("tableDataCleaned.csv"))) {
			for (String[] rowData : data) {
				for (String colData : rowData) {
					writer.write(colData);
					writer.write(",");
				}
				writer.write("\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

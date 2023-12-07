import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeVisitor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParseHTML {
	public static void main(String[] args) {
		// Read the HTML into a string (text)
		String html = readFileByLine("tableData.html");
		System.out.println("HTML Read!");
		System.out.println("HTML size: " + html.length());

		// Use JSoup to parse the HTML
		Document doc = Jsoup.parse(html);
		System.out.println("HTML parsed!");

		// This looks scary, but all I'm doing is
		// going through the HTML and saving all
		// of the text that isn't whitespace or HTML
		// code
		List<String> data = new ArrayList<>();
		doc.traverse(new NodeVisitor() {
			public void head(Node node, int depth) {
				if (node instanceof TextNode) {
					TextNode textNode = (TextNode) node;
					String text = textNode.text().trim();
					if (!text.isEmpty()) {
						data.add(text);
					}
				}
			}

			public void tail(Node node, int depth) {
				// Do nothing
			}
		});

		System.out.println("Data size: " + data.size());

		// Write the data to a CSV file
		writeSingleToCSV(data);
	}

	private static void writeSingleToCSV(List<String> data) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("tableData.csv"))) {
			StringBuilder sb = new StringBuilder();
			for (String rowData : data) {
				sb.append(rowData);
				sb.append(",");
			}
			writer.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void writeDataToCSV(List<List<String>> data) {
		try (PrintWriter writer = new PrintWriter(new FileWriter("tableData.csv"))) {
			StringBuilder sb = new StringBuilder();
			for (List<String> rowData : data) {
				for (String colData : rowData) {
					sb.append(colData);
					sb.append(",");
				}
				sb.append("\n");
			}
			writer.write(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String readFileByLine(String string) {
		try (BufferedReader br = new BufferedReader(new FileReader(string))) {
			String result = "";
			String line = br.readLine();

			while (line != null) {
				result += line;
				result += "\n";
				line = br.readLine();
			}

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
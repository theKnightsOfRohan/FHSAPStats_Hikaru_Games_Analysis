import java.util.Arrays;

public class CalculateUsingCSV {
	public static void main(String[] args) {
		String[][] data = AnalyzeCSV.readCSV("Data/Sorted/Baku AZE.csv");
		// System.out.println("Average win chance per game: " +
		// getAverage(getWinOrDrawChancePerGame(data, "NakamuraHikaru")));

		data = AnalyzeCSV.readCSV("Data/Sorted/chess.com INT.csv");
		System.out.println("Average win chance per game: " +
				getAverage(getWinOrDrawChancePerGame(data, "NakamuraHikaru")));

		// data = AnalyzeCSV.readCSV("Data/Sorted/Doha QAT.csv");
		// System.out.println("Average win chance per game: " +
		// getAverage(getWinOrDrawChancePerGame(data, "NakamuraHikaru")));

		// data = AnalyzeCSV.readCSV("Data/Sorted/Douglas IOM.csv");
		// System.out.println("Average win chance per game: " +
		// getAverage(getWinOrDrawChancePerGame(data, "NakamuraHikaru")));

		// data = AnalyzeCSV.readCSV("tableDataCleaned.csv");
		// System.out.println("Average win chance per game: " +
		// getAverage(getWinOrDrawChancePerGame(data, "NakamuraHikaru")));
	}

	private static double getAverage(double[] winChancePerGame) {
		double sum = 0;
		for (int i = 1; i < winChancePerGame.length; i++) {
			sum += winChancePerGame[i];
		}

		return sum / (winChancePerGame.length - 1);
	}

	private static double[] getWinOrDrawChancePerGame(String[][] data, String playerName) {

		double[] winChancePerGame = new double[data.length];
		int personindex;

		for (int i = 0; i < data.length; i++) {
			String[] game = data[i];
			if (game[1].equals(playerName)) {
				personindex = 0;
			} else if (game[2].equals(playerName)) {
				personindex = 1;
			} else {
				continue;
			}

			int ratingDifference = Integer.parseInt(game[3]) - Integer.parseInt(game[4]);
			double ratingDifferenceForPlayer = ratingDifference * (personindex == 0 ? 1.0 : -1.0);

			winChancePerGame[i] = getWinOrDrawChance(ratingDifferenceForPlayer);
		}

		return winChancePerGame;
	}

	private static double getWinOrDrawChance(double ratingDifference) {
		// Translate the above python sequence to java and return the win or draw chance
		double winChance = 1.0 / (1.0 + Math.pow(10, (-ratingDifference / 400)));

		System.out.println(winChance);
		return winChance;
	}
}
package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Data {
	private HashMap<String, double[]> flowers;

	public Data(File trainingFile) {
		flowers = new HashMap<String, double[]>();

		try {
			populateMap(trainingFile);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nThere was an issue reading your file");
		}
	}

	private void populateMap(File trainingFile) throws IOException {
		final FileReader readData = new FileReader(trainingFile);
		final BufferedReader reader = new BufferedReader(readData);

		String line;
		String[] split;
		while (!(line = reader.readLine()).equals(null)
				&& !line.isEmpty()) {
			split = line.split("\\s+");
			if (split.length < 5) {
				split = line.split(",");
			}

			flowers.put(split[4], new double[]{parseDouble(split[0]), parseDouble(split[1]), parseDouble(split[2]), parseDouble(split[3])});
			System.out.println(split[4]);
		}
		reader.close();
	}

	private double parseDouble(String str) {
		return Double.parseDouble(str);
	}

}

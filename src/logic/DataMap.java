package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DataMap extends ArrayList<FlowerDataPair<String, double[]>> {

	public DataMap(File file) {
		try {
			populateMap(file);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("\nThere was an issue reading your file");
		}
	}

	private void populateMap(File file) throws IOException {
		final FileReader readData = new FileReader(file);
		final BufferedReader reader = new BufferedReader(readData);

		String line;
		String[] split;
		String name;
		while (!(line = reader.readLine()).equals(null)
				&& !line.isEmpty()) {
			split = line.split("\\s+");
			if (split.length < 5) {
				split = line.split(",");
			}

			name = (split.length > 4)? split[4] : "UNKNOWN";
			add(new FlowerDataPair(name, new double[]{parseDouble(split[0]), parseDouble(split[1]), parseDouble(split[2]), parseDouble(split[3])}));
		}
		reader.close();
	}

	private double parseDouble(String str) {
		return Double.parseDouble(str);
	}

}

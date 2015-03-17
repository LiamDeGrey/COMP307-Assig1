package main;

import java.io.File;

import logic.DataMap;
import logic.DataProcessor;

public class Main {

	public static void main(String[] args) {
		new DataProcessor(new DataMap(new File(args[0])), new DataMap(new File(args[1])));
	}

}

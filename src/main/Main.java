package main;

import java.io.File;

import logic.Data;

public class Main {

	public static void main(String[] args) {
		new DataProcessor(new Data(new File(args[0])), new Data(new File(args[1])));
	}

}

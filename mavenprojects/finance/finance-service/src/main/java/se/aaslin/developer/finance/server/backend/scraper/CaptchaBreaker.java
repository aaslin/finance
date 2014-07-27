package se.aaslin.developer.finance.server.backend.scraper;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

public class CaptchaBreaker {

	Map<Integer, ArrayList<ArrayList<Integer>>> numberMap = new HashMap<Integer, ArrayList<ArrayList<Integer>>>(); 
	
	public CaptchaBreaker() throws Exception {
		setupNumberMap();
	}

	private void setupNumberMap() throws NumberFormatException, IOException {
		InputStream numberStream = CaptchaBreaker.class.getResourceAsStream("/se/aaslin/developer/finance/server/backend/scraper/numbers.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(numberStream));
		
		ArrayList<ArrayList<Integer>> number = new ArrayList<ArrayList<Integer>>();
		String line = "";
		int num = 0;
		while ((line = reader.readLine()) != null) {
			if (line.equals("?")) {
				number = format(number);
				numberMap.put(num++, number);
				number = new ArrayList<ArrayList<Integer>>();
			} else {
				ArrayList<Integer> row = new ArrayList<Integer>();
				for (String character : line.split("")) {
					if (character.equals("")) {
						continue;
					}
					int val = Integer.parseInt(character);
					row.add(val);
				}
				number.add(row);
			}
		}
	}

	public String breakCaptcha(InputStream rawImage) throws IOException {
		BufferedImage image = ImageIO.read(rawImage);
		
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>(image.getWidth());
		
		for (int x = 0; x < image.getWidth(); x++) {
			ArrayList<Integer> column = new ArrayList<Integer>(image.getHeight());
			for (int y = 0; y < image.getHeight(); y++) {
				column.add(0);
			}
			matrix.add(x, column);
		}
		
		for (int x = 0; x < image.getWidth(); x++) {
			ArrayList<Integer> column = matrix.get(x);
			for (int y = 0; y < image.getHeight(); y++) {
				int color = image.getRGB(x, y);
				if (color != 0xFFFFFFFF) {
					column.set(y, 1);
				}
			}
			matrix.set(x, column);
		}
		
//		printMatrix(matrix);

		List<ArrayList<ArrayList<Integer>>> numbers = new ArrayList<ArrayList<ArrayList<Integer>>>();
		ArrayList<ArrayList<Integer>> number;
		while ((number = getNextNumber(matrix)).size() > 0) {
			numbers.add(number);
		}
		
		String numberString = "";
		for (ArrayList<ArrayList<Integer>> n : numbers) {
			numberString += parseNumber(n, numberMap);
		}
		
		return numberString;
	}
	
	private Integer parseNumber(ArrayList<ArrayList<Integer>> n, Map<Integer, ArrayList<ArrayList<Integer>>> numberMap) {
		for(Entry<Integer, ArrayList<ArrayList<Integer>>> numberEntry : numberMap.entrySet()) {
			Integer number = numberEntry.getKey();
			ArrayList<ArrayList<Integer>> candidate = numberEntry.getValue();
			if (n.size() == candidate.size() && n.get(0).size() == candidate.get(0).size()) {
				boolean isSame = true;
				for (int x = 0; x < n.size(); x++) {
					for (int y = 0; y < n.get(0).size(); y++) {
						if (n.get(x).get(y) != candidate.get(x).get(y)) {
							isSame = false;
						}
					}
				}
				if (isSame) {
					return number;
				}
			}
		}
		return null;
	}

	private ArrayList<ArrayList<Integer>> format(ArrayList<ArrayList<Integer>> number) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<Integer> row : number) {
			for (int col = 0; col < row.size(); col++) {
				try {
					result.get(col);
				} catch (IndexOutOfBoundsException e) {
					result.add(col, new ArrayList<Integer>());
				}
				result.get(col).add(row.get(col));
			}
		}
		
		return result;
	}

//	private void printMatrix(ArrayList<ArrayList<Integer>> matrix) {
//		for (int y = 0; y < matrix.get(0).size(); y++) {
//			for (int x = 0; x < matrix.size(); x++) {
//				Integer val = matrix.get(x).get(y);
//				System.out.print(val);
//			}
//			System.out.println();
//		}
//	}

	private ArrayList<ArrayList<Integer>> getNextNumber(ArrayList<ArrayList<Integer>> matrix) {
		ArrayList<ArrayList<Integer>> number = new ArrayList<ArrayList<Integer>>();
		Iterator<ArrayList<Integer>> columnIterator = matrix.iterator();
	
		ArrayList<Integer> column;
		while (columnIterator.hasNext()) {
			column = columnIterator.next();
			columnIterator.remove();
			if (!isColumnEmpty(column)) {
				number.add(column);
				break;
			} else {
				
			}
		}
		while (columnIterator.hasNext()) {
			column = columnIterator.next();
			if (isColumnEmpty(column)) {
				break;
			} else {
				columnIterator.remove();
				number.add(column);
			}
		}
		
		return number;
	}

	private boolean isColumnEmpty(ArrayList<Integer> column) {
		for (Integer cell : column) {
			if (cell.equals(1)) {
				return false;
			}
		}
		
		return true;
	}
}

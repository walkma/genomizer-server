package geo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class TXTParser {

	public static void main(String[] args) {
		ArrayList<ArrayList<String>> infoList = null;
		String path = "/home/dv12/dv12tkn/Downloads/GSE47236_series_matrix.txt";

		try {
			infoList = TXTParser.readFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (ArrayList<String> list : infoList) {
			for (String l : list) {
				if (list.indexOf(l) == 0) {
					System.out.printf("%-30s", l);
				} else {
					System.out.printf("%-90s", l);
				}
			}
			System.out.println();
		}
	}

	public static ArrayList<ArrayList<String>> readFile(String filePath)
			throws IOException {

		ArrayList<ArrayList<String>> infoList = new ArrayList<ArrayList<String>>();
		File f = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(f));

		String line = br.readLine();

		while (line != null) {

			if (equalsSplit(line, "!Series_title")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contributor")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_geo_accession")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_status")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_submission_date")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_last_update_date")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_web_link")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_summary")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_type")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_title")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_characteristics_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_data_processing")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!series_matrix_table_begin")) {
				// Read the next line in the file,
				// thats where the good stuff is
				line = br.readLine();
				if (br != null) {
					addString(infoList, line);
				}
			} else if (equalsSplit(line, "!Sample_supplementary_file_1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_supplementary_file_2")) {
				addString(infoList, line);
			}

			line = br.readLine();
		}

		br.close();

		pushFPTRowsTogether(infoList);

		return infoList;

	}

	private static void addString(ArrayList<ArrayList<String>> infoList,
			String line) {
		// Add a new row to the infoList
		infoList.add(new ArrayList<String>());
		// Split the line of text where there is a tab
		String[] strings = line.split("\t");
		// Add each sub-string to a new "cell"
		for (int i = 0; i < strings.length; i++) {
			infoList.get(infoList.size() - 1).add(formatString(strings[i]));
		}
	}

	private static String formatString(String string) {
		// Replace all ,, with , to make it look nicer
		string = string.replace(",,", ", ");
		// Split the string where there is a "
		// Because all strings are surrounded with ""
		String[] strings = string.split("\"");
		// If there was no text between the two ""
		// Send back an empty string
		if (strings.length == 0) {
			return "";
		}
		return checkIfNotURLToSRAFile(strings[strings.length - 1]);
	}

	private static String checkIfNotURLToSRAFile(String string) {
		// If the string is containing ftp, then the string is
		// an url
		if (string.contains("ftp")) {
			// If the string contains sra, the the string is a
			// url to a .sra file
			if (string.contains("sra")) {
				return string;
			}
			// If not, the url is pointing to an uninteresting file
			// and we remove the url
			return "";
		}
		// If the string is not an url
		return string;

	}

	private static boolean equalsSplit(String str1, String str2) {
		// Check if the first word in str1 is equal to str2
		return str1.split("\t")[0].compareTo(str2) == 0;
	}

	public static void pushFPTRowsTogether(ArrayList<ArrayList<String>> infoList) {

		// Loop through all the values in infoList
		for (int i = 0; i < infoList.size(); i++) {
			for (int j = 0; j < infoList.get(i).size(); j++) {
				// If there is an empty cell
				if (infoList.get(i).get(j).isEmpty()) {
					// Check if the cell below it contains a url to an sra file
					if (infoList.get(i + 1).get(j).contains("sra")) {
						// Copy from the cell below to this cell
						infoList.get(i).set(j, infoList.get(i + 1).get(j));
						// Remove the info from the cell below
						infoList.get(i + 1).set(j, "");

					}
				}
			}
		}

		// Remove all the empty rows
		boolean deleteRow = true;

		for (int i = 0; i < infoList.size(); i++) {
			for (int j = 1; j < infoList.get(i).size(); j++) {
				if (!infoList.get(i).get(j).isEmpty()) {
					deleteRow = false;
				}
			}
			if (deleteRow) {
				infoList.remove(i);
			}
			deleteRow = true;
		}
	}
}

package geo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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
					System.out.printf("%-35s", l);
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
			} else if (equalsSplit(line, "!Series_overall_design")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_type")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contributor")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_sample_id")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_name")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_email")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_phone")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_laboratory")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_institute")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_address")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_city")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_state")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_zip/postal_code")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_contact_country")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_platform_id")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_platform_taxid")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_sample_taxid")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Series_relation")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_title")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_geo_accession")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_status")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_submission_date")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_last_update_date")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_type")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_channel_count")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_source_name_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_organism_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_characteristics_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_molecule_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_extract_protocol_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_taxid_ch1")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_description")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_data_processing")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_platform_id")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_name")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_email")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_phone")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_laboratory")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_institute")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_address")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_city")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_state")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_contact_zip/postal_code")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_instrument_model")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_library_selection")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_library_source")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_library_strategy")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_relation")) {
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
			} else if (equalsSplit(line, "!Sample_supplementary_file_3")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_supplementary_file_4")) {
				addString(infoList, line);
			} else if (equalsSplit(line, "!Sample_supplementary_file_5")) {
				addString(infoList, line);
			}

			line = br.readLine();
		}

		br.close();

		pushFPTRowsTogether(infoList);

		mergeSort(infoList);

		return infoList;

	}

	private static void mergeSort(ArrayList<ArrayList<String>> infoList) {
		if (infoList.size() > 1) {
			int q = infoList.size() / 2;

			ArrayList<ArrayList<String>> leftArray = new ArrayList<ArrayList<String>>(
					infoList.subList(0, q));
			ArrayList<ArrayList<String>> rightArray = new ArrayList<ArrayList<String>>(
					infoList.subList(q, infoList.size()));

			mergeSort(leftArray);
			mergeSort(rightArray);

			merge(infoList, leftArray, rightArray);
		}
	}

	static void merge(ArrayList<ArrayList<String>> infoList,
			ArrayList<ArrayList<String>> leftArray,
			ArrayList<ArrayList<String>> rightArray) {
		int totElem = leftArray.size() + rightArray.size();
		int i, li, ri;
		i = li = ri = 0;
		while (i < totElem) {
			if ((li < leftArray.size()) && (ri < rightArray.size())) {
				if (leftArray.get(li).size() < rightArray.get(ri).size()) {
					infoList.set(i, leftArray.get(li));
					i++;
					li++;
				} else {
					infoList.set(i, rightArray.get(ri));
					i++;
					ri++;
				}
			} else {
				if (li >= leftArray.size()) {
					while (ri < rightArray.size()) {
						infoList.set(i, rightArray.get(ri));
						i++;
						ri++;
					}
				}
				if (ri >= rightArray.size()) {
					while (li < leftArray.size()) {
						infoList.set(i, leftArray.get(li));
						li++;
						i++;
					}
				}
			}
		}
	}

	private static void addString(ArrayList<ArrayList<String>> infoList,
			String line) throws IOException {

		// Split the line of text where there is a tab
		String[] strings = line.split("\t");

		// If the category is already added
		if (infoList.size() > 0
				&& infoList.get(infoList.size() - 1).get(0)
						.equalsIgnoreCase(strings[0])) {
			// Add ;; to the end of the row
			if (infoList.get(infoList.size() - 1).size() == 2) {
				infoList.get(infoList.size() - 1).set(1,
						infoList.get(infoList.size() - 1).get(1) + ";;");
			}
			// Pate on the new string
			for (int i = 1; i < strings.length; i++) {
				infoList.get(infoList.size() - 1).set(
						1,
						infoList.get(infoList.size() - 1).get(1)
								+ formatString(strings[i]));
			}
		} else {
			// Add a new row to the infoList
			infoList.add(new ArrayList<String>());
			// Add each sub-string to a new "cell"
			for (int i = 0; i < strings.length; i++) {
				infoList.get(infoList.size() - 1).add(formatString(strings[i]));
			}
		}
	}

	private static String formatString(String string) throws IOException {
		// Replace all ,, with " " to make it look nicer
		string = string.replace(",,", " ");
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

	private static String checkIfNotURLToSRAFile(String string)
			throws IOException {
		// If the string is containing ftp, then the string is
		// an url
		if (string.contains("ftp")) {
			// If the string contains sra, the the string is a
			// url to a .sra file
			if (string.contains("sra")) {
				return getSRAFromDir(string);
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

	private static String getSRAFromDir(String url) throws IOException {
		url = url + "/";
		// This will get the subfolders from the url
		InputStream is = new URL(url).openStream();
		byte[] buffer = new byte[1024];
		int bytesRead = -1;
		StringBuilder page = new StringBuilder(1024);
		while ((bytesRead = is.read(buffer)) != -1) {
			page.append(new String(buffer, 0, bytesRead));
		}
		String fileInfo = page.toString();

		// Pick out the last word in fileInfo, that is the filename that we want
		String[] temp = fileInfo.split(" ");
		// Then paste the string together
		url = url + temp[temp.length - 1].trim() + "/"
				+ temp[temp.length - 1].trim() + ".sra";

		is.close();

		return url;
	}
}

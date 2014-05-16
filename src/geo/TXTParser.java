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
		ArrayList<GEOFileTuple> infoList = null;
		String path = "/home/dv12/dv12tkn/Downloads/GSE47236_series_matrix.txt";

		try {
			long s = System.currentTimeMillis();
			infoList = TXTParser.readFile(path);
			System.out.println("-----------------------------------");
			System.out.println("It took " + (System.currentTimeMillis() - s)
					+ "ms to get all the data.");
			System.out.println("-----------------------------------");
		} catch (IOException e) {
			e.printStackTrace();
		}

		int i = 0;
		if (true) {
			System.out.println(infoList.get(i).seriesRelation);
			System.out.println(infoList.get(i).seriesSampleTaxid);
			System.out.println(infoList.get(i).seriesPlatformTaxid);
			System.out.println(infoList.get(i).seriesPlatformId);
			System.out.println(infoList.get(i).seriesContactCountry);
			System.out.println(infoList.get(i).seriesContactPostalCode);
			System.out.println(infoList.get(i).seriesContactState);
			System.out.println(infoList.get(i).seriesContactCity);
			System.out.println(infoList.get(i).seriesContactAddress);
			System.out.println(infoList.get(i).seriesContactInstitute);
			System.out.println(infoList.get(i).seriesContactLaboratory);
			System.out.println(infoList.get(i).seriesContactPhone);
			System.out.println(infoList.get(i).seriesContactEmail);
			System.out.println(infoList.get(i).seriesContactName);
			System.out.println(infoList.get(i).seriesSampleId);
			System.out.println(infoList.get(i).seriesContributors[0]);
			System.out.println(infoList.get(i).seriesType);
			System.out.println(infoList.get(i).seriesOverallDesign);
			System.out.println(infoList.get(i).seriesSummary[0]);
			System.out.println(infoList.get(i).seriesWebLink);
			System.out.println(infoList.get(i).seriesLastUpdateDate);
			System.out.println(infoList.get(i).seriesSubmissionDate);
			System.out.println(infoList.get(i).seriesStatus);
			System.out.println(infoList.get(i).seriesGeoAccession);
			System.out.println(infoList.get(i).seriesTitle);
			System.out.println(infoList.get(i).ID_REF);
			System.out.println(infoList.get(i).sampleSupplementaryFile);
			System.out.println(infoList.get(i).sampleRelation);
			System.out.println(infoList.get(i).sampleLibraryStrategy);
			System.out.println(infoList.get(i).sampleLibrarySource);
			System.out.println(infoList.get(i).sampleLibrarySelection);
			System.out.println(infoList.get(i).sampleInstrumentModel);
			System.out.println(infoList.get(i).sampleContactPostalCode);
			System.out.println(infoList.get(i).sampleContactState);
			System.out.println(infoList.get(i).sampleContactCity);
			System.out.println(infoList.get(i).sampleContactAddress);
			System.out.println(infoList.get(i).sampleContactInstitute);
			System.out.println(infoList.get(i).sampleContactPhone);
			System.out.println(infoList.get(i).sampleContactLaboratory);
			System.out.println(infoList.get(i).sampleContactEmail);
			System.out.println(infoList.get(i).sampleContactName);
			System.out.println(infoList.get(i).samplePlatformId);
			System.out.println(infoList.get(i).sampleDataProcessing);
			System.out.println(infoList.get(i).sampleDescription);
			System.out.println(infoList.get(i).sampleTaxid);
			System.out.println(infoList.get(i).sampleExtractProtocol);
			System.out.println(infoList.get(i).sampleMolecule);
			System.out.println(infoList.get(i).sampleCharacteristics);
			System.out.println(infoList.get(i).sampleOrganism);
			System.out.println(infoList.get(i).sampleSourceName);
			System.out.println(infoList.get(i).sampleChannelCount);
			System.out.println(infoList.get(i).sampleType);
			System.out.println(infoList.get(i).sampleLastUpdateDate);
			System.out.println(infoList.get(i).sampleSubmissionDate);
			System.out.println(infoList.get(i).sampleStatus);
			System.out.println(infoList.get(i).sampleGeoAccession);
			System.out.println(infoList.get(i).sampleTitle);
			System.out.println(infoList.get(i).sampleSupplementaryFile);
		}
	}

	public static ArrayList<GEOFileTuple> readFile(String filePath)
			throws IOException {
		ArrayList<GEOFileTuple> infoList = new ArrayList<GEOFileTuple>();
		infoList.add(new GEOFileTuple());
		File f = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(f));

		String line = br.readLine();

		while (line != null) {
			if (equalsSplit(line, "!Series_title")) {
				infoList.get(0).seriesTitle = formatString(line);
			} else if (equalsSplit(line, "!Series_geo_accession")) {
				infoList.get(0).seriesGeoAccession = formatString(line);
			} else if (equalsSplit(line, "!Series_status")) {
				infoList.get(0).seriesStatus = formatString(line);
			} else if (equalsSplit(line, "!Series_submission_date")) {
				infoList.get(0).seriesSubmissionDate = formatString(line);
			} else if (equalsSplit(line, "!Series_last_update_date")) {
				infoList.get(0).seriesLastUpdateDate = formatString(line);
			} else if (equalsSplit(line, "!Series_web_link")) {
				infoList.get(0).seriesWebLink = formatString(line);
			} else if (equalsSplit(line, "!Series_summary")) {
				if (infoList.get(0).seriesSummary == null) {
					String[] temp = new String[1];
					temp[0] = formatString(line);
					infoList.get(0).seriesSummary = temp;
				} else {
					String[] temp = new String[infoList.get(0).seriesSummary.length + 1];
					for (int i = 0; i < infoList.get(0).seriesSummary.length; i++) {
						temp[i] = infoList.get(0).seriesSummary[i];
					}
					temp[temp.length - 1] = formatString(line);
					infoList.get(0).seriesSummary = temp;
				}
			} else if (equalsSplit(line, "!Series_overall_design")) {
				infoList.get(0).seriesOverallDesign = formatString(line);
			} else if (equalsSplit(line, "!Series_type")) {
				infoList.get(0).seriesType = formatString(line);
			} else if (equalsSplit(line, "!Series_contributor")) {
				if (infoList.get(0).seriesContributors == null) {
					String[] temp = new String[1];
					temp[0] = formatString(line);
					infoList.get(0).seriesContributors = temp;
				} else {
					String[] temp = new String[infoList.get(0).seriesContributors.length + 1];
					for (int i = 0; i < infoList.get(0).seriesContributors.length; i++) {
						temp[i] = infoList.get(0).seriesContributors[i];
					}
					temp[temp.length - 1] = formatString(line);
					infoList.get(0).seriesContributors = temp;
				}
			} else if (equalsSplit(line, "!Series_sample_id")) {
				infoList.get(0).seriesSampleId = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_name")) {
				infoList.get(0).seriesContactName = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_email")) {
				infoList.get(0).seriesContactEmail = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_phone")) {
				infoList.get(0).seriesContactPhone = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_laboratory")) {
				infoList.get(0).seriesContactLaboratory = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_institute")) {
				infoList.get(0).seriesContactInstitute = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_address")) {
				infoList.get(0).seriesContactAddress = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_city")) {
				infoList.get(0).seriesContactCity = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_state")) {
				infoList.get(0).seriesContactState = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_zip/postal_code")) {
				infoList.get(0).seriesContactPostalCode = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_country")) {
				infoList.get(0).seriesContactCountry = formatString(line);
			} else if (equalsSplit(line, "!Series_platform_id")) {
				infoList.get(0).seriesPlatformId = formatString(line);
			} else if (equalsSplit(line, "!Series_platform_taxid")) {
				infoList.get(0).seriesPlatformTaxid = formatString(line);
			} else if (equalsSplit(line, "!Series_sample_taxid")) {
				infoList.get(0).seriesSampleTaxid = formatString(line);
			} else if (equalsSplit(line, "!Series_relation")) {
				infoList.get(0).seriesRelation = formatString(line);
			} else if (equalsSplit(line, "!Sample_title")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleTitle = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_geo_accession")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleGeoAccession = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_status")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleStatus = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_submission_date")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleSubmissionDate = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_last_update_date")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleLastUpdateDate = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_type")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleType = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_channel_count")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleChannelCount = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_source_name_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleSourceName = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_organism_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleOrganism = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_characteristics_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleCharacteristics = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_molecule_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleMolecule = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_extract_protocol_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleExtractProtocol = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_taxid_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleTaxid = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_description")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleDescription = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_data_processing")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleDataProcessing = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_platform_id")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).samplePlatformId = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_name")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactName = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_email")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactEmail = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_phone")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactPhone = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_laboratory")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactLaboratory = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_institute")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactInstitute = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_address")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactAddress = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_city")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactCity = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_state")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactState = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_contact_zip/postal_code")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleContactPostalCode = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_instrument_model")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleInstrumentModel = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_library_selection")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleLibrarySelection = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_library_source")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleLibrarySource = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_library_strategy")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleLibraryStrategy = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_relation")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleRelation = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_characteristics_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleCharacteristics = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!Sample_data_processing")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					infoList.get(i).sampleDataProcessing = formatString(temp[i + 1]);
				}
			} else if (equalsSplit(line, "!series_matrix_table_begin")) {
				// Read the next line in the file,
				// thats where the good stuff is
				line = br.readLine();
				if (br != null) {
					String[] temp = addNewGEOFile(infoList, line);
					for (int i = 0; i < infoList.size(); i++) {
						infoList.get(i).ID_REF = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_supplementary_file_1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					String temporaryString = formatString(temp[i + 1]);
					if (temporaryString.length() > 0) {
						infoList.get(i).sampleSupplementaryFile = checkIfNotURLToSRAFile(temporaryString);
					}
				}
			} else if (equalsSplit(line, "!Sample_supplementary_file_2")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					String temporaryString = formatString(temp[i + 1]);
					if (temporaryString.length() > 0) {
						infoList.get(i).sampleSupplementaryFile = checkIfNotURLToSRAFile(temporaryString);
					}
				}
			}
			line = br.readLine();
		}

		br.close();

		return infoList;
	}

	private static String[] addNewGEOFile(ArrayList<GEOFileTuple> infoList,
			String line) throws IOException {
		/*
		 * If a there is more files than GEOFileTuple, add a new GEOFileTuple
		 */
		String[] temp = addString(infoList, line);
		while (temp.length - 1 > infoList.size()) {
			infoList.add(new GEOFileTuple());
		}
		return temp;
	}

	private static String[] addString(ArrayList<GEOFileTuple> infoList,
			String line) throws IOException {

		// Split the line of text where there is a tab
		String[] strings = line.split("\t");
		return strings;
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
		return strings[strings.length - 1];
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

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
		GEOFileTuple commonGEOInfo = new GEOFileTuple();
		infoList.add(new GEOFileTuple());
		File f = new File(filePath);
		BufferedReader br = new BufferedReader(new FileReader(f));

		String line = br.readLine();

		while (line != null) {
			if (equalsSplit(line, "!Series_title")) {
				commonGEOInfo.seriesTitle = formatString(line);
			} else if (equalsSplit(line, "!Series_geo_accession")) {
				commonGEOInfo.seriesGeoAccession = formatString(line);
			} else if (equalsSplit(line, "!Series_status")) {
				commonGEOInfo.seriesStatus = formatString(line);
			} else if (equalsSplit(line, "!Series_submission_date")) {
				commonGEOInfo.seriesSubmissionDate = formatString(line);
			} else if (equalsSplit(line, "!Series_last_update_date")) {
				commonGEOInfo.seriesLastUpdateDate = formatString(line);
			} else if (equalsSplit(line, "!Series_web_link")) {
				commonGEOInfo.seriesWebLink = formatString(line);
			} else if (equalsSplit(line, "!Series_summary")) {
				if (commonGEOInfo.seriesSummary == null) {
					String[] temp = new String[1];
					temp[0] = formatString(line);
					commonGEOInfo.seriesSummary = temp;
				} else {
					String[] temp = new String[commonGEOInfo.seriesSummary.length + 1];
					for (int i = 0; i < commonGEOInfo.seriesSummary.length; i++) {
						temp[i] = commonGEOInfo.seriesSummary[i];
					}
					temp[temp.length - 1] = formatString(line);
					commonGEOInfo.seriesSummary = temp;
				}
			} else if (equalsSplit(line, "!Series_overall_design")) {
				commonGEOInfo.seriesOverallDesign = formatString(line);
			} else if (equalsSplit(line, "!Series_type")) {
				commonGEOInfo.seriesType = formatString(line);
			} else if (equalsSplit(line, "!Series_contributor")) {
				if (commonGEOInfo.seriesContributors == null) {
					String[] temp = new String[1];
					temp[0] = formatString(line);
					commonGEOInfo.seriesContributors = temp;
				} else {
					String[] temp = new String[commonGEOInfo.seriesContributors.length + 1];
					for (int i = 0; i < commonGEOInfo.seriesContributors.length; i++) {
						temp[i] = commonGEOInfo.seriesContributors[i];
					}
					temp[temp.length - 1] = formatString(line);
					commonGEOInfo.seriesContributors = temp;
				}
			} else if (equalsSplit(line, "!Series_sample_id")) {
				commonGEOInfo.seriesSampleId = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_name")) {
				commonGEOInfo.seriesContactName = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_email")) {
				commonGEOInfo.seriesContactEmail = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_phone")) {
				commonGEOInfo.seriesContactPhone = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_laboratory")) {
				commonGEOInfo.seriesContactLaboratory = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_institute")) {
				commonGEOInfo.seriesContactInstitute = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_address")) {
				commonGEOInfo.seriesContactAddress = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_city")) {
				commonGEOInfo.seriesContactCity = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_state")) {
				commonGEOInfo.seriesContactState = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_zip/postal_code")) {
				commonGEOInfo.seriesContactPostalCode = formatString(line);
			} else if (equalsSplit(line, "!Series_contact_country")) {
				commonGEOInfo.seriesContactCountry = formatString(line);
			} else if (equalsSplit(line, "!Series_platform_id")) {
				commonGEOInfo.seriesPlatformId = formatString(line);
			} else if (equalsSplit(line, "!Series_platform_taxid")) {
				commonGEOInfo.seriesPlatformTaxid = formatString(line);
			} else if (equalsSplit(line, "!Series_sample_taxid")) {
				commonGEOInfo.seriesSampleTaxid = formatString(line);
			} else if (equalsSplit(line, "!Series_relation")) {
				commonGEOInfo.seriesRelation = formatString(line);
			} else if (equalsSplit(line, "!Sample_title")) {
				String[] temp = addNewGEOFile(infoList, line);
				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleTitle != null) {
						infoList.get(i).sampleTitle = infoList.get(i).sampleTitle
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleTitle = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_geo_accession")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleGeoAccession != null) {
						infoList.get(i).sampleGeoAccession = infoList.get(i).sampleGeoAccession
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleGeoAccession = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_status")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleStatus != null) {
						infoList.get(i).sampleStatus = infoList.get(i).sampleStatus
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleStatus = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_submission_date")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleSubmissionDate != null) {
						infoList.get(i).sampleSubmissionDate = infoList.get(i).sampleSubmissionDate
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleSubmissionDate = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_last_update_date")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleLastUpdateDate != null) {
						infoList.get(i).sampleLastUpdateDate = infoList.get(i).sampleLastUpdateDate
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleLastUpdateDate = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_type")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleType != null) {
						infoList.get(i).sampleType = infoList.get(i).sampleType
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleType = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_channel_count")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleChannelCount != null) {
						infoList.get(i).sampleChannelCount = infoList.get(i).sampleChannelCount
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleChannelCount = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_source_name_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleSourceName != null) {
						infoList.get(i).sampleSourceName = infoList.get(i).sampleSourceName
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleSourceName = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_organism_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleOrganism != null) {
						infoList.get(i).sampleOrganism = infoList.get(i).sampleOrganism
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleOrganism = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_characteristics_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleCharacteristics != null) {
						infoList.get(i).sampleCharacteristics = infoList.get(i).sampleCharacteristics
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleCharacteristics = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_molecule_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleMolecule != null) {
						infoList.get(i).sampleMolecule = infoList.get(i).sampleMolecule
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleMolecule = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_extract_protocol_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleExtractProtocol != null) {
						infoList.get(i).sampleExtractProtocol = infoList.get(i).sampleExtractProtocol
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleExtractProtocol = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_taxid_ch1")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleTaxid != null) {
						infoList.get(i).sampleTaxid = infoList.get(i).sampleTaxid
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleTaxid = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_description")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleDescription != null) {
						infoList.get(i).sampleDescription = infoList.get(i).sampleDescription
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleDescription = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_data_processing")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleDataProcessing != null) {
						infoList.get(i).sampleDataProcessing = infoList.get(i).sampleDataProcessing
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleDataProcessing = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_platform_id")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).samplePlatformId != null) {
						infoList.get(i).samplePlatformId = infoList.get(i).samplePlatformId
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).samplePlatformId = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_name")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactName != null) {
						infoList.get(i).sampleContactName = infoList.get(i).sampleContactName
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactName = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_email")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactEmail != null) {
						infoList.get(i).sampleContactEmail = infoList.get(i).sampleContactEmail
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactEmail = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_phone")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactPhone != null) {
						infoList.get(i).sampleContactPhone = infoList.get(i).sampleContactPhone
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactPhone = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_laboratory")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactLaboratory != null) {
						infoList.get(i).sampleContactLaboratory = infoList.get(i).sampleContactLaboratory
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactLaboratory = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_institute")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactInstitute != null) {
						infoList.get(i).sampleContactInstitute = infoList.get(i).sampleContactInstitute
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleMolecule = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_address")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactAddress != null) {
						infoList.get(i).sampleContactAddress = infoList.get(i).sampleContactAddress
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactAddress = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_city")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactCity != null) {
						infoList.get(i).sampleContactCity = infoList.get(i).sampleContactCity
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactCity = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_state")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactState != null) {
						infoList.get(i).sampleContactState = infoList.get(i).sampleContactState
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactState = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_contact_zip/postal_code")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleContactPostalCode != null) {
						infoList.get(i).sampleContactPostalCode = infoList.get(i).sampleContactPostalCode
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleContactPostalCode = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_instrument_model")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleInstrumentModel != null) {
						infoList.get(i).sampleInstrumentModel = infoList.get(i).sampleInstrumentModel
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleInstrumentModel = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_library_selection")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleLibrarySelection != null) {
						infoList.get(i).sampleLibrarySelection = infoList.get(i).sampleLibrarySelection
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleLibrarySelection = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_library_source")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleLibrarySource != null) {
						infoList.get(i).sampleLibrarySource = infoList.get(i).sampleLibrarySource
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleLibrarySource = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_library_strategy")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleLibraryStrategy != null) {
						infoList.get(i).sampleLibraryStrategy = infoList.get(i).sampleLibraryStrategy
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleLibraryStrategy = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_relation")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleRelation != null) {
						infoList.get(i).sampleRelation = infoList.get(i).sampleRelation
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleMolecule = formatString(temp[i + 1]);
					}
				}
			} else if (equalsSplit(line, "!Sample_data_processing")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					if (infoList.get(i).sampleDataProcessing != null) {
						infoList.get(i).sampleDataProcessing = infoList.get(i).sampleDataProcessing
								+ "\n" + formatString(temp[i + 1]);
					} else {
						infoList.get(i).sampleDataProcessing = formatString(temp[i + 1]);
					}
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
						infoList.get(i).sampleSupplementaryFile = temporaryString;
					}
				}
			} else if (equalsSplit(line, "!Sample_supplementary_file_2")) {
				String[] temp = addNewGEOFile(infoList, line);

				for (int i = 0; i < infoList.size(); i++) {
					String temporaryString = formatString(temp[i + 1]);
					if (temporaryString.length() > 0) {
						infoList.get(i).sampleSupplementaryFile = temporaryString;
					}
				}
			}
			line = br.readLine();
		}

		br.close();

		infoList.add(0, commonGEOInfo);

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

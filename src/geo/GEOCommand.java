package geo;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import command.Command;

public class GEOCommand extends Command {


	public GEOCommand() {
	}

	@Override
	public boolean validate() {
		if(this.getHeader() == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public response.Response execute() {
		String tempZipFileLoc = "/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile1.txt.gz";
		String tempFileLoc = "/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile1.txt";
		ArrayList<ArrayList<String>> infoList;

		try {
		String matrixFileURL = GEOAccessor.getMatrixFileURL(this.header);
		GEOAccessor.downloadMatrixFile(matrixFileURL, tempZipFileLoc);

		GEOAccessor.gunzipFile(tempZipFileLoc, tempFileLoc);

		infoList = TXTParser.readFile(tempFileLoc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}


	public void getDownloads(String id) {
		InputStream is;
		try {
			// Paste the id together with a searchstring
			String url = "http://www.ncbi.nlm.nih.gov/geo/query/acc.cgi?acc="
					+ id + "&form=html";
			is = new URL(url).openStream();
			// Parse the xml and get all the urls to the files to download
//			xmlH = XMLParser.parse(is);
			findMatrixURL(is, url);
			downloadMatrixFile();
			gunzipFile();

			ArrayList<ArrayList<String>> infoList = null;

			try {
				infoList = TXTParser.readFile("/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile2.txt");
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

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		GEOCommand geo = new GEOCommand();
		geo.getDownloads("GSE57423");
	}
}

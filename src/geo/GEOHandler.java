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

public class GEOHandler {

	private String matrixFileURL;

	public GEOHandler() {
		matrixFileURL = null;
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

	private void findMatrixURL(InputStream is, String url) {
		Document doc = null;
		try {
			doc = Jsoup.parse(is, "UTF-8", url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Elements links = doc.getElementsByTag("a");
		for (int i = 0; i < links.size(); i++) {

			Element link = links.get(i);
			String linkHref = link.attr("href");
			String linkText = link.text();

			if(linkText.equals("Series Matrix File(s)")) {
				String[] temp = linkHref.split("/");
				matrixFileURL = linkHref + temp[temp.length - 2].trim() + "_series_matrix.txt.gz";
				System.out.println(matrixFileURL);
			}
		}
	}

	private void downloadMatrixFile() {
		InputStream is;
		try {
			is = new URL(matrixFileURL).openStream();
			OutputStream outputStream = new FileOutputStream(new File("/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile1.txt"));
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

			outputStream.close();
			is.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void gunzipFile(){

		byte[] buffer = new byte[1024];

		try{

			GZIPInputStream gzis =
					new GZIPInputStream(new FileInputStream("/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile1.txt"));

			FileOutputStream out = new FileOutputStream("/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile2.txt");

			int len;
			while ((len = gzis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}

			gzis.close();
			out.close();

			System.out.println("Done");
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}

	public String getSRAFromDir(String url) {
		System.out.println(url);
		url = url + "/";
		InputStream is = null;
		try {
			is = new URL(url).openStream();
		    byte[] buffer = new byte[1024];
		    int bytesRead = -1;
		    StringBuilder page = new StringBuilder(1024);
		    while ((bytesRead = is.read(buffer)) != -1) {
		        page.append(new String(buffer, 0, bytesRead));
		    }
		    String fileInfo = page.toString();
		    System.out.println(fileInfo);

		//    String[] temp = fileInfo.split(" ");

	//	    url = url + temp[temp.length-1].trim() + "/" + temp[temp.length-1].trim() + ".sra";

	//	    System.out.println(url);

		    return url;
		} catch (IOException ex) {
		    ex.printStackTrace();
		} finally {
		    try {
		        is.close();
		    } catch (Exception e) {
		    }
		}
		return url;
	}

	public static void main(String[] args) {
		GEOHandler geo = new GEOHandler();
		geo.getDownloads("GSE57423");
	}
}

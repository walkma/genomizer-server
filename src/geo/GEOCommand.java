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

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import command.Command;

public class GEOCommand extends Command {


	public GEOCommand() {
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public response.Response execute() {

		//"/home/pvt/tempfiles/Matrixfile" + System.nanoTime() + ".txt.gz"
		String tempZipFileLoc = "/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile1.txt.gz";
		//"/home/pvt/tempfiles/Matrixfile" + System.nanoTime() + ".txt"
		String tempFileLoc = "/home/oi11/oi11msd/edu/programvaruteknik/Projekt/Testfiler/MatrxiFile1.txt";
		ArrayList<GEOFileTuple> infoList = null;

		try {
		String matrixFileURL = GEOAccessor.getMatrixFileURL("GSE47236");
		GEOAccessor.downloadMatrixFile(matrixFileURL, tempZipFileLoc);

		GEOAccessor.gunzipFile(tempZipFileLoc, tempFileLoc);

		infoList = TXTParser.readFile(tempFileLoc);
		File file = new File(tempFileLoc);
		file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new GetGEOResponse(infoList);
	}

	public static void main(String[] args) {
		GEOCommand geo = new GEOCommand();
		String temp = geo.execute().getBody();

		String[] hej = temp.split("[,}{]");

		for(String l : hej) {
			System.out.println(l);
		}
	}
}

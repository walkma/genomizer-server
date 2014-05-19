package geo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
//		File file = new File(tempFileLoc);
//		file.delete();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new GetGEOResponse(infoList);
	}

    public static String toPrettyFormat(String jsonString)
    {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(jsonString).getAsJsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String prettyJson = gson.toJson(json);

        return prettyJson;
    }

	public static void main(String[] args) {
		GEOCommand geo = new GEOCommand();
		String temp = geo.execute().getBody();

		System.out.println(toPrettyFormat(temp));
	}
}

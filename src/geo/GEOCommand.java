package geo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

		long time = System.nanoTime();

		//"/home/pvt/tempfiles/Matrixfile" + System.nanoTime() + ".txt.gz"
		String tempZipFileLoc = "/home/dv12/dv12tkn/Downloads/MatrxiFile" + time + ".txt.gz";
		//"/home/pvt/tempfiles/Matrixfile" + System.nanoTime() + ".txt"
		String tempFileLoc = "/home/dv12/dv12tkn/Downloads/MatrxiFile" + time + ".txt";
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

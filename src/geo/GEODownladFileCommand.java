package geo;

import java.io.IOException;

import com.google.gson.annotations.Expose;

import response.Response;
import command.Command;

public class GEODownladFileCommand extends Command{

	@Expose
	private String experimentID;

	@Expose
	private String fileName;

	@Expose
 	private String type;

	@Expose
	private String metaData;

	@Expose
	private String author;

	@Expose
	private String uploader;

	@Expose
	private boolean isPrivate;

	@Expose
	private String grVersion;

	@Expose
	private String url;

	/**
	 * Validates the request by checking
	 * the attributes. No attribute can be null
	 * and type needs to be either "raw", "profile",
	 * or "region".
	 */
	@Override
	public boolean validate() {

		if(experimentID == null || fileName == null || type == null || url == null) {
			return false;
		}
		return true;
	}


	@Override
	public Response execute() {
		try {
			String sraURL = GEOFileDownloader.getSRAFromDir(url);



		} catch (IOException e) {
			e.printStackTrace();
		}




		return null;
	}

}

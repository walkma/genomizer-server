package command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import response.GetTransferResponse;
import response.Response;

/**
 * Command that generates a fileinputstream from a file path.
 * @author c11vlg, dv12ahn
 *
 */
public class GetTransferCommand extends Command {

	private String filePath;

	public GetTransferCommand(String filePath) {
		this.filePath = filePath;
	}

	public Response execute() {


		File file = new File(filePath);

		FileInputStream fis = null;
		GetTransferResponse response;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			response = new GetTransferResponse(null, 0, 404);
		}

		response = new GetTransferResponse(fis, file.length(), 200);

		return response;
	}

	@Override
	public boolean validate() {
		if(filePath == null) {
			return false;
		} else {
			return true;
		}
	}

}

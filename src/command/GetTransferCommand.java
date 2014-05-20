package command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import response.GetTransferResponse;
import response.Response;

public class GetTransferCommand extends Command {

	private String filePath;

	public GetTransferCommand(String filePath) {
		this.filePath = filePath;
	}

	public Response execute() {

		GetTransferResponse response = null;

		try {
			File file = new File(filePath);
			FileInputStream fis;
			fis = new FileInputStream(file);

			response = new GetTransferResponse(fis, file.length(), 200);

		} catch (FileNotFoundException e) {
			response = new GetTransferResponse(null, 0, 404);
		}
		return response;
	}

	@Override
	public boolean validate() {
		if(filePath == null) {
			return false;
		}else {
			return true;
		}

	}

}

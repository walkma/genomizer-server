package command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

import response.GetTransferResponse;
import response.Response;
import server.DatabaseSettings;
import database.DatabaseAccessor;

public class GetTransferCommand extends Command {

	private BufferedInputStream bis;
	private String filePath;

	public GetTransferCommand(String filePath) {
		// this.fileID = fileID;
		this.filePath = filePath;
	}

	public Response execute() {

		DatabaseAccessor db = null;

		GetTransferResponse response = null;

		try {
//			db = new DatabaseAccessor(DatabaseSettings.username,
//					DatabaseSettings.password, DatabaseSettings.host,
//					DatabaseSettings.database);


//			System.out.println("fileID = " + fileID);
//			String path = db.getFilePath(fileID);
//			System.out.println("path= " + path);


			System.out.println("download path: " + filePath);

			File file = new File(filePath);
			byte[] bytearray = new byte[(int) file.length()];
			FileInputStream fis;
			fis = new FileInputStream(file);

			response = new GetTransferResponse(fis, file.length());
			System.out.println("Sending file response");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

}

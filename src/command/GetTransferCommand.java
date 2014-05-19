package command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import database.DatabaseAccessor;

import response.GetTransferResponse;
import response.Response;
import server.DatabaseSettings;

public class GetTransferCommand extends Command {

	private BufferedInputStream bis;
	private String fileID;

	public GetTransferCommand(String fileID) {
		// this.fileID = fileID;
		this.fileID = fileID;
	}

	public Response execute() {

		DatabaseAccessor db = null;

		GetTransferResponse response = null;

		try {
			db = new DatabaseAccessor(DatabaseSettings.username,
					DatabaseSettings.password, DatabaseSettings.host,
					DatabaseSettings.database);


			System.out.println("fileID = " + fileID);
			String path = db.getFilePath(fileID);
			System.out.println("path= " + path);

			// TODO: REMOVE
			path = "/home/c11/c11vlg/Downloads/test3.txt";

			File file = new File(path);
			byte[] bytearray = new byte[(int) file.length()];
			FileInputStream fis;
			fis = new FileInputStream(file);

			bis = new BufferedInputStream(fis);
			bis.read(bytearray, 0, bytearray.length);
//
//			Headers h = exchange.getResponseHeaders();
//			h.set("Content-Type", "application/octet-stream");

			//exchange.sendResponseHeaders(200, file.length());

//			OutputStream os = exchange.getResponseBody();
//			os.write(bytearray, 0, bytearray.length);
//			os.close();
			fis.close();

			String str = new String(bytearray, "UTF-8");

			response = new GetTransferResponse(str);
			System.out.println("Sending file response");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
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

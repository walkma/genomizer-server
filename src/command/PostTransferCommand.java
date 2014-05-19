package command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import response.PostTransferResponse;
import response.Response;
import server.DatabaseSettings;
import database.DatabaseAccessor;

public class PostTransferCommand extends Command {

	//private HttpExchange exchange;
	private String body;
	private String fileID;

	public PostTransferCommand(String body, String fileID) {
		this.fileID = fileID;
		this.body = body;
	}

	public Response execute() {

//		BufferedInputStream bis = new BufferedInputStream(
//				exchange.getRequestBody());
//		String path = exchange.getRequestURI().toString();
//
//		String[] split = path.split("=");
//		path = split[split.length - 1];


//		for (Iterator iterator = exchange.getRequestHeaders().values().iterator(); iterator.hasNext();) {
//	        String type = (String) iterator.next();
//	        System.out.println(type);
//	    }

		DatabaseAccessor db = null;

		PostTransferResponse response = null;

		try {
			db = new DatabaseAccessor(DatabaseSettings.username,
					DatabaseSettings.password, DatabaseSettings.host,
					DatabaseSettings.database);


			System.out.println("fileID = " + fileID);
			String path = db.getFilePath(fileID);
			System.out.println("path= " + path);

			// TODO: REMOVE
			path = "/home/c11/c11vlg/Downloads/uploadTest.txt";

			System.out.println("path = " + path);

			File file = new File(path);


			FileOutputStream fos = new FileOutputStream(file);

//			int len = Integer.parseInt(exchange.getRequestHeaders().getFirst(
//					"Content-Length"));
//			byte[] buffer = new byte[len];
//			System.out.println("Lenght = " + len);
//			bis.read(buffer, 0, len);


			String[] bodyarr = body.split(" Content");
			String boundary = bodyarr[0];
			bodyarr = body.split("binary ");
			String filestring = bodyarr[1].split(boundary)[0];

			System.out.println("file: " + filestring);

			fos.write(filestring.getBytes());
//
//			System.out.println("BOUNDARY: " + boundary);
//
//			for(int i = 0; i < bodyarr.length; i++) {
//				if(bodyarr[i].indexOf("Content-") != -1){
//					System.out.println("CONTENT: " + bodyarr[i]);
//				} else if(bodyarr[i].equals("\n")) {
//					System.out.println("NEW LINE: " + bodyarr[i]);
//				} else if(bodyarr[i].indexOf(boundary) != -1) {
//					System.out.println("BOUNDARY: " + bodyarr[i]);
//				} else {
//					System.out.println("FILE: " + bodyarr[i]);
//				}
//			}


//			String qry = new String(buffer, "UTF-8");

			System.out.println("MESSAGE START:\n\n");
			System.out.println(body + "\n");

			//OutputStream bos = exchange.getResponseBody();

			// bos.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
			// bos.write("Content-Length: 0\r\n".getBytes("UTF-8"));
			// bos.write("\r\n".getBytes("UTF-8"));
			//
//			bos.flush();
//			bos.close();
			fos.close();

			response = new PostTransferResponse();
//			bis.close();
//			exchange.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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

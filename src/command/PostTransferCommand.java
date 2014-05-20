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
			path = "/home/c11/c11vlg/Downloads/uploadTest.jpg";

			System.out.println("path = " + path);

			//File file = new File(path);


			//FileOutputStream fos = new FileOutputStream(file);

//			int len = Integer.parseInt(exchange.getRequestHeaders().getFirst(
//					"Content-Length"));
//			byte[] buffer = new byte[len];
//			System.out.println("Lenght = " + len);
//			bis.read(buffer, 0, len);


//			String boundary = body.split(System.getProperty("line.separator"))[0];
//			String filename = body.split("filename=\"")[1].split("\"")[0];
//			String bodyarr[] = body.split(System.getProperty("line.separator"));
//			System.out.println("boundary: " + boundary);
//
//			String filestring = "";
//
//			boolean readFirstNewline = false;
//
//			for(int i = 0; i < bodyarr.length; i++) {
//				if(bodyarr[i].indexOf("Content-") != -1 || bodyarr[i].equals(boundary)) {
//					//System.out.println("content: " + bodyarr[i]);
//				} else {
//					if(!readFirstNewline && bodyarr[i].equals("\r")) {
//						System.out.println("first newline");
//						readFirstNewline = true;
//					} else if((i+1) < bodyarr.length && bodyarr[i + 1].equals(boundary + "--") && bodyarr[i].equals("\r")) {
//						break;
//					} else {
//						filestring = filestring.concat(bodyarr[i]);
//						System.out.println("bodyarr: " + bodyarr[i]);
//					}
//				}
//
//			}
//
//
//			System.out.println("filename: " + filename);
//			System.out.println("file: " + filestring);
			System.out.println("body: " + body);

			//fos.write(body.getBytes());
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

//			System.out.println("MESSAGE START:\n\n");
//			System.out.println(body + "\n");

			//OutputStream bos = exchange.getResponseBody();

			// bos.write("HTTP/1.1 200 OK\r\n".getBytes("UTF-8"));
			// bos.write("Content-Length: 0\r\n".getBytes("UTF-8"));
			// bos.write("\r\n".getBytes("UTF-8"));
			//
//			bos.flush();
//			bos.close();
			//fos.close();

			response = new PostTransferResponse();
//			bis.close();
//			exchange.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return response;

	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return true;
	}

}

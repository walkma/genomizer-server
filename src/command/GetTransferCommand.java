package command;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import response.GetTransferResponse;
import response.Response;

public class GetTransferCommand extends Command {

<<<<<<< HEAD
	private String filePath;
=======
	private BufferedInputStream bis;
	private String fileID;
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git

<<<<<<< HEAD
	public GetTransferCommand(String filePath) {
		this.filePath = filePath;
=======
	public GetTransferCommand(String fileID) {
		// this.fileID = fileID;
		this.fileID = fileID;
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
	}

	public Response execute() {

		GetTransferResponse response = null;

		try {
<<<<<<< HEAD
			File file = new File(filePath);
=======
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
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
			FileInputStream fis;
			fis = new FileInputStream(file);

<<<<<<< HEAD
			response = new GetTransferResponse(fis, file.length(), 200);
=======
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
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git

		} catch (FileNotFoundException e) {
<<<<<<< HEAD
			response = new GetTransferResponse(null, 0, 404);
=======
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
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

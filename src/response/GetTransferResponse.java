package response;

import java.io.FileInputStream;

public class GetTransferResponse extends Response {

	FileInputStream fileInputStream;
	long fileSize;

	public GetTransferResponse(FileInputStream fileInputStream, long fileSize) {
		code = 200;
		this.fileInputStream = fileInputStream;
		this.fileSize = fileSize;
	}

	public FileInputStream getFileBody() {
		return fileInputStream;
	}

	public long getFileSize() {
		return fileSize;
	}

}

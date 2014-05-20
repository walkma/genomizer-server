package response;

import java.io.FileInputStream;

public class GetTransferResponse extends Response {

	FileInputStream fileInputStream;
	long fileSize;

	public GetTransferResponse(FileInputStream fileInputStream, long fileSize, int code) {
		this.code = code;
		this.fileInputStream = fileInputStream;
		this.fileSize = fileSize;
	}

	public long getFileSize() {
		return fileSize;
	}

	public FileInputStream getFileBody() {
		return fileInputStream;
	}

}

package response;

import java.io.FileInputStream;

/**
 * A response containing filesize and and a fileinputstream for a file.
 * @author c11vlg
 *
 */
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

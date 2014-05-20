package response;

public class GetTransferResponse extends Response {

	String fileAsString;

<<<<<<< HEAD
	public GetTransferResponse(FileInputStream fileInputStream, long fileSize, int code) {
		this.code = code;
		this.fileInputStream = fileInputStream;
		this.fileSize = fileSize;
=======
	public GetTransferResponse(String fileAsString) {
		code = 200;
		this.fileAsString = fileAsString;
>>>>>>> branch 'master' of ssh://git@github.com:22/walkma/genomizer-server.git
	}

	@Override
	public String getBody() {
		return fileAsString;
	}

}

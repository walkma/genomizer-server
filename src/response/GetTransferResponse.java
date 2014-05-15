package response;

public class GetTransferResponse extends Response {

	String fileAsString;

	public GetTransferResponse(String fileAsString) {
		code = 200;
		this.fileAsString = fileAsString;
	}

	@Override
	public String getBody() {
		return fileAsString;
	}

}

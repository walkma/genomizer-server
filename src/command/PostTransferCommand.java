package command;

import response.PostTransferResponse;
import response.Response;

public class PostTransferCommand extends Command {

	private String body;

	public PostTransferCommand(String body) {
		this.body = body;
	}

	public Response execute() {

		Response response = new PostTransferResponse(Integer.parseInt(body));

		return response;

	}

	@Override
	public boolean validate() {
		if(body == null) {
			return false;
		} else {
			return true;
		}
	}

}

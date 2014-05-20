package command;

import response.PostTransferResponse;
import response.Response;

public class PostTransferCommand extends Command {

	private String body;

	public PostTransferCommand(String body) {
		this.body = body;
	}

	public Response execute() {

		PostTransferResponse response = new PostTransferResponse(
				Integer.parseInt(body));

		return response;

	}

	@Override
	public boolean validate() {
		if (body != null) {
			try {
				new Integer(body);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}

}

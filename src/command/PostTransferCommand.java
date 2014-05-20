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
		// TODO Auto-generated method stub
		return true;
	}

}

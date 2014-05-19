package command;

import java.sql.SQLException;

import response.MinimalResponse;
import response.Response;
import response.StatusCode;
import server.DatabaseSettings;

import com.google.gson.annotations.Expose;

import database.DatabaseAccessor;

/**
 * Class used to represent a logout command.
 *
 * @author tfy09jnn
 * @version 1.0
 */
public class AddAnnotationValueCommand extends Command {

	@Expose
	private String name;

	@Expose
	private String newValue;

	/**
	 * Used to validate the logout command.
	 */
	@Override
	public boolean validate() {

		// TODO Auto-generated method stub
		return true;

	}

	/**
	 * Used to execute the logout command.
	 */
	@Override
	public Response execute() {

		DatabaseAccessor accessor = null;

		try {
			accessor = new DatabaseAccessor(DatabaseSettings.username, DatabaseSettings.password, DatabaseSettings.host, DatabaseSettings.database);
			System.out.println("Got annotations.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//accessor.addDropDownAnnotation(label, choices, defaultValueIndex, required)
		//Method not implemented, send appropriate response
		return 	new MinimalResponse(StatusCode.NO_CONTENT);

	}

}

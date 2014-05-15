package command;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Used to create and return different commands that
 * will be executed later by calling their execute() method.
 *
 * @author tfy09jnn
 * @version 1.0
 */
public class CommandFactory {

	private final GsonBuilder builder;
	private final Gson gson;

	/**
	 * Constructor that creates the gson builder.
	 */
	public CommandFactory() {

		//Create the builder.
	    builder = new GsonBuilder();
	    builder.excludeFieldsWithoutExposeAnnotation();
	    gson = builder.create();

	}

	/**
	 * Used to create the command needed for login.
	 * @param json string to initiate class.
	 * @return the actual command.
	 */
	public Command createLoginCommand(String json) {


		//Create command with json.
		final Command loginCmd = gson.fromJson(json, LoginCommand.class);

		//Set headers
		//loginCmd.setHeader(restful);
		return loginCmd;
	}

	/**
	 * Used to create logout command.
	 * @param RESTful-header.
	 * @return a logout command.
	 */
	public Command createLogoutCommand(String username) {

		//Create command with json.
		final Command logoutCmd = new LogoutCommand(username);

		return logoutCmd;

	}

	/**
	 * Used to create the command needed for retrieving experiments.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createRetrieveExperimentCommand(String json, String restful) {
		return new GetExperimentCommand(restful);
	}

	/**
	 * Used to create the command needed for adding experiments.
	 * @param json string to initiate class.
	 * @return the actual command.
	 */
	public Command createAddExperimentCommand(String json) {

		final Command addExperimentCmd = gson.fromJson(json, AddExperimentCommand.class);

		return addExperimentCmd;

	}

	/**
	 * Used to create the command needed for updateing experiments.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createUpdateExperimentCommand(String json, String restful) {

		return new UpdateExperimentCommand();

	}

	/**
	 * Used to create the command needed for removeing experiments.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createRemoveExperimentCommand(String json, String restful) {

		return new DeleteExperimentCommand(restful);

	}

	/**
	 * Used to create the command needed for retrieving experiment files.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createGetFileFromExperimentCommand(String json, String restful) {

		return new GetFileFromExperimentCommand(restful);

	}

	/**
	 * Used to create the command needed for adding files to experiments.
	 * @param json string to initiate class.
	 * @return the actual command.
	 */
	public Command createAddFileToExperimentCommand(String json) {

		final Command addFileToExperimentCmd = gson.fromJson(json, AddFileToExperimentCommand.class);

		return addFileToExperimentCmd;

	}

	/**
	 * Used to create the command needed to update files in experiments.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createUpdateFileInExperimentCommand(String json, String restful) {

		return new UpdateFileInExperimentCommand();

	}

	/**
	 * Used to create the command needed to remove files from experiments.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createDeleteFileFromExperimentCommand(String json, String restful) {

		return new DeleteFileFromExperimentCommand(restful);

	}

	/**
	 * Used to create the command needed for searching experiments.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 * @throws SQLException
	 */
	public Command createSearchForExperimentCommand(String restful) {
		int index = restful.indexOf("=");
		return new SearchForExperimentsCommand(restful.substring(index+1));

	}

	/**
	 * Used to create the command needed for updating users.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createUpdateUserCommand(String json, String restful) {

		return new UpdateUserCommand();

	}

	/**
	 * Used to create the command needed for deleting users.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createDeleteUserCommand(String json, String restful) {

		return new DeleteUserCommand();

	}

	//TODO: Refactor raw to profile command to process command.
	/**
	 * Used to create the command needed for converting raw to profile.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @param username
	 * @return the actual command.
	 */
	public Command createProcessCommand(String json, String username) {

		ProcessCommand processCommand = gson.fromJson(json, ProcessCommand.class);
//		processCommand.setProcessType(restful[2]);
//		processCommand.setFileID(restful[3]);
		processCommand.setUsername(username);
		//Create from json
		//set userID
		//set fileID
		//set processType
		return processCommand;

	}

	/**
	 * Used to create the command needed to get annotation information.
	 * @param json string to initiate class.
	 * @return the actual command.
	 */
	public Command createGetAnnotationInformationCommand(String json) {

		return new GetAnnotationInformationCommand();

	}

	/**
	 * Used to create a command needed to add annotation fields.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createAddAnnotationFieldCommand(String json, String restful) {

		final Command AddAnnotationFieldCmd = gson.fromJson(json, AddAnnotationFieldCommand.class);

		return AddAnnotationFieldCmd;

	}


	/**
	 * Used to create the command needed to add annotation values.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createAddAnnotationValueCommand(String json, String restful) {

		return new AddAnnotationValueCommand();

	}

	/**
	 * Used to create the command needed to remove annotation fields.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createRemoveAnnotationFieldCommand(String json, String restful) {

		return new DeleteAnnotationFieldCommand();

	}

	/**
	 * Used to create the command needed to get annotation privileges.
	 * @param json string to initiate class.
	 * @return the actual command.
	 */
	public Command createGetAnnotationPrivilegesCommand(String json) {

		return new GetAnnotationPrivilegesCommand();

	}

	/**
	 * Used to create the command needed to update annotation privileges.
	 * @param json string to initiate class.
	 * @param restful tag to put into class.
	 * @return the actual command.
	 */
	public Command createUpdateAnnotationPrivilegesCommand(String json, String restful) {

		return new UpdateAnnotationPrivilegesCommand();

	}

	public Command createGetTransferCommand(String restful) {
		int index = restful.indexOf("=");
		return new GetTransferCommand(restful.substring(index+1));

	}

	public Command createPostTransferCommand(String restful) {
		int index = restful.indexOf("=");
		return new PostTransferCommand(restful.substring(index+1));
	}

}

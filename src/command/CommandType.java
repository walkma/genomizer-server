package command;

public enum CommandType {
	LOGIN_COMMAND,
	LOGOUT_COMMAND,
	RETRIEVE_EXPERIMENT_COMMAND,
	ADD_EXPERIMENT_COMMAND,
	UPDATE_EXPERIMENT_COMMAND,
	REMOVE_EXPERIMENT_COMMAND,
	GET_FILE_FROM_EXPERIMENT_COMMAND,
	ADD_FILE_TO_EXPERIMENT_COMMAND,
	UPDATE_FILE_IN_EXPERIMENT_COMMAND,
	DELETE_FILE_FROM_EXPERIMENT_COMMAND,
	SEARCH_FOR_EXPERIMENTS_COMMAND,
	CREATE_USER_COMMAND,
	UPDATE_USER_COMMAND,
	DELETE_USER_COMMAND,
	PROCESS_COMMAND,
	GET_ANNOTATION_INFORMATION_COMMAND,
	ADD_ANNOTATION_FIELD_COMMAND,
	ADD_ANNOTATION_VALUE_COMMAND,
	REMOVE_ANNOTATION_FIELD_COMMAND,
	GET_ANNOTATION_PRIVILEGES_COMMAND,
	UPDATE_ANNOTATION_PRIVILEGES_COMMAND;
}
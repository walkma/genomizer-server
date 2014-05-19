package command;

import java.sql.SQLException;
import java.util.ArrayList;

import response.GetExperimentResponse;
import response.MinimalResponse;
import response.Response;
import response.StatusCode;
import server.DatabaseSettings;
import database.DatabaseAccessor;
import database.Experiment;

/**
 * Class used to retrieve an experiment.
 *
 * @author tfy09jnn, Hugo Källström
 * @version 1.1
 */
public class GetExperimentCommand extends Command {



	/**
	 * Empty constructor.
	 */
	public GetExperimentCommand(String rest) {
		this.header = rest;
	}

	@Override
	public boolean validate() {

		if(this.header == null) {
			return false;
		} else {
			return true;
		}

	}

	@Override
	public Response execute() {

	    Experiment exp;
	    DatabaseAccessor db = null;

		try {
			db = new DatabaseAccessor(DatabaseSettings.username, DatabaseSettings.password, DatabaseSettings.host, DatabaseSettings.database);
			exp = db.getExperiment(this.header);
		} catch (SQLException e) {
			return new MinimalResponse(StatusCode.SERVICE_UNAVAILABLE);
		}

		return new GetExperimentResponse(getInfo(exp), exp.getAnnotations(), exp.getFiles(), StatusCode.OK);
	}

	public ArrayList<String> getInfo(Experiment exp) {
		ArrayList<String> info = new ArrayList<String>();
		info.add(exp.getID());
		return info;

	}





}
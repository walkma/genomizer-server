package response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import database.FileTuple;

public class GetExperimentResponse extends Response {

	public GetExperimentResponse(ArrayList<String> info, Map<String, String> annotations, List<FileTuple> list, int code) {
		this.code = code;

		JsonObject obj = new JsonObject();
		obj.addProperty("name", "testExp");
		obj.addProperty("createdBy", "hugga");
		JsonArray fileArray = new JsonArray();
		for (FileTuple ft: list) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			Gson gson = gsonBuilder.setPrettyPrinting().create();
			FileInformation fileInfo = new FileInformation(ft);
			JsonElement fileJson = gson.toJsonTree(fileInfo);
			fileArray.add(fileJson);
		}

		obj.add("files", fileArray);

		JsonArray annotationArray = new JsonArray();
		for (String key: annotations.keySet()) {
			JsonObject anno = new JsonObject();
			anno.addProperty("name", key);
			anno.addProperty("value", annotations.get(key));
			annotationArray.add(anno);
		}

		obj.add("annotations", annotationArray);
	}
}

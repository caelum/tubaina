package br.com.caelum.tubaina.gists;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class GistFilesDeserializer implements JsonDeserializer<GistedFiles> {

	public GistedFiles deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		List<GistedFile> files = new ArrayList<GistedFile>();

		Set<Entry<String, JsonElement>> filesAtJson = json.getAsJsonObject()
				.entrySet();

		for (Entry<String, JsonElement> f : filesAtJson) {
			JsonElement element = f.getValue();
			String content = element.getAsJsonObject().get("content")
					.getAsString();
			String language = element.getAsJsonObject().get("language")
					.isJsonNull() ? "" : element.getAsJsonObject()
					.get("language").getAsString();
			String filename = f.getKey();
			GistedFile file = new GistedFile(filename, language, content);
			files.add(file);
		}

		return new GistedFiles(files);

	}
}
package br.com.caelum.tubaina.gists;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonToGistResultConverter {
	public GistResult convert(String json) {
		Gson gson = new GsonBuilder().registerTypeAdapter(
				GistedFiles.class, new GistFilesDeserializer()).create();

		return gson.fromJson(json, GistResult.class);
	}
}

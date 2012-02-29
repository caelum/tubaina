package br.com.caelum.tubaina.gists;

import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class GistRequest {

	private static final String GIST_URL = "https://api.github.com/gists/";

	public String get(long gistId) throws IOException, ClientProtocolException {
		HttpGet get = new HttpGet(GIST_URL + gistId);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response;
		response = client.execute(get);
		HttpEntity entity = response.getEntity();

		String jsonResult = new Scanner(entity.getContent()).useDelimiter("\\Z").next();
		return jsonResult;
	}
}

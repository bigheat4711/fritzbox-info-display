package cmuche.fritzbox_info_display.tools;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class NetworkTool {
	public static String getFileContents(String url) throws Exception {
		HttpGet request = new HttpGet(url);
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpResponse response = httpClient.execute(request);
		HttpEntity entity = response.getEntity();
		String contents = EntityUtils.toString(entity, "UTF-8");
		return contents;
	}
}

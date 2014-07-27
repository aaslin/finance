package se.aaslin.developer.finance.server.backend.scraper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpPuller {

	public static class HttpResponse {
		
		private String contentAsString;
		private final byte[] rawContent;
		private final String setCookie;
		private final StatusLine statusLine;
		private final Header[] headers;

		private HttpResponse(byte[] rawContent, String setCookie, StatusLine statusLine, Header[] headers) {
			this.rawContent = rawContent;
			this.setCookie = setCookie;
			this.statusLine = statusLine;
			this.headers = headers;
		}

		public byte[] getRawContent() {
			return rawContent;
		}

		public String getSetCookie() {
			return setCookie;
		}

		public StatusLine getStatusLine() {
			return statusLine;
		}
		
		public Header[] getHeaders() {
			return headers;
		}
		
		public String getContentAsString() throws IOException {
			if (contentAsString == null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(rawContent)));
				StringBuilder content = new StringBuilder();
				String line = "";
				while ((line = br.readLine()) != null) {
					content.append(line).append("\n");
				}
				contentAsString = content.toString();
			}
			
			return contentAsString;
		}

		public static HttpResponse build(org.apache.http.HttpResponse httpResponse) throws IOException {
			String cookie = null;
			Header[] allHeaders = httpResponse.getAllHeaders();
			for (Header header : allHeaders) {
				if (header.getName().equals("Set-Cookie")) {
					cookie = header.getValue();
					cookie = cookie.substring(0, cookie.indexOf(";"));
				}
			}
			
			return new HttpResponse(EntityUtils.toByteArray(httpResponse.getEntity()), cookie, httpResponse.getStatusLine(), allHeaders);
		}
	}
	
	public static abstract class HttpRequest<M extends HttpRequestBase> {
		
		protected final Map<String, String> headers = new HashMap<String, String>();
		protected final String url;
		protected final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		public HttpRequest(String url) {
			this.url = url;
		}
		
		protected abstract M getRequest() throws UnsupportedEncodingException;

		public Map<String, String> getHeaders() {
			return headers;
		}

		public List<NameValuePair> getNameValuePairs() {
			return nameValuePairs;
		}

	}
	
	public static class HttpPostRequest extends HttpRequest<HttpPost> {

		public HttpPostRequest(String url) {
			super(url);
		}

		@Override
		protected HttpPost getRequest() throws UnsupportedEncodingException {
			HttpPost request = new HttpPost(url);
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			for (Entry<String, String> header : headers.entrySet()) {
				request.addHeader(header.getKey(), header.getValue());
			}
			
			return request;
		}
	}
	
	public static class HttpGetRequest extends HttpRequest<HttpGet> {

		public HttpGetRequest(String url) {
			super(url);
		}

		@Override
		protected HttpGet getRequest() throws UnsupportedEncodingException {
			HttpGet request = new HttpGet(url);
			for (Entry<String, String> header : headers.entrySet()) {
				request.addHeader(header.getKey(), header.getValue());
			}
			
			return request;
		}
	}
	
	public static HttpResponse pull(HttpRequest<? extends HttpRequestBase> request) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpRequestBase httpRequest = request.getRequest();
		try {
			return HttpResponse.build(client.execute(httpRequest));
		} finally {
			httpRequest.releaseConnection();
		}
	}
}

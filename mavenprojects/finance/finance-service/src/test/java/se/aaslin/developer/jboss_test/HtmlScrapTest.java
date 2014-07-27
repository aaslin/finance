package se.aaslin.developer.jboss_test;

import java.io.ByteArrayInputStream;

import org.apache.http.Header;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Ignore;
import org.junit.Test;

import se.aaslin.developer.finance.server.backend.scraper.CaptchaBreaker;
import se.aaslin.developer.finance.server.backend.scraper.HtmlScraper;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpGetRequest;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpPostRequest;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpResponse;

public class HtmlScrapTest {

	private static final String ACCOUNT_1_PAGE = "https://mobil.nordea.se/banking-nordea/nordea-c3/account.html?id=konton:1";
	private static final String LOGIN_PAGE = "https://mobil.nordea.se/banking-nordea/nordea-c3/login.html";
	private static final String CAPTCHA_IMAGE = "https://mobil.nordea.se/banking-nordea/nordea-c3/captcha.png";

	@Test
	public void testScrap2() throws Exception {
		String cookie = "JSESSIONID=9216B59E27845269E107106F0DE661DF.worker1";

		HttpResponse response = getLoginPage();
		
		System.out.println(response.getStatusLine());
		for (Header header: response.getHeaders()) {
			System.out.println(header.getName() + ":" + header.getValue());
		}
//		System.out.println();
		
		String csrf = HtmlScraper.extractCSRFToken(response.getContentAsString());	
		cookie = response.getSetCookie();
		response = postLogin(csrf, cookie, null);

//		System.out.println(response.getStatusLine());
//		for (Header header: response.getHeaders()) {
//			System.out.println(header.getName() + ":" + header.getValue());
//		}
//		System.out.println();
		
		if (HtmlScraper.cointainsCaptcha(response.getContentAsString())) {
			csrf = HtmlScraper.extractCSRFToken(response.getContentAsString());
			response = getCaptcha(cookie);
			String captcha = new CaptchaBreaker().breakCaptcha(new ByteArrayInputStream(response.getRawContent()));
			response = postLogin(csrf, cookie, captcha);
//			System.out.println(response.getContentAsString());
		}

		cookie = response.getSetCookie();
		response = getTransactionPage(cookie);

//		System.out.println(response.getStatusLine());
//		for (Header header: response.getHeaders()) {
//			System.out.println(header.getName() + ":" + header.getValue());
//		}
//		System.out.println();

//		List<Transaction> transactions = HtmlScraper.extractTransactions(response.getContentAsString());
		
//		for (Transaction transaction : transactions) {
//			System.out.println(transaction.getComment());
//			System.out.println(transaction.getCost());
//			System.out.println(transaction.getDate());
//			System.out.println();
//		}
	}
	
	private HttpResponse getCaptcha(String cookie) throws Exception {
		HttpGetRequest getRequest = new HttpGetRequest(CAPTCHA_IMAGE);
		getRequest.getHeaders().put("Cookie", cookie);
		HttpResponse response = HttpPuller.pull(getRequest);
		
		return response;
	}
	
	private HttpResponse postLogin(String csrf, String cookie, String captcha) throws Exception {
		HttpPostRequest postRequest = new HttpPostRequest(LOGIN_PAGE);
		postRequest.getNameValuePairs().add(new BasicNameValuePair("_csrf_token", csrf));
		postRequest.getNameValuePairs().add(new BasicNameValuePair("xyz", "qweqwe"));
		postRequest.getNameValuePairs().add(new BasicNameValuePair("zyx", "123123"));
		if (captcha != null) {
			postRequest.getNameValuePairs().add(new BasicNameValuePair("captcha", captcha));
		}
		postRequest.getHeaders().put("Cookie", cookie);
		postRequest.getHeaders().put("Origin", "https://mobil.nordea.se");
		postRequest.getHeaders().put("Referer", "https://mobil.nordea.se/banking-nordea/nordea-c3/login.html?");
		HttpResponse response = HttpPuller.pull(postRequest);
		
		return response;
	}


	private HttpResponse getLoginPage() throws Exception {
		HttpGetRequest getRequest = new HttpGetRequest(LOGIN_PAGE);
		HttpResponse response = HttpPuller.pull(getRequest);
		
		return response;
	}


	private HttpResponse getTransactionPage(String cookie) throws Exception {
		HttpGetRequest getRequest = new HttpGetRequest(ACCOUNT_1_PAGE);
		getRequest.getHeaders().put("Cookie", cookie);
		getRequest.getHeaders().put("Referer", LOGIN_PAGE);
		HttpResponse response = HttpPuller.pull(getRequest);
		
		return response;
	}
	
	@Ignore
	@Test
	public void testScrap() {
//		HttpClient client = new DefaultHttpClient();
//		client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BEST_MATCH);
//		HttpGet httpget = new HttpGet("https://mobil.nordea.se/banking-nordea/nordea-c3/login.html");
//		HttpPost httppost = new HttpPost("https://mobil.nordea.se/banking-nordea/nordea-c3/login.html");
//		try {
//			String cookie = "JSESSIONID=9216B59E27845269E107106F0DE661DF.worker1";
//			
//			if (!loggedIn ) {
//				HttpResponse response = client.execute(httpget);
//				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//				String body = "";
//				String line = "";
//				while ((line = br.readLine()) != null) {
//					body += line + "\n";
//				}
//	
//				System.out.println(response.getStatusLine());
//
//				for (Header header : response.getAllHeaders()) {
//					if (header.getName().equals("Set-Cookie")) {
//						cookie = header.getValue();
//						cookie = cookie.substring(0, cookie.indexOf(";"));
//					}
//					System.out.println(header.getName() + ":" + header.getValue());
//				}
//
//				String csrf = HtmlScraper.extractCSRFToken(body);	
//	
//				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
//				nameValuePairs.add(new BasicNameValuePair("_csrf_token", csrf));
//				nameValuePairs.add(new BasicNameValuePair("xyz", "qweqwe"));
//				nameValuePairs.add(new BasicNameValuePair("zyx", "123123"));
//				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//				httppost.setHeader("Cookie", cookie);
//				httppost.setHeader("Origin", "https://mobil.nordea.se");
//				httppost.setHeader("Referer", "https://mobil.nordea.se/banking-nordea/nordea-c3/login.html?");
//				
//				response = client.execute(httppost);
//				br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//				body = "";
//				line = "";
//				while ((line = br.readLine()) != null) {
//					body += line + "\n";
//				}
//				System.out.println(response.getStatusLine());
//				for (Header header : response.getAllHeaders()) {
//					if (header.getName().equals("Set-Cookie")) {
//						cookie = header.getValue();
//						cookie = cookie.substring(0, cookie.indexOf(";"));
//					}
//					System.out.println(header.getName() + ":" + header.getValue());
//				}
//				
//				System.out.println();
//			}
//			
//			HttpGetRequest getRequest = new HttpGetRequest("https://mobil.nordea.se/banking-nordea/nordea-c3/account.html?id=konton:1");
//			getRequest.getHeaders().put("Cookie", cookie);
//			getRequest.getHeaders().put("Referer", "https://mobil.nordea.se/banking-nordea/nordea-c3/login.html");
//			
//			try {
//				se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpResponse response = HttpPuller.pull(getRequest);
//				System.out.println(response.getStatusLine());
//				System.out.println();
//				
//				java.io.File file = new java.io.File("/home/lars/Desktop/body.html");
//				FileWriter writer = new FileWriter(file);
//				writer.write(response.getContent());
//				writer.close();
//				
//				
//				List<Transaction> transactions = HtmlScraper.extractTransactions(response.getContent());
//				Assert.assertTrue(transactions.size() > 0);
//				
//				for (Transaction transaction : transactions) {
//					System.out.println(transaction.getComment());
//					System.out.println(transaction.getCost());
//					System.out.println(transaction.getDate());
//					System.out.println();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				return;
//			}
//			httpget = new HttpGet("https://mobil.nordea.se/banking-nordea/nordea-c3/account.html?id=konton:1");
//			httpget.setHeader("Cookie", cookie);
//			httpget.setHeader("Referer", "https://mobil.nordea.se/banking-nordea/nordea-c3/login.html");
//			for (Header header : httpget.getAllHeaders()) {
//				System.out.println(header.getName() + ":" + header.getValue());
//			}
//			HttpResponse response = client.execute(httpget);
//			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//			String body = "";
//			String line = "";
//			while ((line = br.readLine()) != null) {
//				body += line + "\n";
//			}
//
//			System.out.println();
//			System.out.println(response.getStatusLine());
//			System.out.println();
//			
//			java.io.File file = new java.io.File("/home/lars/Desktop/body.html");
//			FileWriter writer = new FileWriter(file);
//			writer.write(body);
//			writer.close();
//			
//			
//			List<Transaction> transactions = HtmlScraper.extractTransactions(body);
//			Assert.assertTrue(transactions.size() > 0);
//			
//			for (Transaction transaction : transactions) {
//				System.out.println(transaction.getComment());
//				System.out.println(transaction.getCost());
//				System.out.println(transaction.getDate());
//				System.out.println();
//			}
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			httpget.releaseConnection();
//			httppost.releaseConnection();
//		}
	}
}

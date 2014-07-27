package se.aaslin.developer.finance.server.service.scraper;

import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.http.message.BasicNameValuePair;

import se.aaslin.developer.finance.db.entity.Category;
import se.aaslin.developer.finance.db.entity.File;
import se.aaslin.developer.finance.db.entity.TimeFrame;
import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.server.backend.scraper.CaptchaBreaker;
import se.aaslin.developer.finance.server.backend.scraper.HtmlScraper;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpGetRequest;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpPostRequest;
import se.aaslin.developer.finance.server.backend.scraper.HttpPuller.HttpResponse;
import se.aaslin.developer.finance.server.dao.FileDAO;
import se.aaslin.developer.finance.server.dao.TransactionDAO;
import se.aaslin.developer.finance.server.service.TimeFrameService;
import se.aaslin.developer.finance.server.service.file.FileAccountServiceLocalBusiness;

@Stateless
public class TransactionScraperServiceImpl implements TransactionScraperServiceLocalBusiness, TransactionScraperServiceMBean {

	private static final String ACCOUNT_1_PAGE = "https://mobil.nordea.se/banking-nordea/nordea-c3/account.html?id=konton:1";
	private static final String LOGIN_PAGE = "https://mobil.nordea.se/banking-nordea/nordea-c3/login.html";
	private static final String CAPTCHA_IMAGE = "https://mobil.nordea.se/banking-nordea/nordea-c3/captcha.png";
	private static final String USERNAME = "qweqwe";
	private static final String PASSWORD = "123123";
	
	private static class TransactionKey {
		
		private Transaction transaction;
		
		public TransactionKey(Transaction transaction) {
			this.transaction = transaction;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((transaction.getComment() == null) ? 0 : transaction.getComment().hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TransactionKey other = (TransactionKey) obj;
			if (transaction.getComment() == null) {
				if (other.transaction.getComment() != null)
					return false;
			} else if (!transaction.getComment().equals(other.transaction.getComment()))
				return false;
			return true;
		}
	}
	
	@Inject	TransactionDAO transactionDAO;
	@Inject FileDAO fileDAO;
	@Inject TimeFrameService timeFrameService;
	@Inject FileAccountServiceLocalBusiness fileAccounter;
	
	@Override
//	@Schedule(hour = "*", minute = "*/5")
	public void scrap() throws Exception {
		String cookie = "";
		String captcha = null;
		
		HttpResponse response = getLoginPage();
		String csrf = HtmlScraper.extractCSRFToken(response.getContentAsString());	
		cookie = response.getSetCookie();
		response = postLogin(csrf, cookie, USERNAME, PASSWORD, null);
		if (HtmlScraper.cointainsCaptcha(response.getContentAsString())) {
			csrf = HtmlScraper.extractCSRFToken(response.getContentAsString());
			response = getCaptcha(cookie);
			captcha = new CaptchaBreaker().breakCaptcha(new ByteArrayInputStream(response.getRawContent()));
			response = postLogin(csrf, cookie, USERNAME, PASSWORD, captcha);
		}
		cookie = response.getSetCookie();
		response = getTransactionPage(cookie);
		List<Transaction> transactions = HtmlScraper.extractTransactions(response.getContentAsString());  
		//TODO check for empty list
		storeNewTransactions(transactions);
	}

	private void storeNewTransactions(List<Transaction> newTransactions) {
		Comparator<Transaction> transactionComparator = new Comparator<Transaction>() {

			@Override
			public int compare(Transaction o1, Transaction o2) {
				int c = o1.getDate().compareTo(o2.getDate());
				if (c == 0) {
					c = o1.getComment().compareTo(o2.getComment());
				}
				if (c == 0) {
					c = o1.getCost().compareTo(o2.getCost());
				}
				return c;
			}
		};

		Collections.sort(newTransactions, transactionComparator);
		Date firstDate = newTransactions.get(0).getDate();
		
		List<Transaction> existingTransactions = transactionDAO.getTransactions(firstDate);		
		Collections.sort(existingTransactions, transactionComparator);
		
		Map<TransactionKey, Category> categoryMap = createCategoryMap(existingTransactions);
		
		for (Transaction transaction : newTransactions) {
			TransactionKey key = new TransactionKey(transaction);
			if (categoryMap.containsKey(key)) {
				Category category = categoryMap.get(key);
				transaction.setCategory(category);
			} else {
				Category category = fileAccounter.account(transaction);
				transaction.setCategory(category);
			}
		}
		
		removeExistingTransactions(existingTransactions);
		saveTransactions(newTransactions);
	}


	private void removeExistingTransactions(List<Transaction> existingTransactions) {
		for (Transaction transaction : existingTransactions) {
			transactionDAO.delete(transaction);
		}
	}

	private Map<TransactionKey, Category> createCategoryMap(List<Transaction> existingTransactions) {
		Map<TransactionKey, Category> result = new HashMap<TransactionKey, Category>();
		for (Transaction transaction : existingTransactions) {
			if (transaction.getCategory() != null) {
				TransactionKey key = new TransactionKey(transaction);
				result.put(key, transaction.getCategory());
			}
		}
		
		return result;
	}

	private void saveTransactions(List<Transaction> transactions) {
		for (Transaction transaction : transactions) {
			Date date = transaction.getDate();
			TimeFrame timeFrame = timeFrameService.getOrCreateMonthTimeFrame(date);
			File file = fileDAO.findFile(date);
			if (file == null) {
				file = new File();
				file.setName(new SimpleDateFormat("MMMMyyyy").format(date) + ".xlsx");
				file.setTimeFrame(timeFrame);
				fileDAO.create(file);
			}
			transaction.setFile(file);
			transactionDAO.create(transaction);
		}
	}
	
	private HttpResponse postLogin(String csrf, String cookie, String username, String password, String captcha) throws Exception {
		HttpPostRequest postRequest = new HttpPostRequest(LOGIN_PAGE);
		postRequest.getNameValuePairs().add(new BasicNameValuePair("_csrf_token", csrf));
		postRequest.getNameValuePairs().add(new BasicNameValuePair("xyz", username));
		postRequest.getNameValuePairs().add(new BasicNameValuePair("zyx", password));
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
	
	private HttpResponse getCaptcha(String cookie) throws Exception {
		HttpGetRequest getRequest = new HttpGetRequest(CAPTCHA_IMAGE);
		getRequest.getHeaders().put("Cookie", cookie);
		HttpResponse response = HttpPuller.pull(getRequest);
		
		return response;
	}
}

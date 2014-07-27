package se.aaslin.developer.finance.server.backend.scraper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import se.aaslin.developer.finance.db.entity.Transaction;

public class HtmlScraper {
	
	public static class HtmlScraperException extends Exception {
		private static final long serialVersionUID = 5054483195887157919L;

		public HtmlScraperException(Exception e, String body) {
			super(e.getMessage() + "\n\n" + body, e);
		}
	}
	
	public static List<Transaction> extractTransactions(String body) throws HtmlScraperException {
		List<Transaction> transactions = new ArrayList<Transaction>();
		try {
			body = body.substring(body.indexOf("<div class=\"transaction-list-wrapper\">"));
		} catch (IndexOutOfBoundsException e) {
			throw new HtmlScraperException(e, body);
		}
		String[] transactionBlocks = body.split("</dl>");
		for (String transactionBlock : transactionBlocks) {
			// skip last
			if (transactionBlock.equals(transactionBlocks[transactionBlocks.length - 1])) {
				break;
			}
			Transaction transaction = extractTransaction(transactionBlock);
			transactions.add(transaction);
		}
		
		return transactions;
	}

	private static Transaction extractTransaction(String transaction) throws HtmlScraperException {
		String t = transaction.substring(transaction.indexOf("<dt class=\"account-list-left\">") + "<dt class=\"account-list-left\">".length());
		try {
			String date = t.substring(0, t.indexOf("</dt>")).trim();
			t = t.substring(t.indexOf("<dt class=\"account-list-left\">") + "<dt class=\"account-list-left\">".length());
			String comment = t.substring(0, t.indexOf("</dt>")).trim()
					.replace("&ouml;", "ö").replace("&Ouml;", "Ö")
					.replace("&auml;", "ä").replace("&Auml;", "Ä")
					.replace("&aring;", "å").replace("%Aring", "Å");
			t = t.substring(t.indexOf("<span class=\"transaction") + "<span class=\"transaction".length());
			t = t.substring(t.indexOf(">") + ">".length());
			String cost = t.substring(0, t.indexOf("</span>")).trim().replace(".", "").replace(",", ".");
			
			Transaction trans = new Transaction();
			trans.setComment(comment);
			trans.setCost(new BigDecimal(cost));
			trans.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
			
			return trans;
		} catch (IndexOutOfBoundsException e) {
			throw new HtmlScraperException(e, transaction);
		} catch (ParseException e) {
			throw new HtmlScraperException(e, transaction);
		}
	}
	
	public static String extractCSRFToken(String body) {
		int start = body.indexOf("<input type=\"hidden\" name=\"_csrf_token\"");
		String input = body.substring(start);
		int stop = input.indexOf("/>");
		input = input.substring(0, stop + 2);
		start = input.indexOf("value=\"") + "value=\"".length();
		input = input.substring(start);
		stop = input.indexOf("\" />");
		input = input.substring(0, stop);
		return input;
	}

	public static boolean cointainsCaptcha(String contentAsString) {
		return contentAsString.contains("inloggningar med ditt personnummer idag, ber vi dig fylla i uppgifterna nedan");
	}
}

package se.aaslin.developer.finance.server.backend.scraper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.editor.client.Editor.Ignore;

import se.aaslin.developer.finance.db.entity.Transaction;
import se.aaslin.developer.finance.server.backend.scraper.HtmlScraper.HtmlScraperException;

public class HtmlScrapTest {

	private String transactionFile;
	private String csrfFile;
	
	@Before
	public void setup() throws IOException {
		String fileName = this.getClass().getResource("/se/aaslin/developer/finance/server/backend/scraper/body.html").getFile();
		BufferedReader reader = new BufferedReader(new FileReader(fileName)); 
		String line = "";
		while ((line = reader.readLine()) != null) {
			transactionFile += line + "\n";
		}
		
		fileName = this.getClass().getResource("/se/aaslin/developer/finance/server/backend/scraper/body2.html").getFile();
		reader = new BufferedReader(new FileReader(fileName)); 
		line = "";
		while ((line = reader.readLine()) != null) {
			csrfFile += line + "\n";
		}
	}
	
	@Ignore
	@Test
	public void testExtractTransactions() throws HtmlScraperException, ParseException {
		List<Transaction> transactions = HtmlScraper.extractTransactions(transactionFile);
		Assert.assertNotNull(transactions);
		Assert.assertEquals(4, transactions.size());
		Assert.assertEquals("Reservation Kortköp", transactions.get(0).getComment());
		Assert.assertEquals(new BigDecimal("-42.00"), transactions.get(0).getCost());
		Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("2012-09-11"), transactions.get(0).getDate());
	}

	@Ignore
	@Test
	public void testExtractCSRFToken() {
		String csrfToken = HtmlScraper.extractCSRFToken(csrfFile);
		Assert.assertEquals("lWoiHTOevUiXKljEgXMR5UIls5SqS2BommPX2YZUmak", csrfToken);
	}
}

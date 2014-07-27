package se.aaslin.developer.finance.server.backend.scraper;

import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

public class CaptchaBreakerTest {

	@Test
	public void testCaptchaBreaker() throws Exception {
		InputStream captcha = this.getClass().getResourceAsStream("/se/aaslin/developer/finance/server/backend/scraper/captcha.png");
		String numberString = new CaptchaBreaker().breakCaptcha(captcha);
		
		Assert.assertEquals("54759", numberString);
	}
}

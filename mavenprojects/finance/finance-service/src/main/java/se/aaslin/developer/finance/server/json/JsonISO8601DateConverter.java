package se.aaslin.developer.finance.server.json;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.spi.StringConverter;

@Provider
public class JsonISO8601DateConverter implements StringConverter<Date> {
	
	private static final DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
	
	@Override
	public Date fromString(String str) {
		try {
			return iso8601Format.parse(str);
		} catch (ParseException e) {
			return null;
		}
	}

	@Override
	public String toString(Date value) {
		return iso8601Format.format(value);
	}
}

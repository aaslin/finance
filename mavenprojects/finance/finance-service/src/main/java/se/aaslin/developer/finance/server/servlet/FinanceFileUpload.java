package se.aaslin.developer.finance.server.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;

import se.aaslin.developer.finance.server.backend.util.timeframe.TimeFrameGenerator;
import se.aaslin.developer.finance.server.service.file.FileImportServiceLocalBusiness;

public class FinanceFileUpload extends HttpServlet {
	private static final Logger logger = Logger.getLogger(FinanceFileUpload.class);
	private static final long serialVersionUID = 3990229042104726240L;

	@Inject FileImportServiceLocalBusiness fileImporter;
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ServletFileUpload upload = new ServletFileUpload();
			Map<String, ByteArrayOutputStream> params = new HashMap<String, ByteArrayOutputStream>();
			
			
			FileItemIterator iter = upload.getItemIterator(request);
			String fileName = null;
			ByteArrayInputStream fileStream;
			int year;
			int month;
			while (iter.hasNext()) {
				FileItemStream param = iter.next();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				IOUtils.copy(param.openStream(), out);
				String key = param.getFieldName();
				if (key.equals("file")) {
					fileName = param.getName();
				}
				params.put(key, out);
			}
			
			ByteArrayOutputStream out = params.get("file");
			if (out != null && fileName != null) {
				fileStream = new ByteArrayInputStream(out.toByteArray());
			} else {
				throw new Exception("Received request without file");
			}
			
			out = params.get("year");
			if (out != null) {
				year = Integer.parseInt(new String(out.toByteArray()));
			} else {
				throw new Exception("Received request without year");
			}
			
			out = params.get("month");
			if (out != null) {
				month = Integer.parseInt(new String(out.toByteArray())) + 1;
			} else {
				throw new Exception("Received request without month");
			}
			
			Calendar cal = Calendar.getInstance();
			cal.set(year, month, 0);
			String tag = TimeFrameGenerator.createMonthTimeFrame(cal.getTime()).getTag();
			
			
			fileImporter.importFile(fileName, tag, fileStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

package se.aaslin.developer.finance.server.backend.common.importer;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;

import se.aaslin.developer.finance.server.backend.common.ErrorMessage;
import se.aaslin.developer.finance.server.backend.common.exception.SSUtilDataFormatException;
import se.aaslin.developer.finance.server.backend.common.exception.SSUtilMissingDataException;

public class SSUtil {

	private static final int MAX_YEAR = 2100;
	private static final int MIN_YEAR = 1900;
	private final Logger log = Logger.getLogger(SSUtil.class);

	private static Map<String, SimpleDateFormat> patternSDFs;
	private static Map<String, SimpleDateFormat> patternTime;
	private final GregorianCalendar calendar = new GregorianCalendar();

	static {
		patternSDFs = new HashMap<String, SimpleDateFormat>();
		patternSDFs.put("\\d{4}-\\d{2}-\\d{2}", new SimpleDateFormat("yyyy-MM-dd"));
		patternSDFs.put("\\d{8}", new SimpleDateFormat("yyyyMMdd"));
		patternSDFs.put(".{28}", new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy"));
		patternSDFs.put("\\d{2}:\\d{2}", new SimpleDateFormat("HH:mm"));
		patternSDFs.put("\\d{2}:\\d{2}:\\d{2}", new SimpleDateFormat("HH:mm:ss"));
		patternSDFs.put("\\d{2}:\\d{2}\\p{javaWhitespace}(?i:[ap]m)", new SimpleDateFormat("HH:mm a"));
		patternSDFs.put("\\d{2}:\\d{2}:\\d{2}\\p{javaWhitespace}(?i:[ap]m)", new SimpleDateFormat("HH:mm:ss a"));
	}
	static {
		patternTime = new HashMap<String, SimpleDateFormat>();
		patternTime.put("\\d{2}:\\d{2}", new SimpleDateFormat("HH:mm"));
		patternTime.put("\\d{2}:\\d{2}:\\d{2}", new SimpleDateFormat("HH:mm:ss"));
		patternTime.put("\\d{2}:\\d{2}\\p{javaWhitespace}(?i:[ap]m)", new SimpleDateFormat("HH:mm a"));
		patternTime.put("\\d{2}:\\d{2}:\\d{2}\\p{javaWhitespace}(?i:[ap]m)", new SimpleDateFormat("HH:mm:ss a"));
		patternTime.put("\\d{2}\\d{2}", new SimpleDateFormat("HHmm"));
		patternTime.put("\\d{2}\\d{2}\\d{2}", new SimpleDateFormat("HHmmss"));
		patternTime.put("\\d{2}\\d{2}.\\d{1}", new SimpleDateFormat("HHmm.s"));
		patternTime.put("\\d{1}\\d{2}.\\d{1}", new SimpleDateFormat("Hmm.s"));

	}
	private final DataFormatter dataFormatter = new DataFormatter();

	public Date getCellDateValue(Cell cell) throws SSUtilDataFormatException, SSUtilMissingDataException {
		if ((cell != null) && (cell.getCellType() != Cell.CELL_TYPE_BLANK)) {
			java.util.Date date = null;

			// some people enter dates as text and not as dates
			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {

				String cellData = cell.getStringCellValue();
				date = parseDateString(cellData);
			} else {
				date = cell.getDateCellValue();
			}

			// as a last resort, if we don't have a reasonable date
			// try reading as numeric value, convert to string and parse.
			if (!isDateSane(date) && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				String cellData = Long.toString((long) cell.getNumericCellValue());
				date = parseDateString(cellData);
			}
			if (date == null) {
				String msg = String.format("Kan inte konvertera värdet på %s till ett datum", cellRef(cell));
				throw new SSUtilDataFormatException(ErrorMessage.WRONG_DATA_FORMAT, new String[] { msg });
			}

			calendar.setTime(date);

			// sanity check for date
			if (!isDateSane(date)) {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String dateString = dateFormat.format(date);
				String msg = String.format("Värdet (%s) på %s är inget giltigt datum", dateString, cellRef(cell));
				throw new SSUtilDataFormatException(ErrorMessage.WRONG_DATA_FORMAT, new String[] { msg });
			}
			return new Date(calendar.getTimeInMillis());
		}
		throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA);
	}

	private boolean isDateSane(java.util.Date date) {
		if (date == null) {
			return false;
		}

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		if (year > MAX_YEAR || year < MIN_YEAR) {
			return false;
		}
		return true;
	}

	private java.util.Date parseDateString(String dateString) {
		java.util.Date date = null;
		for (String pattern : patternSDFs.keySet()) {
			if (dateString.matches(pattern)) {
				try {
					date = patternSDFs.get(pattern).parse(dateString);
				} catch (ParseException pe) {
					// System.out.println("Could not parse date string" +
					// pe.getMessage());
					log.warn("Could not parse date string" + pe.getMessage(), pe);
				}
				break;
			}
		}
		return date;
	}

	/**
	 * Try to get the time in a proper way
	 *
	 * @param timeString
	 * @return
	 */
	private java.util.Date parseTimeString(String timeString) {
		java.util.Date time = null;
		for (String pattern : patternTime.keySet()) {
			if (timeString.matches(pattern)) {
				try {
					time = patternTime.get(pattern).parse(timeString);
				} catch (ParseException pe) {
					// System.out.println("Could not parse date string" +
					// pe.getMessage());
					log.warn("Could not parse date string" + pe.getMessage(), pe);
				}
				break;
			}
		}
		return time;
	}

	public Time getCellTimeValue(Cell cell) throws SSUtilDataFormatException, SSUtilMissingDataException {
		if (cell != null) {
			java.util.Date time = null;

			// some people enter time as text and not as times

			// In Relacom delivery files time is formatted as double (08:31 is
			// entered as 831.0).

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String cellData = cell.getStringCellValue();
				time = parseTimeString(cellData);
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				double cellData = cell.getNumericCellValue();
				String cellDataAsString = Double.toString(cellData);
				time = parseTimeString(cellDataAsString);
				if (time == null) {
					time = cell.getDateCellValue();
				}
			} else {
				time = cell.getDateCellValue();
			}
			if (time == null) {
				String msg = String.format("Kan inte konvertera värdet på %s till en tid", cellRef(cell));
				throw new SSUtilDataFormatException(ErrorMessage.WRONG_DATA_FORMAT, new String[] { msg });
			}

			calendar.setTime(time);
			return new Time(calendar.getTimeInMillis());
		}
		throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA);
	}

	public String getCellStringValue(Cell cell) throws SSUtilMissingDataException, SSUtilDataFormatException {
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return String.format("%.0f", cell.getNumericCellValue());
			} else {
				try {
					return dataFormatter.formatCellValue(cell);
				} catch (Exception e) {
					String msg = String.format("Kan inte tolka innehållet i cell " + cellRef(cell));
					throw new SSUtilDataFormatException(ErrorMessage.WRONG_DATA_FORMAT, new String[] { msg });
				}
			}
		}
		throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA);
	}

	public Double getCellDoubleValue(Cell cell) throws SSUtilMissingDataException, SSUtilDataFormatException {
		Double cellData = null;
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cellData = cell.getNumericCellValue();
			} else {
				String tmp = dataFormatter.formatCellValue(cell).trim();
				// some cells end with a dash (-) which is...odd
				if (tmp.endsWith("-")) {
					tmp = tmp.substring(0, tmp.length() - 1);
				}
				try {
					cellData = Double.parseDouble(tmp.replaceAll(",", "."));
				} catch (NumberFormatException e) {
					String msg = String.format("Kan inte konvertera värdet på %s till ett tal", cellRef(cell));
					throw new SSUtilDataFormatException(ErrorMessage.WRONG_DATA_FORMAT, new String[] { msg });
				}
			}
		}
		if (cellData == null) {
			throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA);
		}
		return cellData;
	}

	/**
	 * Attempt to interpret cell content as a boolean value.
	 *
	 * @param cell
	 * @return
	 * @throws SSUtilMissingDataException
	 * @throws SSUtilDataFormatException
	 */
	public Boolean getCellBooleanValue(Cell cell) throws SSUtilMissingDataException, SSUtilDataFormatException {
		Double cellData = null;
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				cellData = cell.getNumericCellValue();
				if (cellData >= 1) {
					return true;
				}
			} else {
				String tmp = cell.getStringCellValue().trim();
				if (tmp.equals("1")) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA);
		}
		return false;
	}

	public static String cellRef(Cell cell) {
		return new CellReference(cell.getRowIndex(), cell.getColumnIndex()).formatAsString();
	}

	public static String cellRef(int row, int col) {
		return new CellReference(row, col).formatAsString();
	}

}

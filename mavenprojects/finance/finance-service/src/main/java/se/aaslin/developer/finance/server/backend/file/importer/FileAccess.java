package se.aaslin.developer.finance.server.backend.file.importer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import se.aaslin.developer.finance.server.backend.common.ErrorMessage;
import se.aaslin.developer.finance.server.backend.common.exception.SSUtilDataFormatException;
import se.aaslin.developer.finance.server.backend.common.exception.SSUtilMissingDataException;
import se.aaslin.developer.finance.server.backend.common.importer.SSUtil;

public class FileAccess {
	private SSUtil ssUtil = new SSUtil();

	/**
	 * Puts a String value in the cell. First trim off white spaces
	 *
	 * @param row
	 * @param fh
	 * @return
	 * @throws SSUtilDataFormatException
	 * @throws SSUtilMissingDataException
	 */
	private String cell2string2(Row row, FileHeader fh) throws SSUtilDataFormatException, SSUtilMissingDataException {
		Cell cell = row.getCell(fh.getColNr());
		if (cell != null) {
			String result = null;
			try {
				result = ssUtil.getCellStringValue(cell);
			} catch (SSUtilMissingDataException e) {
				if (!fh.isEmptyAllowed()) {
					throw e;
				}
			}
			return result.trim();
		} else if (fh.isEmptyAllowed()) {
			return null;
		}

		String errorMessage = String.format("Saknar data på %s", SSUtil.cellRef(row.getRowNum(), fh.getColNr()));
		throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA, new String[] { errorMessage });
	}

	/**
	 * Check for empty string.
	 *
	 * @param row
	 * @param fh
	 * @return
	 * @throws SSUtilDataFormatException
	 * @throws SSUtilMissingDataException
	 */
	private String cell2String(Row row, FileHeader fh) throws SSUtilDataFormatException, SSUtilMissingDataException {
		String val = cell2string2(row, fh);
		if ((val == null || val.isEmpty()) && !fh.isEmptyAllowed()) {
			throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA, new String[] { String.format("saknar data på %s",
					SSUtil.cellRef(row.getRowNum(), fh.getColNr())) });
		}
		return val;
	}

	private Double cell2Double(Row row, FileHeader fh) throws SSUtilDataFormatException, SSUtilMissingDataException {
		Cell cell = row.getCell(fh.getColNr());
		Double result = null;
		if (cell != null) {
			try {
				result = ssUtil.getCellDoubleValue(cell);
			} catch (SSUtilMissingDataException e) {
				if (!fh.isEmptyAllowed()) {
					throw e;
				}
			}
			return result;
		} else if (fh.isEmptyAllowed()) {
			return null;
		}
		String errorMessage = String.format("Saknar data på %s", SSUtil.cellRef(row.getRowNum(), fh.getColNr()));
		throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA, new String[] { errorMessage });
	}

	private Date cell2Date(Row row, FileHeader fh) throws SSUtilDataFormatException, SSUtilMissingDataException {
		Cell cell = row.getCell(fh.getColNr());
		Date result = null;
		if (cell != null) {
			try {
				result = ssUtil.getCellDateValue(cell);
			} catch (SSUtilMissingDataException e) {
				if (!fh.isEmptyAllowed()) {
					throw e;
				}
			}
			return result;
		} else if (fh.isEmptyAllowed()) {
			return null;
		}
		String errorMessage = String.format("Saknar data på %s", SSUtil.cellRef(row.getRowNum(), fh.getColNr()));
		throw new SSUtilMissingDataException(ErrorMessage.MISSING_DATA, new String[] { errorMessage });
	}
	
	public Date getDate(Row row) throws SSUtilDataFormatException, SSUtilMissingDataException {
		return cell2Date(row, FileHeader.DATE);
	}
	
	public String getTransaction(Row row) throws SSUtilDataFormatException, SSUtilMissingDataException {
		return cell2String(row, FileHeader.TRANSACTION);
	}
	
	public String getCategory(Row row) throws SSUtilDataFormatException, SSUtilMissingDataException {
		return cell2String(row, FileHeader.CATEGORY);
	}
	
	public BigDecimal getCost(Row row) throws SSUtilDataFormatException, SSUtilMissingDataException {
		return new BigDecimal(cell2Double(row, FileHeader.COST)).setScale(2, RoundingMode.HALF_UP);
	}
}
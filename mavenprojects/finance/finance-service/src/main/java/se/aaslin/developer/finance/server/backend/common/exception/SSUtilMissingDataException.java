package se.aaslin.developer.finance.server.backend.common.exception;

import se.aaslin.developer.finance.server.backend.common.ErrorMessage;

public class SSUtilMissingDataException extends FinanceApplicationException {

	private static final long serialVersionUID = -3878315553936713450L;

	/**
	 * @param messageEnum
	 * @param msgObjects
	 * @param e
	 */
	public SSUtilMissingDataException(ErrorMessage messageEnum, Object[] msgObjects, Throwable e) {
		super(messageEnum, msgObjects, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageEnum
	 * @param msgObjects
	 */
	public SSUtilMissingDataException(ErrorMessage messageEnum, Object[] msgObjects) {
		super(messageEnum, msgObjects);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageEnum
	 * @param e
	 */
	public SSUtilMissingDataException(ErrorMessage messageEnum, Throwable e) {
		super(messageEnum, e);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param messageEnum
	 */
	public SSUtilMissingDataException(ErrorMessage messageEnum) {
		super(messageEnum);
		// TODO Auto-generated constructor stub
	}

}

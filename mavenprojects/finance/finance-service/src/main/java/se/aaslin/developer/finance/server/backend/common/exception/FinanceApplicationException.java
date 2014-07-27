package se.aaslin.developer.finance.server.backend.common.exception;

import se.aaslin.developer.finance.server.backend.common.ErrorMessage;

/**
 * General application Exception for the web tier of TheCube application.
 * 
 * @author samuelwirzen
 *
 */
public class FinanceApplicationException extends AbstractApplicationException {

	private static final long serialVersionUID = 2293371432192883215L;

	/**
	 * Constructor taking only an error message enum.
	 *
	 * @param messageEnum
	 *            the enum indicating the message to use for the exception.
	 */
	public FinanceApplicationException(ErrorMessage messageEnum) {
		super(messageEnum);
	}

	/**
	 * Constructor taking an error message enum and a set of objects to be
	 * inserted into the message defined by the error message enum.
	 *
	 * @param messageEnum
	 *            the message enum to use, giving the message to be used when
	 *            logging.
	 * @param msgObjects
	 *            the objects to insert into the message.
	 */
	public FinanceApplicationException(ErrorMessage messageEnum, Object[] msgObjects) {
		super(messageEnum, msgObjects);
	}

	/**
	 * Constructor taking only error message enum, encapsulates another
	 * exception.
	 *
	 * @param messageEnum
	 *            the message enum to use, giving the message to be used when
	 *            logging.
	 * @param e
	 *            the throwable to encapsulate.
	 */
	public FinanceApplicationException(ErrorMessage messageEnum, Throwable e) {
		super(messageEnum, e);
	}

	/**
	 * Constructor taking an error message enum and a set of objects to be
	 * inserted into the message defined by the error message enum, as well as a
	 * throwable to encapsulate.
	 *
	 * @param messageEnum
	 *            the message enum to use, giving the message to be used when
	 *            logging.
	 * @param msgObjects
	 *            the objects to insert into the message.
	 * @param e
	 *            the throwable to encapsulate.
	 */
	public FinanceApplicationException(ErrorMessage messageEnum, Object[] msgObjects, Throwable e) {
		super(messageEnum, msgObjects, e);
	}

}

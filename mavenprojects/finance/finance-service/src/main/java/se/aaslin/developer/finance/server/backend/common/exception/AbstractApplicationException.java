package se.aaslin.developer.finance.server.backend.common.exception;

import se.aaslin.developer.finance.server.backend.common.ErrorMessage;


/**
 * Abstract parent class for all ApplicationExceptions.
 * 
 * @author samuelwirzen
 * 
 */
public abstract class AbstractApplicationException extends Exception {

	private static final long serialVersionUID = -4866095880026889476L;

	/**
	 * The error message for this exception.
	 */
	private ErrorMessage errorMessage;

	/**
	 * Error message data.
	 */
	private final Object[] errorData;

	/**
	 * Constructor that sets the error code upon exception creation.
	 * 
	 * @param errorMessage
	 *            The error message to set.
	 */
	public AbstractApplicationException(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
		errorData = null;
	}

	/**
	 * Constructor that sets the error code and error data upon exception
	 * creation.
	 * 
	 * @param errorMessage
	 *            The error message to set.
	 * @param errorData
	 *            The error data to set.
	 */
	public AbstractApplicationException(ErrorMessage errorMessage, Object[] errorData) {
		this.errorMessage = errorMessage;
		this.errorData = errorData;
	}

	/**
	 * Constructor that sets the error code and a throwable. Used when wrapping
	 * or transforming another exception.
	 * 
	 * @param errorMessage
	 *            The error message to set.
	 * @param throwable
	 *            The throwable to set.
	 */
	public AbstractApplicationException(ErrorMessage errorMessage, Throwable throwable) {
		super(throwable);
		errorData = null;
		this.errorMessage = errorMessage;
	}

	/**
	 * Constructor that sets the error code, error data and a throwable. Used
	 * when wrapping or transforming another exception.
	 * 
	 * @param errorMessage
	 *            The error message to set.
	 * @param errorData
	 *            The error data to set.
	 * @param throwable
	 *            The throwable to set.
	 */
	public AbstractApplicationException(ErrorMessage errorMessage, Object[] errorData, Throwable throwable) {
		super(throwable);
		this.errorMessage = errorMessage;
		this.errorData = errorData;
	}

	/**
	 * Returns the errorMessage.
	 * 
	 * @return Returns the errorMessage.
	 */
	public ErrorMessage getErrorMessage() {
		return this.errorMessage;
	}
	
	/**
	 * Returns the error data.
	 * 
	 * @return Returns the errorData.
	 */
	public Object[] getErrorData() {
		return errorData;
	}

}

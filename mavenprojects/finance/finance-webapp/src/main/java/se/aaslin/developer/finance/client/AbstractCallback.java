package se.aaslin.developer.finance.client;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.user.client.Window;

public abstract class AbstractCallback<T> implements MethodCallback<T>{
	
	protected abstract void onSuccess(T response);
	
	@Override
	public final void onSuccess(Method method, T response) {
		onSuccess(response);
	}

	@Override
	public void onFailure(Method method, Throwable exception) {
		Window.alert(exception.getMessage());
	}
}

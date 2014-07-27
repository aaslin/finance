package se.aaslin.developer.finance.client.common.widget;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.TextBoxBase;

public class HiddenInput extends TextBoxBase {

	@UiConstructor
	public HiddenInput(String name) {
       super(DOM.createElement("input"));
       DOM.setElementProperty(getElement(), "type", "hidden");
       DOM.setElementProperty(getElement(), "name", name);
    }
} 

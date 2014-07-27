package se.aaslin.developer.finance.client.menu.view;

import se.aaslin.developer.finance.client.menu.presenter.AbstractSubMenuPresenter;
import se.aaslin.developer.finance.client.menu.presenter.AbstractSubMenuPresenter.SubMenu;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.LIElement;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class SubMenuView extends Composite implements AbstractSubMenuPresenter.View {
	public interface SubMenuViewUIBinder extends UiBinder<HTMLPanel, SubMenuView> {
	}
	
	public interface Style extends CssResource {
		
		String active();
		
		String hyperlinkStyle();
		
		String finance();
		
		String management();
		
		String account();
	}
	
	public class SubMenuBar extends Widget {

		private UListElement list;
		
		public SubMenuBar() {
			list = Document.get().createULElement();		
			setElement(list);
		}
		
		public void addMenu(String name, String historyToken, boolean isActive) {
			LIElement element = Document.get().createLIElement();
			Hyperlink link = new Hyperlink(name, historyToken);
			if (isActive) {
				element.setClassName(style.active());
			}
			element.appendChild(link.getElement());
			list.appendChild(element);
		}
	}
	
	SubMenuViewUIBinder uiBinder = GWT.create(SubMenuViewUIBinder.class);
	
	@UiField FlowPanel submenu;
	@UiField Style style;
	@UiField Label title;
	
	SubMenuBar menuBar;
	
	public SubMenuView() {
		initWidget(uiBinder.createAndBindUi(this));
		menuBar = new SubMenuBar();
		submenu.add(menuBar);
	}

	@Override
	public void addMenu(String name, String token, boolean isActive) {
		menuBar.addMenu(name, token, isActive);
	}

	@Override
	public void setMenuTitle(SubMenu subMenu) {
		String name = "";
		String styleName = "";
		switch (subMenu) {
		case FINANCE:
			name = "Finance";
			styleName = style.finance();
			break;
		case MANAGEMENT:
			name = "Mangement";
			styleName = style.management();
			break;
		case ACCOUNT:
			name = "Account";
			styleName = style.account();
		default:
			break;
			
		}
		this.title.setText(name);
		this.title.addStyleName(styleName);
	}
}

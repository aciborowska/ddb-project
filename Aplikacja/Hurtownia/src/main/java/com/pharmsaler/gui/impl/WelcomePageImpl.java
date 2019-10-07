package com.pharmsaler.gui.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.dao.AccountRepository;
import com.pharmsaler.dao.PharmacyRepository;
import com.pharmsaler.gui.view.WelcomePage;
import com.pharmsaler.model.Account;
import com.pharmsaler.model.Pharmacy;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class WelcomePageImpl extends WelcomePage implements View {

	private static final long serialVersionUID = -7486939944865652123L;

	@Autowired
	private AccountRepository accountRepo;

	@Autowired
	private PharmacyRepository pharmacyRepo;

	@Autowired
	private MainViewImpl mainView;

	public WelcomePageImpl() {
		this.addStyleName("background");
		VerticalLayout layout = (VerticalLayout) ((Panel) getComponent(0)).getContent();
		TextField login = (TextField) layout.getComponent(1);
		PasswordField password = (PasswordField) layout.getComponent(3);
		Button log = (Button) layout.getComponent(4);
		log.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				List<Account> accounts = accountRepo.findByLoginAndPassword(login.getValue(),
						password.getValue());
				if (!accounts.isEmpty()) {
					log.setComponentError(null);
					Account account = accounts.get(0);
					Pharmacy pharmacy = pharmacyRepo.findByAccount(account);
					getSession().setAttribute(Pharmacy.class, pharmacy);
					UI.getCurrent().setContent(mainView);
					mainView.init();
				} else {
					log.setComponentError(new UserError("Błędny login i/lub hasło"));
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		if (getSession().getAttribute(Pharmacy.class) != null) {
			UI.getCurrent().setContent(mainView);
			mainView.init();
		}
		Page.getCurrent().reload();
	}

}

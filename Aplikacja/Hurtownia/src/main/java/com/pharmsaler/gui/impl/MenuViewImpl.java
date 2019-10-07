package com.pharmsaler.gui.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.gui.view.MenuView;
import com.pharmsaler.model.Pharmacy;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SpringComponent
@UIScope
public class MenuViewImpl extends MenuView {

	private static final long serialVersionUID = 9119725924344792467L;

	private Navigator navigator;

	private Label name;

	@Autowired
	private WelcomePageImpl welcomePage;

	public void init(Navigator navigator) {
		this.navigator = navigator;
		Pharmacy pharmacy = getSession().getAttribute(Pharmacy.class);
		name.setValue(pharmacy.getName());
	}

	public MenuViewImpl() {
		name = (Label) getComponent(2);

		Button medicines = (Button) getComponent(3);
		medicines.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(VersionListViewImpl.TAG);
			}
		});

		Button suppliers = (Button) getComponent(4);
		suppliers.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(SuppliersListImpl.TAG);

			}
		});

		Button orders = (Button) getComponent(5);
		orders.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(OrderListViewImpl.TAG);
			}
		});

		Button newOrder = (Button) getComponent(6);
		newOrder.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(NewOrderViewImpl.TAG);
			}
		});

		Button info = (Button) getComponent(7);
		info.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(PharmacyInfoViewImpl.TAG);
			}
		});

		Button logout = (Button) getComponent(8);
		logout.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getSession().setAttribute(Pharmacy.class, null);
				navigator.destroy();
				Notification.show("Wylogowano");
				UI.getCurrent().setContent(welcomePage);
				Page.getCurrent().reload();
			}
		});
	}

}
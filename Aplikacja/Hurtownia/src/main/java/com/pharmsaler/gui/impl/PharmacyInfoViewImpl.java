package com.pharmsaler.gui.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.dao.PharmacyRepository;
import com.pharmsaler.gui.view.PharmacyInfoView;
import com.pharmsaler.model.Pharmacy;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

@SpringComponent
@UIScope
public class PharmacyInfoViewImpl extends PharmacyInfoView implements View {

	public static final String TAG = "pharmacyInfoView";

	private Button edit;

	private Button save;

	private Button cancel;

	private TextArea address;

	private TextField number;

	private TextField email;

	private Pharmacy pharmacy;

	private Label name;

	@Autowired
	private PharmacyRepository pharmacyRepo;

	public PharmacyInfoViewImpl(PharmacyRepository repo) {
		name = (Label) ((HorizontalLayout) getComponent(0)).getComponent(1);
		address = (TextArea) ((HorizontalLayout) getComponent(1)).getComponent(1);
		number = (TextField) ((HorizontalLayout) getComponent(2)).getComponent(1);
		email = (TextField) ((HorizontalLayout) getComponent(3)).getComponent(1);
		HorizontalLayout buttonLayout = (HorizontalLayout) getComponent(4);
		edit = (Button) buttonLayout.getComponent(0);
		save = (Button) buttonLayout.getComponent(1);
		cancel = (Button) buttonLayout.getComponent(2);

		edit.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				editModeOn(true);
			}
		});

		cancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				editModeOn(false);
			}
		});

		save.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				saveAction();
			}
		});
	}

	private void saveAction() {
		pharmacy.setAddress(address.getValue());
		pharmacy.setContactNumber(number.getValue());
		pharmacy.setEmail(email.getValue());
		pharmacyRepo.saveAndFlush(pharmacy);
		getSession().setAttribute(Pharmacy.class, pharmacy);
		editModeOn(false);

	}

	private void editModeOn(boolean isOn) {
		save.setVisible(isOn);
		cancel.setVisible(isOn);
		address.setEnabled(isOn);
		email.setEnabled(isOn);
		number.setEnabled(isOn);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		pharmacy = getSession().getAttribute(Pharmacy.class);
		name.setValue(pharmacy.getName());
		address.setValue(pharmacy.getAddress());
		number.setValue(pharmacy.getContactNumber());
		email.setValue(pharmacy.getEmail());
	}
}

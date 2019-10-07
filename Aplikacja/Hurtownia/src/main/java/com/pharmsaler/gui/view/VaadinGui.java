package com.pharmsaler.gui.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.gui.impl.WelcomePageImpl;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("citygames")
@StyleSheet("http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,700,700italic&subset=greek,latin")
public class VaadinGui extends UI {

	private final WelcomePageImpl welcomePage;

	@Autowired
	public VaadinGui(WelcomePageImpl welcomePage) {
		this.welcomePage = welcomePage;
	}

	@Override
	protected void init(VaadinRequest request) {
		VerticalLayout mainLayout = new VerticalLayout(welcomePage);
		setContent(mainLayout);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
	}

}

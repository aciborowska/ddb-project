package com.pharmsaler.gui.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.gui.view.MainView;
import com.vaadin.navigator.Navigator;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;

@SpringComponent
@UIScope
public class MainViewImpl extends MainView {

	private static final long serialVersionUID = 6855648098715707758L;

	private MenuViewImpl menuView;

	private HorizontalSplitPanel splitPanel;

	private Navigator navigator;

	@Autowired
	private PharmacyInfoViewImpl pharmacyInfoView;

	@Autowired
	private VersionListViewImpl medicineListView;

	@Autowired
	private SuppliersListImpl supplierListView;

	@Autowired
	private OrderListViewImpl orderListView;

	@Autowired
	private NewOrderViewImpl newOrderView;

	public MainViewImpl(PharmacyInfoViewImpl pharmacyInfoView) {
		splitPanel = (HorizontalSplitPanel) this.getComponent(0);
		menuView = (MenuViewImpl) splitPanel.getFirstComponent();
	}

	public void init() {
		navigator = new Navigator(UI.getCurrent(), (Panel) splitPanel.getSecondComponent());
		navigator.addView(PharmacyInfoViewImpl.TAG, pharmacyInfoView);
		navigator.addView(VersionListViewImpl.TAG, medicineListView);
		navigator.addView(SuppliersListImpl.TAG, supplierListView);
		navigator.addView(OrderListViewImpl.TAG, orderListView);
		navigator.addView(NewOrderViewImpl.TAG, newOrderView);
		navigator.navigateTo(VersionListViewImpl.TAG);
		menuView.init(navigator);
	}

}

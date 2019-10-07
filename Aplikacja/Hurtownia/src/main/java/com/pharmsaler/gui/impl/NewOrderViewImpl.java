package com.pharmsaler.gui.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.dao.OrderRepository;
import com.pharmsaler.dao.SupplierServiceRepository;
import com.pharmsaler.dao.VersionRepository;
import com.pharmsaler.gui.view.NewOrderView;
import com.pharmsaler.model.OrderPosition;
import com.pharmsaler.model.Pharmacy;
import com.pharmsaler.model.PurchaseOrder;
import com.pharmsaler.model.SupplierService;
import com.pharmsaler.model.Version;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class NewOrderViewImpl extends NewOrderView implements View {

	public static final String TAG = "NewOrderViewImpl";

	private Grid versionGrid;

	private Grid orderPositionGrid;

	@Autowired
	private VersionRepository versionRepo;

	@Autowired
	private SupplierServiceRepository supplierServiceRepo;

	@Autowired
	private OrderRepository orderRepo;

	private BeanItemContainer<OrderPosition> orderPositionContainer;

	private ComboBox paymentCombo;

	private ComboBox deliveryCombo;

	private TextArea additionalRequest;

	public NewOrderViewImpl() {
		setupMedicationGrid();
		setupOrderPositionGrid();
		HorizontalLayout payment = (HorizontalLayout) ((VerticalLayout) ((HorizontalLayout) getComponent(
				0)).getComponent(0)).getComponent(0);
		paymentCombo = (ComboBox) payment.getComponent(1);
		paymentCombo.addItems("przelew", "za pobraniem", "przy odbiorze");
		paymentCombo.setValue(paymentCombo.getItemIds().iterator().next());

		HorizontalLayout delivery = (HorizontalLayout) ((VerticalLayout) ((HorizontalLayout) getComponent(
				0)).getComponent(0)).getComponent(1);
		deliveryCombo = (ComboBox) delivery.getComponent(1);

		additionalRequest = (TextArea) ((VerticalLayout) ((HorizontalLayout) getComponent(0))
				.getComponent(0)).getComponent(2);
		additionalRequest.setCaption("Uwagi do zamówienia");

		HorizontalLayout buttonLayout = (HorizontalLayout) getComponent(2);
		Button saveOrder = (Button) buttonLayout.getComponent(0);
		saveOrder.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				saveOrder();

			}
		});
		Button cancelOrder = (Button) buttonLayout.getComponent(1);
		cancelOrder.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				cancelOrder();

			}
		});

	}

	private void cancelOrder() {
		orderPositionContainer.removeAllItems();
		paymentCombo.setValue(paymentCombo.getItemIds().iterator().next());
		deliveryCombo.setValue(deliveryCombo.getItemIds().iterator().next());
		additionalRequest.setValue("");
	}

	@Transactional
	private void saveOrder() {
		try {
			PurchaseOrder order = prepareOrder();
			updateVersionsAmounts(order);
			order = orderRepo.save(order);
			Notification.show(
					"Zamówienie nr " + order.getIdOrder() + " zostało przyjęte do realizacji");
		} catch (NotEnoughVersionAmount e) {
			Notification.show(
					"Aktualnie nie posiadamy wymaganej ilości produktu: "
							+ e.getVersion().toString()
							+ ". \nProszę wprowadzić mniejszą liczbę sztuk w zamówieniu.",
					Notification.Type.ERROR_MESSAGE);
		}
	}

	private void updateVersionsAmounts(PurchaseOrder order) throws NotEnoughVersionAmount {
		for (OrderPosition position : order.getOrderPositions()) {
			Version version = position.getVersion();
			if (version.getInStockAmount() >= position.getAmount()) {
				version.setInStockAmount(version.getInStockAmount() - (int) position.getAmount());
				versionRepo.save(version);
			} else {
				throw new NotEnoughVersionAmount(version);
			}
		}

	}

	private PurchaseOrder prepareOrder() {
		PurchaseOrder order = new PurchaseOrder();
		order.setOrderPositions(orderPositionContainer.getItemIds());
		for (OrderPosition position : order.getOrderPositions()) {
			position.setOrder(order);
		}
		order.setPayment((String) paymentCombo.getValue());
		order.setPharamcy(this.getSession().getAttribute(Pharmacy.class));
		order.setStartDate(System.currentTimeMillis());
		order.setStatus("przyjęte do realizacji");
		order.setSupplierService((SupplierService) deliveryCombo.getValue());
		return order;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		BeanItemContainer<Version> versionContainer = new BeanItemContainer<>(Version.class,
				versionRepo.findAll());
		versionContainer.addNestedContainerBean("medication");
		versionGrid.setContainerDataSource(versionContainer);
		createFilterRow(versionGrid);

		orderPositionContainer = new BeanItemContainer<>(OrderPosition.class);
		orderPositionContainer.addNestedContainerBean("version");
		orderPositionContainer.addNestedContainerBean("version.medication");
		orderPositionGrid.setContainerDataSource(orderPositionContainer);

		deliveryCombo.removeAllItems();
		for (SupplierService service : supplierServiceRepo.findAll()) {
			deliveryCombo.addItem(service);
		}
		deliveryCombo.setValue(deliveryCombo.getItemIds().iterator().next());

	}

	private void setupMedicationGrid() {
		versionGrid = (Grid) getComponent(1);
		versionGrid.addColumn("medication.name").setHeaderCaption("Nazwa");
		versionGrid.addColumn("medication.latinName").setHeaderCaption("Nazwa łac.");
		versionGrid.addColumn("type").setHeaderCaption("Typ");
		versionGrid.addColumn("unit").setHeaderCaption("Jednostka");
		versionGrid.addColumn("amount", Integer.class).setHeaderCaption("Ilość");
		versionGrid.addColumn("price", Float.class).setHeaderCaption("Cena za jedn. [zł]");
		versionGrid.addColumn("inStockAmount", Integer.class).setHeaderCaption("Dostępne sztuki");
		versionGrid.setEditorEnabled(false);
		versionGrid.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isDoubleClick()) {
					Version version = (Version) event.getItemId();
					OrderPosition position = new OrderPosition();
					position.setVersion(version);
					position.setAmount(1);
					position.setPrice(version.getPrice());
					orderPositionContainer.addBean(position);
				}

			}
		});
	}

	private void setupOrderPositionGrid() {
		orderPositionGrid = (Grid) ((HorizontalLayout) getComponent(0)).getComponent(1);
		orderPositionGrid.addColumn("version.idVersion").setHeaderCaption("Id wersji leku")
				.setEditable(false);
		orderPositionGrid.addColumn("amount", Integer.class).setHeaderCaption("Liczba")
				.setEditable(true);
		orderPositionGrid.addColumn("price", Float.class).setHeaderCaption("Kwota")
				.setEditable(false);
		orderPositionGrid.addColumn("version.medication.name").setHeaderCaption("Nazwa leku")
				.setEditable(false);
		orderPositionGrid.setEditorEnabled(true);
		orderPositionGrid.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				if (event.isAltKey()) {
					OrderPosition position = (OrderPosition) event.getItemId();
					orderPositionGrid.deselectAll();
					orderPositionContainer.removeItem(position);
					orderPositionGrid.getSelectionModel().reset();
				}

			}
		});

	}

	private void createFilterRow(Grid grid) {
		BeanItemContainer container = (BeanItemContainer) grid.getContainerDataSource();
		HeaderRow versionFilterRow;
		if (grid.getHeaderRowCount() == 1) {
			versionFilterRow = grid.appendHeaderRow();
		} else {
			versionFilterRow = grid.getHeaderRow(1);
		}
		for (Object pid : container.getContainerPropertyIds()) {
			HeaderCell cell = versionFilterRow.getCell(pid);
			if (cell != null) {
				TextField filterField = new TextField();
				filterField.setWidth("100%");
				filterField.setHeight("25px");
				filterField.setColumns(8);
				filterField.addTextChangeListener(change -> {
					container.removeContainerFilters(pid);
					if (!change.getText().isEmpty())
						container.addContainerFilter(
								new SimpleStringFilter(pid, change.getText(), true, false));
				});
				cell.setComponent(filterField);
			}
		}
	}

}

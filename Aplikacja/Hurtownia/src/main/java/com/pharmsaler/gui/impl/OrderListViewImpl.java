package com.pharmsaler.gui.impl;

import java.text.DateFormat;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.dao.OrderPositionRepository;
import com.pharmsaler.dao.OrderRepository;
import com.pharmsaler.gui.view.OrdersListView;
import com.pharmsaler.model.OrderPosition;
import com.pharmsaler.model.Pharmacy;
import com.pharmsaler.model.PurchaseOrder;
import com.pharmsaler.model.SupplierService;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.DateToLongConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.renderers.DateRenderer;

@SpringComponent
@UIScope
public class OrderListViewImpl extends OrdersListView implements View {

	private static final long serialVersionUID = 8673591780135218030L;

	public static final String TAG = "OrderListViewImpl";

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderPositionRepository orderPositionRepo;

	private Grid ordersGrid;

	private Label totalAmount;

	private Label versionDescription;

	private Grid orderPositions;

	private BeanItemContainer<OrderPosition> orderPositionContainer;

	public OrderListViewImpl() {
		ordersGrid = (Grid) getComponent(0);
		ordersGrid.addColumn("idOrder").setHeaderCaption("Id");
		ordersGrid.addColumn("status").setHeaderCaption("Status");
		ordersGrid.addColumn("startDate", Long.class).setHeaderCaption("Data zgłoszenia")
				.setRenderer(new DateRenderer(DateFormat.getDateInstance()),
						(new DateToLongConverter()));
		ordersGrid.addColumn("payment").setHeaderCaption("Sposób zapłaty");
		ordersGrid.addColumn("supplierService", SupplierService.class).setHeaderCaption("Dostawa");
		ordersGrid.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				PurchaseOrder order = (PurchaseOrder) event.getItemId();
				setOrderPositionsForGrid(order);
				float total = 0;
				for (OrderPosition position : order.getOrderPositions()) {
					total += position.getPrice();
				}
				totalAmount.setValue(String.format("%.2f zł", total));
			}
		});
		VerticalLayout orderDetails = (VerticalLayout) ((Panel) getComponent(1)).getContent();
		versionDescription = (Label) orderDetails.getComponent(3);
		totalAmount = (Label) ((HorizontalLayout) orderDetails.getComponent(4)).getComponent(1);
		orderPositions = (Grid) orderDetails.getComponent(1);
		orderPositions.setColumns("idOrderPosition", "amount", "price", "version.idVersion");
		orderPositions.getColumn("idOrderPosition").setHeaderCaption("Id");
		orderPositions.getColumn("amount").setHeaderCaption("Liczba");
		orderPositions.getColumn("price").setHeaderCaption("Kwota");
		orderPositions.getColumn("version.idVersion").setHeaderCaption("Id wersji leku");
		orderPositions.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				OrderPosition position = (OrderPosition) event.getItemId();
				versionDescription.setValue(position.getDescription());

			}
		});
	}

	protected void setOrderPositionsForGrid(PurchaseOrder order) {
		orderPositionContainer.removeAllItems();
		orderPositionContainer.addAll(orderPositionRepo.findByOrder(order));
	}

	@Override
	public void enter(ViewChangeEvent event) {
		BeanItemContainer<PurchaseOrder> orderContainer = new BeanItemContainer<>(
				PurchaseOrder.class,
				orderRepo.findByPharmacy(getSession().getAttribute(Pharmacy.class)));
		ordersGrid.setContainerDataSource(orderContainer);
		createFilterRow(ordersGrid);

		orderPositionContainer = new BeanItemContainer<>(OrderPosition.class);
		orderPositionContainer.addNestedContainerBean("version");
		orderPositions.setContainerDataSource(orderPositionContainer);
		createFilterRow(orderPositions);
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

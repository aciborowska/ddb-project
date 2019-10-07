package com.pharmsaler.gui.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.dao.SupplierServiceRepository;
import com.pharmsaler.gui.view.SuppliersList;
import com.pharmsaler.model.Supplier;
import com.pharmsaler.model.SupplierService;
import com.vaadin.data.util.BeanItemContainer;
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
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SpringComponent
@UIScope
public class SuppliersListImpl extends SuppliersList implements View {

	private static final long serialVersionUID = -6072704499205366312L;

	public static final String TAG = "suppliersListImpl";

	@Autowired
	private SupplierServiceRepository supplierServiceRepo;

	private Grid suppliersGrid;

	private Label name;

	private Label phone;

	private Label additionalInfo;

	public SuppliersListImpl() {
		suppliersGrid = (Grid) getComponent(0);
		suppliersGrid.addColumn("idSupplierService").setHeaderCaption("Id");
		suppliersGrid.addColumn("name").setHeaderCaption("Nazwa usługi");
		suppliersGrid.addColumn("price").setHeaderCaption("Cena [zł]");
		suppliersGrid.addColumn("restrictions").setHeaderCaption("Uwagi");
		suppliersGrid.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				SupplierService service = (SupplierService) event.getItemId();
				Supplier supplier = service.getSupplier();
				name.setValue(supplier.getName());
				phone.setValue("Tel.: " + supplier.getContactNumber());
				additionalInfo.setValue(supplier.getAdditionalInfo());

			}
		});

		VerticalLayout info = (VerticalLayout) ((Panel) getComponent(1)).getContent();
		name = (Label) info.getComponent(1);
		phone = (Label) info.getComponent(2);
		additionalInfo = (Label) info.getComponent(4);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		BeanItemContainer<SupplierService> supplierContainer = new BeanItemContainer<>(
				SupplierService.class, supplierServiceRepo.findAll());
		suppliersGrid.setContainerDataSource(supplierContainer);
		HeaderRow versionFilterRow;
		if (suppliersGrid.getHeaderRowCount() == 1) {
			versionFilterRow = suppliersGrid.appendHeaderRow();
		} else {
			versionFilterRow = suppliersGrid.getHeaderRow(1);
		}
		for (Object pid : supplierContainer.getContainerPropertyIds()) {
			HeaderCell cell = versionFilterRow.getCell(pid);
			if (cell != null) {
				TextField filterField = new TextField();
				filterField.setWidth("100%");
				filterField.setHeight("25px");
				filterField.setColumns(8);
				filterField.addTextChangeListener(change -> {
					supplierContainer.removeContainerFilters(pid);
					if (!change.getText().isEmpty())
						supplierContainer.addContainerFilter(
								new SimpleStringFilter(pid, change.getText(), true, false));
				});
				cell.setComponent(filterField);
			}
		}

	}

}

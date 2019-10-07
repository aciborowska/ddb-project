package com.pharmsaler.gui.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.pharmsaler.dao.MedicationRepository;
import com.pharmsaler.dao.VersionRepository;
import com.pharmsaler.gui.view.VersionListView;
import com.pharmsaler.model.Medication;
import com.pharmsaler.model.Version;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.TextField;

@SpringComponent
@UIScope
public class VersionListViewImpl extends VersionListView implements View {

	private static final long serialVersionUID = 4236387166820525909L;

	public static final String TAG = "medicineListViewImpl";

	@Autowired
	private VersionRepository versionRepo;

	@Autowired
	private MedicationRepository medicationRepository;

	private Grid versionGrid;

	private Grid medicationGrid;

	private BeanItemContainer<Version> versionContainer;

	public VersionListViewImpl() {
		prepareVersionGrid();
		prepareMedicationGrid();
	}

	private void prepareVersionGrid() {
		versionGrid = (Grid) getComponent(1);
		versionGrid.addColumn("idVersion").setHeaderCaption("Id");
		versionGrid.addColumn("type").setHeaderCaption("Typ");
		versionGrid.addColumn("unit").setHeaderCaption("Jednostka");
		versionGrid.addColumn("amount", Integer.class).setHeaderCaption("Ilość");
		versionGrid.addColumn("price", Float.class).setHeaderCaption("Cena za jedn. [zł]");
		versionGrid.addColumn("inStockAmount", Integer.class).setHeaderCaption("Dostępne sztuki");
	}

	private void prepareMedicationGrid() {
		medicationGrid = (Grid) getComponent(0);
		medicationGrid.setColumns("idMedication", "name", "latinName");
		medicationGrid.getColumn("idMedication").setHeaderCaption("Id");
		medicationGrid.getColumn("name").setHeaderCaption("Nazwa");
		medicationGrid.getColumn("latinName").setHeaderCaption("Nazwa łacińska");
		medicationGrid.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				Medication med = (Medication) medicationGrid.getSelectedRow();
				setVersionGridForMedication(med);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		BeanItemContainer<Medication> medicationContainer = new BeanItemContainer<>(
				Medication.class, medicationRepository.findAll());
		medicationGrid.setContainerDataSource(medicationContainer);
		createFilterRow(medicationGrid);

		versionContainer = new BeanItemContainer<>(Version.class);
		versionGrid.setContainerDataSource(versionContainer);
		createFilterRow(versionGrid);
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

	private void setVersionGridForMedication(Medication medication) {
		versionContainer.removeAllItems();
		versionContainer.addAll(versionRepo.findByMedication(medication));
	}
}

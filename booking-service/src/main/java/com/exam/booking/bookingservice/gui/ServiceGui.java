package com.exam.booking.bookingservice.gui;


import com.exam.booking.bookingservice.entity_model.Customer;
import com.exam.booking.bookingservice.entity_model.Reservation;
import com.exam.booking.bookingservice.entity_model.Room;
import com.exam.booking.bookingservice.normal_model.ReservedTO;
import com.exam.booking.bookingservice.repository.CustomerRepository;
import com.exam.booking.bookingservice.repository.ReservationRepository;
import com.exam.booking.bookingservice.repository.RoomRepository;
import com.exam.booking.bookingservice.service.TableObjectService;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringUI(path = "/")
public class ServiceGui extends UI {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TableObjectService tableObjectService;

    private VerticalLayout root = new VerticalLayout();

    private ComboBox<Room> roomComboBox = new ComboBox<>();
    private TextField customerName_Textfield = new TextField();
    private TextField customerCapacity_Textfield = new TextField();
    private DateField from_DataFiled = new DateField();
    private DateField to_DataFiled = new DateField();
    private Grid<ReservedTO> bookingTableGrid = new Grid<>();
    private Button submit_Button = new Button();

    private List<ReservedTO> listTO = new ArrayList<>();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initComboBoxes();
        initTextFields();
        initDataFields();
        initButtons();
        initGrids();

        root.addComponents( customerName_Textfield, customerCapacity_Textfield, roomComboBox, from_DataFiled, to_DataFiled, submit_Button, bookingTableGrid);
        setContent(root);

    }

    private void initComboBoxes() {
        roomComboBox.setCaption("Rooms");
        roomComboBox.setItems(roomRepository.findAll());
        roomComboBox.setTextInputAllowed(false);
        roomComboBox.setItemCaptionGenerator(Room::getNumber);
        roomComboBox.setPlaceholder("Select room");
        roomComboBox.setEmptySelectionAllowed(false);
    }

    private void initTextFields() {
        customerName_Textfield.setCaption("New Customer");
        customerName_Textfield.setPlaceholder("New customer's name");

        customerCapacity_Textfield.setCaption("Customer amount");
        customerCapacity_Textfield.setPlaceholder("Customer capacity.");

    }

    private void initDataFields() {
        from_DataFiled.setCaption("From Date");
        from_DataFiled.setValue(LocalDate.now());
        from_DataFiled.setTextFieldEnabled(false);

        to_DataFiled.setCaption("To Date");
        to_DataFiled.setValue(LocalDate.now());
        to_DataFiled.setTextFieldEnabled(false);
    }

    private void initButtons() {
        submit_Button.setCaption("Submit");
        submit_Button.addClickListener(clickEvent -> {
            LocalDate fromDate = from_DataFiled.getValue();
            LocalDate toDate = to_DataFiled.getValue();
            if (toDate.isBefore(fromDate)) {
                Notification.show("Date", "Dates are wrong", Notification.Type.WARNING_MESSAGE);
            } else if (roomComboBox.isEmpty()) {
                Notification.show("Room", "Room is empty", Notification.Type.WARNING_MESSAGE);
            } else {
                Customer customer = customerRepository.save(new Customer(0L, customerName_Textfield.getValue()));
                customerName_Textfield.setValue("");

                Room room = roomComboBox.getValue();

                reservationRepository.save(new Reservation(0L, from_DataFiled.getValue(), to_DataFiled.getDefaultValue(), customer, room));
                Notification.show("Added", Notification.Type.HUMANIZED_MESSAGE);
                updateGridData();
            }
        });
    }

    private void initGrids() {
        bookingTableGrid.addColumn(ReservedTO::getId).setCaption("Booking Id");
        bookingTableGrid.addColumn(ReservedTO::getCustomer).setCaption("Full Name");
        bookingTableGrid.addColumn(ReservedTO::getRoom).setCaption("Room");
        bookingTableGrid.addColumn(ReservedTO::getFrom).setCaption("From Date");
        bookingTableGrid.addColumn(ReservedTO::getTo).setCaption("To Date");
        bookingTableGrid.setDataProvider(setDataProvider());

        bookingTableGrid.setWidth("1200");
        bookingTableGrid.setHeightUndefined();
    }

    private ListDataProvider<ReservedTO> setDataProvider() {
        setTableObjectList();
        return DataProvider.ofCollection(listTO);
    }

    private void updateGridData() {
        bookingTableGrid.setDataProvider(setDataProvider());
    }

    private void setTableObjectList() {
        listTO.clear();
        listTO = tableObjectService.makeTO();
    }

}

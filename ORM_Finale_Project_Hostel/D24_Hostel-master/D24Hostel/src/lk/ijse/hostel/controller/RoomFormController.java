package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.service.ServiceFactory;
import lk.ijse.hostel.service.ServiceTypes;
import lk.ijse.hostel.service.custome.RoomService;
import lk.ijse.hostel.service.exception.DuplicateException;
import lk.ijse.hostel.service.exception.NotFoundException;
import lk.ijse.hostel.tm.RoomTm;
import lk.ijse.hostel.util.FactoryConfiguration;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoomFormController {
    public AnchorPane pane;
    public JFXTextField txtId;
    public JFXTextField txtType;
    public JFXTextField txtxKeyMoney;
    public JFXTextField txtQty;
    public TableView tblRooms;
    public TableColumn colId;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    private Pattern idPattern;
    private Pattern typePattern;
    private Pattern keyMoneyPattern;
    private Pattern qrtPattern;
    public RoomService roomService;
    private final ObservableList<RoomDTO>dtos=FXCollections.observableArrayList();
    private ObservableList<RoomTm> roomTms= FXCollections.observableArrayList();
    public void initialize() throws SQLException, ClassNotFoundException {
        this.roomService= (RoomService) ServiceFactory.getInstance().getService(ServiceTypes.ROOM);
        RoomtView();
        loadRooms();
        pattern();


    }
    public void pattern(){
        idPattern=Pattern.compile("[R][0-9]{1,}");
        typePattern=Pattern.compile("^[A-Za-z]{1,}$");
        keyMoneyPattern=Pattern.compile("[0-9]{1,}");
        qrtPattern=Pattern.compile("[0-9]{1,}");
    }
    private void RoomtView(){
        colId.setCellValueFactory(new PropertyValueFactory<>("room_type_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
       //loadRooms();
    }

    public void txtIDOnAction(ActionEvent actionEvent) {
        RoomDTO roomDTO=roomService.searchRoom(txtId.getText());
        if (roomDTO!=null){
            fillData(roomDTO);
        }
    }
    public void txtTypeOnAction(ActionEvent actionEvent) {
        /*RoomDTO roomDTO=roomService.searchRoom(txtType.getText());
        if (roomDTO!=null){
            fillData(roomDTO);
        }*/
    }


    private void fillData(RoomDTO roomDTO){
        txtId.setText(roomDTO.getRoom_type_id());
        txtType.setText(roomDTO.getType());
        txtxKeyMoney.setText(roomDTO.getKey_money());
        txtQty.setText(String.valueOf(roomDTO.getQty()));
    }

    public void btnAddOnAction(ActionEvent actionEvent) {
        boolean isIdMatched=idPattern.matcher(txtId.getText()).matches();
        boolean isTypeMatched=idPattern.matcher(txtType.getText()).matches();
        boolean isKeyMatched=idPattern.matcher(txtxKeyMoney.getText()).matches();
        boolean isMatched=idPattern.matcher(txtId.getText()).matches();

        if (isIdMatched){
            if (isTypeMatched){
                if (isKeyMatched){
                    if (isMatched){
                        System.out.println("start");
                    }else{
                        txtQty.setFocusColor(Paint.valueOf("Red"));
                        txtQty.requestFocus();
                    }
                }else {
                    txtxKeyMoney.setFocusColor(Paint.valueOf("Red"));
                    txtxKeyMoney.requestFocus();
                }
            }else {
                txtType.setFocusColor(Paint.valueOf("Red"));
                txtType.requestFocus();
            }
        }else {
            txtId.setFocusColor(Paint.valueOf("Red"));
            txtId.requestFocus();
        }

        RoomDTO roomDTO=new RoomDTO(txtId.getText(),txtType.getText(),txtxKeyMoney.getText(),Integer.parseInt(txtQty.getText()));
        try {
            boolean isAdded=roomService.saveRoom(roomDTO);
            if (isAdded){
                tblRooms.getItems().add(new RoomTm(roomDTO.getRoom_type_id(),roomDTO.getType(),roomDTO.getKey_money(), roomDTO.getQty()));
                txtId.clear();
                txtType.clear();
                txtxKeyMoney.clear();
                txtQty.clear();
            }else {
                new Alert(Alert.AlertType.ERROR,"No").show();
            }

        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,"Room Already Added").show();
            txtId.selectAll();
            txtId.requestFocus();
        }
    }

    public void btnDeleteonAction(ActionEvent actionEvent) {
        Alert alert=new Alert(Alert.AlertType.WARNING,"are you sure to delete the employe", ButtonType.YES,ButtonType.NO);
        Optional<ButtonType> result=alert.showAndWait();
        if(result.isPresent()&& result.get()==ButtonType.YES){
            try {
                roomService.deleteRoom(txtId.getText());
                new Alert(Alert.AlertType.INFORMATION,"Deleted").show();
                tblRooms.getItems().removeAll(tblRooms.getSelectionModel().getSelectedItem());
                txtId.clear();
                txtType.clear();
                txtxKeyMoney.clear();
                txtQty.clear();
                loadRooms();
            }catch (NotFoundException e){
                new Alert(Alert.AlertType.WARNING,"No").show();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void btnUpdateonAction(ActionEvent actionEvent) {
        RoomDTO roomDTO=new RoomDTO(txtId.getText(),txtType.getText(),txtxKeyMoney.getText(),Integer.parseInt(txtQty.getText()));
        int select;
        try {
            roomService.updateRoom(roomDTO);
            select=tblRooms.getSelectionModel().getSelectedIndex();
            tblRooms.getItems().remove(select+1);
            new Alert(Alert.AlertType.INFORMATION,"Update").show();
            txtId.clear();
            txtType.clear();
            txtxKeyMoney.clear();
            txtQty.clear();
            loadRooms();
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage());
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        txtIDOnAction(actionEvent);
    }
    private void loadRooms(){
       roomTms.clear();
        roomTms.addAll(
                roomService.getAllRoom().stream().map(roomDTO -> new RoomTm(
                        roomDTO.getRoom_type_id(),roomDTO.getType(),roomDTO.getKey_money(), roomDTO.getQty()
                )).collect(Collectors.toList()));
        tblRooms.setItems(roomTms);
    }


}

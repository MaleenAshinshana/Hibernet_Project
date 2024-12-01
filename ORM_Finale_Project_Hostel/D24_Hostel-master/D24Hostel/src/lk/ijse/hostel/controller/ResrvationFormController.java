package lk.ijse.hostel.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.hostel.dto.ReservationDTO;
import lk.ijse.hostel.dto.RoomDTO;
import lk.ijse.hostel.dto.StudentDTO;
import lk.ijse.hostel.service.ServiceFactory;
import lk.ijse.hostel.service.ServiceTypes;
import lk.ijse.hostel.service.custome.ReservationService;
import lk.ijse.hostel.service.custome.RoomService;
import lk.ijse.hostel.service.custome.StudentService;
import lk.ijse.hostel.service.exception.DuplicateException;
import lk.ijse.hostel.service.exception.NotFoundException;
import lk.ijse.hostel.tm.ReservationTm;
import lk.ijse.hostel.tm.StudentTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ResrvationFormController {
    public JFXComboBox cmbStatus;
    @FXML
    private JFXTextField txtId;

    @FXML
    private JFXTextField txtdate;

    @FXML
    private JFXComboBox cmbStudentId;

    @FXML
    private JFXComboBox   cmbRoomId;

    @FXML
    private JFXTextField txtStudent;

    @FXML
    private TableView<ReservationTm> tblReservation;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> coldate;

    @FXML
    private TableColumn<?, ?> colStudenrId;

    @FXML
    private TableColumn<?, ?> colRoomId;

    @FXML
    private TableColumn<?, ?> colStatus;
    public ReservationService reservationService;
    public RoomService roomService;
    public StudentService studentService;
    private ObservableList<ReservationTm>list=FXCollections.observableArrayList();

    public void initialize() throws SQLException, ClassNotFoundException {
        this.reservationService= (ReservationService) ServiceFactory.getInstance().getService(ServiceTypes.RESEVATION);
        this.roomService= (RoomService) ServiceFactory.getInstance().getService(ServiceTypes.ROOM);
        this.studentService= (StudentService) ServiceFactory.getInstance().getService(ServiceTypes.STUDENT);
        loadAllReservation();
        reservationView();
        ObservableList<String> list=FXCollections.observableArrayList("Paid","Non-Paid");
        cmbStatus.setItems(list);
        LoadStudentIds();
        LoadRoomTypeId();
    }
    private void loadAllReservation(){
        list.clear();
        list.addAll(
                reservationService.getAll().stream().map(reservationDTO ->
                        new ReservationTm(reservationDTO.getId(), reservationDTO.getDate(), reservationDTO.getStatus(),
                                reservationDTO.getStudent(), reservationDTO.getRoom())).collect(Collectors.toList()));
        tblReservation.setItems(list);
        for (ReservationDTO reservationDTO : reservationService.getAll()) {
            System.out.println(reservationDTO);
        }

    }
    public void txtIdOnActionm(ActionEvent actionEvent) {
        ReservationDTO reservationDTO=reservationService.searchReservation(txtId.getText());
        if (reservationDTO!=null){
            fillData(reservationDTO);
        }

    }
    private void fillData(ReservationDTO reservationDTO){
        txtId.setText(reservationDTO.getId());
        txtdate.setText(reservationDTO.getDate());
        cmbStatus.getSelectionModel().select(reservationDTO.getStatus());
        cmbStudentId.getSelectionModel().select(reservationDTO.getStudent());
        cmbRoomId.getSelectionModel().select(reservationDTO.getRoom());
        //cmbStatus.setItems(reservationDTO.getStatus());
        /*txtName.setText(studentDTO.getStudentName());
        txtAddress.setText(studentDTO.getAddress());
        txtxTel.setText(String.valueOf(studentDTO.getContact_number()));
        txtDOB.setText(studentDTO.getDate_of_birth());
        txtGender.setText(studentDTO.getGender());*/
    }

    public void btnRegistaion(ActionEvent actionEvent) {
        String studentId=cmbStudentId.getSelectionModel().getSelectedItem().toString();
        String roomId=cmbRoomId.getSelectionModel().getSelectedItem().toString();
        String status=cmbStatus.getSelectionModel().getSelectedItem().toString();

        ReservationDTO  reservationDTO=new ReservationDTO(txtId.getText(),txtdate.getText(),status,studentId,roomId);
        System.out.println(roomId);
        try {
            boolean isAdded=reservationService.saveReservatoin(reservationDTO);
            if (isAdded) {
                new Alert(Alert.AlertType.CONFIRMATION,"Yes").show();
                tblReservation.getItems().add(new ReservationTm(reservationDTO.getId(),reservationDTO.getDate(),
                        reservationDTO.getStatus(),reservationDTO.getStudent(),reservationDTO.getRoom()));
                txtId.clear();
                txtdate.clear();
            }else{
                new Alert(Alert.AlertType.ERROR,"No").show();
            }
        }catch (DuplicateException e){
            new Alert(Alert.AlertType.ERROR,"NOOOO").show();
        }
    }
    private void reservationView(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        coldate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colStudenrId.setCellValueFactory(new PropertyValueFactory<>("student"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("room"));

    }

    public void btnUpdateOnACtion(ActionEvent actionEvent) {
        ReservationDTO reservationDTO=reservationService.searchReservation(txtId.getText());
        reservationDTO.setStatus((String) cmbStatus.getSelectionModel().getSelectedItem());
        System.out.println(cmbStatus.getSelectionModel().getSelectedItem());
        System.out.println(reservationDTO+"**********");
        try {
            reservationService.updateReservation(reservationDTO);

            new Alert(Alert.AlertType.INFORMATION,"Update").show();
            loadAllReservation();
        }catch (NotFoundException e){
            new Alert(Alert.AlertType.ERROR,"No").show();
        }





    }
    private void LoadStudentIds(){
        try {
            ObservableList<String>observableList= FXCollections.observableArrayList();
            ArrayList<String> idList=reservationService.loadAllStudentIds();

            for (String id: idList) {
                observableList.add(id);
            }
            cmbStudentId.setItems(observableList);
        }catch (SQLException |ClassNotFoundException e){
            throw new RuntimeException();
        }
    }
    private void LoadRoomTypeId(){
        try {
            ObservableList<String>observableList= FXCollections.observableArrayList();
            ArrayList<String> idList=reservationService.loadRoomTypeID();

            for (String id: idList) {
                observableList.add(id);
            }
            cmbRoomId.setItems(observableList);
        }catch (SQLException |ClassNotFoundException e){
            throw new RuntimeException();
        }
    }
}




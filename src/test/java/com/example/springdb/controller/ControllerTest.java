package com.example.springdb.controller;

import com.example.springdb.error.AccountNumberNotValidException;
import com.example.springdb.error.BeneficiaryNoContentException;
import com.example.springdb.error.BeneficiaryNotFoundException;
import com.example.springdb.model.Beneficiary;
import com.example.springdb.service.BeneficiaryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ControllerTest {


    @Mock
    private BeneficiaryService service;

    @InjectMocks
    private BeneficiaryController controller;

    @Test
    @DisplayName("Get all data")
    public void test_getAllBeneficiary() {
        when(service.findAll()).thenReturn(Stream.of(new Beneficiary(1, "Rohini", "23456", "HDFC", "HDB012"), new Beneficiary(2, "Vijay", "23456", "ICICI", "ICIC21"), new Beneficiary(3, "Rohi", "4567", "HDFC", "HDB23")).collect(Collectors.toList()));
        assertEquals(3,controller.getAllBeneficiary().size());
    }

    @Test
    @DisplayName("Test DeleteBeneficiary() by passing List")
    public void test_DeleteBeneficiary() {

        String successMessage = "All Beneficiary Records Deleted Successfully";
        List<Beneficiary> beneficiary=null;
        when(service.findAll()).thenReturn(Stream.of(new Beneficiary(1, "Rohini", "23456", "HDFC", "HDB012"), new Beneficiary(2, "Vijay", "23456", "ICICI", "ICIC21"), new Beneficiary(3, "Rohi", "4567", "HDFC", "HDB23")).collect(Collectors.toList()));
        when(service.deleteAll()).thenReturn(successMessage);
        ResponseEntity<String> result = controller.deleteBeneficiary();
        assertEquals(successMessage, result.getBody());
    }

    @Test
    @DisplayName("Test DeleteBeneficiary() by passing Empty List")
    public void test_DeleteBeneficiary_passEmptyList() {
        List<Beneficiary> beneficiary= Collections.emptyList();
        when(service.findAll()).thenReturn(beneficiary);
        try {
            controller.deleteBeneficiary();
        } catch (BeneficiaryNoContentException e) {
            assertEquals("Beneficiary List is empty", e.getMessage());
        }
    }

    @Test
    @DisplayName("Delete Beneficiary by ID which is exist")
    public void test_deleteBeneficiaryByID_WhenIdExist(){
        int id = 2;
        when(service.checkBeneficiaryExistById(id)).thenReturn(true);
        ResponseEntity<String> result=controller.deleteBeneficiaryById(id);
        assertEquals("Beneficiary id = " + id + " is successfully deleted",result.getBody());
    }

    @Test
    @DisplayName("Delete Beneficiary by ID which is not exist")
    public void test_deleteBeneficiaryByID_WhenNotExist(){
        int id = 4;
        String ExpectedOutput="Beneficiary id = " + id + " is not exist";
        when(service.checkBeneficiaryExistById(id)).thenReturn(false);
        try {
            controller.deleteBeneficiaryById(id);;
        } catch (BeneficiaryNotFoundException e) {
            assertEquals(ExpectedOutput, e.getMessage());
        }

    }

    @Test
    @DisplayName("Add Beneficiary with Valid AccNo")
    public void test_addValidBeneficiary_WithValidAccountNumber() {
        Beneficiary beneficiary = new Beneficiary(1, "Rofi", "1234567890", "HDFC", "HDB123");
        when(service.isValidBankAccNumber(any())).thenReturn(true);
        when(service.save(beneficiary)).thenReturn(beneficiary);
        ResponseEntity<Beneficiary> result = controller.addBeneficiary(beneficiary);
        assertEquals(beneficiary, result.getBody());

    }
    @Test
    @DisplayName("unable to Add Beneficiary with Invalid AccNo")
    public void test_addValidBeneficiary_WithInvalidAccountNumber(){
        Beneficiary beneficiary = new Beneficiary(1, "Rofi", "123456789", "HDFC", "HDB123");
        when(service.isValidBankAccNumber(any())).thenReturn(false);
        String expectedOutput="Entered Account number is not valid";
        try {
            controller.addBeneficiary(beneficiary);
        } catch (AccountNumberNotValidException e) {
            assertEquals(expectedOutput, e.getMessage());
        }

    }

    @Test
    @DisplayName("Get Beneficiary By ID when ID Exist")
    public void test_GetBeneficiaryById_WhenIDExist(){
        Optional<Beneficiary> beneficiary = Optional.of(new Beneficiary(1, "Rofi", "123456789", "HDFC", "HDB123"));
        when(service.findBeneficiaryById(1)).thenReturn(beneficiary);
        ResponseEntity<?> beneficiaryById = controller.getBeneficiaryById(1);
        assertEquals(beneficiary.get(), beneficiaryById.getBody());
        assertEquals(HttpStatus.FOUND,beneficiaryById.getStatusCode());

    }

    @Test
    @DisplayName("Test Get Beneficiary By ID When ID not Exist")
    public void test_GetBeneficiaryById_WhenIDNotExist(){
        var id =1;
        Optional<Beneficiary> beneficiary = Optional.of(new Beneficiary());
        when(service.findBeneficiaryById(id)).thenReturn(Optional.empty());

        try {
            controller.getBeneficiaryById(id);
        } catch (BeneficiaryNotFoundException e) {
            assertEquals("Beneficiary id = " + id + " is not exist", e.getMessage());
        }

    }
}

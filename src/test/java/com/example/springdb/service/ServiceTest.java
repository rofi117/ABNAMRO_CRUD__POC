package com.example.springdb.service;

import com.example.springdb.model.Beneficiary;
import com.example.springdb.repo.BeneficiaryRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ServiceTest {

	@Autowired
	private BeneficiaryService service;

	@MockBean
	private BeneficiaryRepo repo;

	@Test
	public void test_findAll() {
		when(repo.findAll()).thenReturn(Stream.of(new Beneficiary(1, "Rohini", "23456", "HDFC", "HDB012"), new Beneficiary(2, "Vijay", "23456", "ICICI", "ICIC21"), new Beneficiary(3, "Rohi", "4567", "HDFC", "HDB23")).collect(Collectors.toList()));
		int size = service.findAll().size();
		System.out.print("size: " + service.findAll().size());
		assertEquals(3, service.findAll().size(),"Both the actual and expected value are 3");
	}

	@Test
	public void test_save() {
		Beneficiary beneficiary = new Beneficiary(1, "Rofi", "123456789", "HDFC", "HDB123");
		when(repo.save(beneficiary)).thenReturn(beneficiary);
		assertEquals(beneficiary, service.save(beneficiary),"Validating the saved beneficiary");
	}

	@Test
	public void test_findBeneficiaryById() {
		Integer id = 1;
		Optional<Beneficiary> beneficiary = Optional.of(new Beneficiary(1, "Rofi", "1234567891", "HDFC", "HDB123"));
		when(repo.findById(id)).thenReturn(beneficiary);
		Optional<Beneficiary> beneficiaryById = service.findBeneficiaryById(id);
		assertEquals(id, beneficiaryById.get().getId(),"Both the Actual and expected id values are 1" );
        assertEquals("Rofi",beneficiaryById.get().getBeneficiaryName());
	}


	@ParameterizedTest
	@DisplayName("Bank Account Number = 10 digit number")
	@MethodSource()
	public void  isValidBankAccNumberTest(String bankAccNo ){

		assertTrue(service.isValidBankAccNumber(bankAccNo));

	}
	public static Stream<Arguments> isValidBankAccNumberTest(){
		String input1 = "1234567891";
		String input2 = "2345671123";
		String input3 = "4567819234";
		return Stream.of(
				Arguments.of(input1),
				Arguments.of(input2),
				Arguments.of(input3)

		);

	}

	@ParameterizedTest
	@DisplayName("Bank Account Number != 10 digit number")
	@MethodSource()
	public void  isNotValidBankAccNumberTest(String bankAccNo ){

		assertFalse(service.isValidBankAccNumber(bankAccNo));
	}
	public static Stream<Arguments> isNotValidBankAccNumberTest(){
		String input1 = "56789";
		String input2 = "@3er567890";
		String input3 = "2345F6@vhj";
		return Stream.of(
				Arguments.of(input1),
				Arguments.of(input2),
				Arguments.of(input3)

		);

	}

	@Test
	@DisplayName("Test ")
	public void test_checkBeneficiaryExistById_WhenIDExist(){
		Integer id = 1;
		Beneficiary beneficiary = new Beneficiary(1, "Rofi", "123456789", "HDFC", "HDB123");
		when(repo.existsById(id)).thenReturn(true);
		assertTrue(service.checkBeneficiaryExistById(id));
		verify(repo).existsById(id);
	}

	@Test
	@DisplayName("Test  ")
	public void test_checkBeneficiaryExistById_WhenIDNotExist(){
		int id=1;
		when(repo.existsById(id)).thenReturn(false);
		assertFalse(service.checkBeneficiaryExistById(id));
		verify(repo).existsById(id);
	}

	@Test
	public void updateBeneficiaryByIdTest(){




	}
	@Test
	public void DeleteBeneficiaryByIDTest() {
		Integer beneficiaryIdToDelete = 1;
		boolean isDeleted = service.deleteBeneficiaryByID(beneficiaryIdToDelete);
		verify(repo).deleteById(beneficiaryIdToDelete);
		assert isDeleted;

	}

	@Test
	public void DeleteAllTest() {
		String result = service.deleteAll();
		verify(repo).deleteAll();
		assert result.equals("All Beneficiary Records Deleted Successfully");
	}



}

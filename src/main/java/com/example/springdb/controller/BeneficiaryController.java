package com.example.springdb.controller;

import com.example.springdb.error.AccountNumberNotValidException;
import com.example.springdb.error.BeneficiaryNoContentException;
import com.example.springdb.error.BeneficiaryNotFoundException;
import com.example.springdb.model.Beneficiary;
import com.example.springdb.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;



@RestController
@CrossOrigin(origins="*")
@RequestMapping("/api")
public class BeneficiaryController {

    @Autowired
    BeneficiaryService service;


    @PostMapping("/addbeneficiary")
    public ResponseEntity<Beneficiary> addBeneficiary(@RequestBody Beneficiary beneficiary) {
        boolean isValidBankAccNumber = service.isValidBankAccNumber(beneficiary.getBeneficiaryAccNo());
         if (!isValidBankAccNumber)
           throw new AccountNumberNotValidException("Entered Account number is not valid");
        else {
            Beneficiary newbeneficiary = service.save(beneficiary);
            return new ResponseEntity<>(newbeneficiary, HttpStatus.CREATED);
        }
    }

    @GetMapping("/getAllbeneficiary")
    public List<Beneficiary> getAllBeneficiary() {
        return service.findAll();
    }

    @DeleteMapping("/deleteallBeneficiary")
    public ResponseEntity<String> deleteBeneficiary() {
        List<Beneficiary> beneficiaryList=service.findAll();
        if(beneficiaryList.isEmpty()) {
             throw new BeneficiaryNoContentException("Beneficiary List is empty");
        }
        else {
            String result = service.deleteAll();
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping("/delete-beneficiary-By-id/{id}")
    public ResponseEntity<String> deleteBeneficiaryById(@PathVariable("id") Integer id){
        if(!service.checkBeneficiaryExistById(id)){
               throw new BeneficiaryNotFoundException("Beneficiary id = " + id + " is not exist");
            }
        else{
            String result = service.deleteBeneficiaryByID(id);
              return new ResponseEntity<>(result, HttpStatus.OK);
          }

    }

    @PutMapping("/update-beneficiary/{id}")
    public ResponseEntity<Beneficiary> updateBeneficiary(@RequestBody Beneficiary beneficiary, @PathVariable("id") Integer id) {
        Optional<Beneficiary> updatedbeneficiary = service.updateBeneficiaryById(beneficiary, id);
       if(updatedbeneficiary.isPresent())
           return ResponseEntity.ok(updatedbeneficiary.get());
       else
           throw new BeneficiaryNotFoundException("Beneficiary id = " + id + " is not exist");
    }

    @GetMapping("/get-beneficiary-By-id/{id}")
    public ResponseEntity<Beneficiary> getBeneficiaryById(@PathVariable("id") Integer id) {
     Optional<Beneficiary> beneficiaryById = service.findBeneficiaryById(id);

     if(beneficiaryById.isEmpty())
         throw new BeneficiaryNotFoundException("Beneficiary id = " + id + " is not exist");

     return new ResponseEntity<>(beneficiaryById.get(), HttpStatus.FOUND);

    }

}

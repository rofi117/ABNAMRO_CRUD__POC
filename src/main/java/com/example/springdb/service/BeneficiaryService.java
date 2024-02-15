package com.example.springdb.service;

import com.example.springdb.model.Beneficiary;
import com.example.springdb.repo.BeneficiaryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BeneficiaryService {

    @Autowired
    BeneficiaryRepo repo;

    public Beneficiary save(Beneficiary beneficiary) {
        return repo.save(beneficiary);
    }

    public List<Beneficiary> findAll() {
        List<Beneficiary> data = repo.findAll();
        System.out.println("Beneficiary:" + data);
        return repo.findAll();
    }

    public String deleteAll() {
        repo.deleteAll();
        return "All Beneficiary Records Deleted Successfully";
    }

    public String deleteBeneficiaryByID(Integer id) {
        repo.deleteById(id);
        return "Beneficiary id = " + id + " is successfully deleted";
    }

    public boolean checkBeneficiaryExistById(Integer id) {
        return repo.existsById(id);
    }

    public Optional<Beneficiary> updateBeneficiaryById(Beneficiary updatedBeneficiary, Integer id) {

        Optional<Beneficiary> beneficiary = repo.findById(id);
        if (beneficiary.isPresent()) {
            Beneficiary existingBeneficiary = beneficiary.get();
            existingBeneficiary.setBeneficiaryName(updatedBeneficiary.getBeneficiaryName());
            existingBeneficiary.setBeneficiaryAccNo(updatedBeneficiary.getBeneficiaryAccNo());
            existingBeneficiary.setBeneficiaryBank(updatedBeneficiary.getBeneficiaryBank());
            existingBeneficiary.setBeneficiaryIFSC(updatedBeneficiary.getBeneficiaryIFSC());

            return Optional.of(repo.save(existingBeneficiary));
        } else {
            return Optional.empty();
        }


}
    public boolean isValidBankAccNumber(String bankAccNo)
    {
        String regex ="^\\d{10}$";
        Pattern p= Pattern.compile(regex);
        if(bankAccNo == null){
            return false;
        }
        Matcher m=p.matcher(bankAccNo);
        return m.matches();
    }

    public Optional<Beneficiary> findBeneficiaryById(Integer id) {

        return repo.findById(id);

    }
}


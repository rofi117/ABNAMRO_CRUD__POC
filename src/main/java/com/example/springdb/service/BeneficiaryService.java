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

    public boolean deleteBeneficiaryByID(Integer id) {
        repo.deleteById(id);
        return true;
    }

    public boolean checkBeneficiaryExistById(Integer id) {
        if (repo.existsById(id))
            return true;
        else
            return false;
    }

    public Optional<Beneficiary> updateBeneficiaryById(Beneficiary beneficiary, Integer id) {

        Optional<Beneficiary> optionalBeneficiary = repo.findById(id);
        if (optionalBeneficiary.isPresent()) {
            Beneficiary existingBeneficiary = optionalBeneficiary.get();
            existingBeneficiary.setBeneficiaryName(beneficiary.getBeneficiaryName());
            existingBeneficiary.setBeneficiaryAccNo(beneficiary.getBeneficiaryAccNo());
            existingBeneficiary.setBeneficiaryBank(beneficiary.getBeneficiaryBank());
            existingBeneficiary.setBeneficiaryIFSC(beneficiary.getBeneficiaryIFSC());

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
        if(m.matches())
            return true;
        return false;
    }

    public Optional<Beneficiary> findBeneficiaryById(Integer id) {

        return repo.findById(id);

    }
}


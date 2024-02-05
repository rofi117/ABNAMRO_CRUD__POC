package com.example.springdb.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="beneficiary_table")
public class Beneficiary  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="Beneficiary_Name")
    @NotEmpty
    private String beneficiaryName;

    @Column(name="Beneficiary_AccountNumber",unique=true)
    @NotEmpty
    @Size(max=10)
    private String beneficiaryAccNo;

    @Column(name="Beneficiary_Bank")
    @NotEmpty
    private String beneficiaryBank;

    @Column(name="Beneficiary_IFSC")
    @NotEmpty
    private String beneficiaryIFSC;

//    public Beneficiary(int id, String beneficiaryName, String beneficiaryAccNo, String beneficiaryBank, String beneficiaryIFSC) {
//        this.id = id;
//        this.beneficiaryName = beneficiaryName;
//        this.beneficiaryAccNo = beneficiaryAccNo;
//        this.beneficiaryBank = beneficiaryBank;
//        this.beneficiaryIFSC = beneficiaryIFSC;
//    }
//
//    public Beneficiary() {
//        super();
//    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getBeneficiaryName() {
//        return beneficiaryName;
//    }
//
//    public void setBeneficiaryName(String beneficiaryName) {
//        this.beneficiaryName = beneficiaryName;
//    }
//
//    public String getBeneficiaryAccNo() {
//        return beneficiaryAccNo;
//    }
//
//    public void setBeneficiaryAccNo(String beneficiaryAccNo) {
//        this.beneficiaryAccNo = beneficiaryAccNo;
//    }
//
//    public String getBeneficiaryBank() {
//        return beneficiaryBank;
//    }
//
//    public void setBeneficiaryBank(String beneficiaryBank) {
//        this.beneficiaryBank = beneficiaryBank;
//    }
//
//    public String getBeneficiaryIFSC() {
//        return beneficiaryIFSC;
//    }
//
//    public void setBeneficiaryIFSC(String beneficiaryIFSC) {
//        this.beneficiaryIFSC = beneficiaryIFSC;
//    }
}

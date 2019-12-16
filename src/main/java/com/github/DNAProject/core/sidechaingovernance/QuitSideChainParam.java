package com.github.DNAProject.core.sidechaingovernance;


import com.github.DNAProject.common.Address;

public class QuitSideChainParam {
    public String sideChainID;
    public Address address;
    public QuitSideChainParam(String sideChainID, Address address){
        this.sideChainID = sideChainID;
        this.address = address;
    }

}

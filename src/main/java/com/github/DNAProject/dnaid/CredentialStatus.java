package com.github.DNAProject.dnaid;


import com.alibaba.fastjson.annotation.JSONType;

@JSONType(orders = {"id", "type"})
public class CredentialStatus {

    public String id; // should be CredentialRecord contract address
    public CredentialStatusType type;

    public CredentialStatus() {
    }

    public CredentialStatus(String scriptHash, CredentialStatusType type) {
        this.id = scriptHash;
        this.type = type;
    }
}
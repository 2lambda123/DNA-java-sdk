package com.github.DNAProject.dnaid.jwt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.github.DNAProject.dnaid.CredentialStatus;
import com.github.DNAProject.dnaid.Proof;
import com.github.DNAProject.dnaid.VerifiableCredential;
import com.github.DNAProject.sdk.exception.SDKException;

@JSONType(orders = {"@context", "type", "issuer", "credentialSubject", "credentialStatus", "proof"})
public class JWTVC {
    @JSONField(name = "@context")
    public String[] context;
    public String[] type;
    public Object issuer;
    public Object credentialSubject;
    public CredentialStatus credentialStatus;
    public Proof proof;

    public JWTVC() {
    }

    public JWTVC(VerifiableCredential credential) throws Exception {
        this.context = credential.context;
        this.type = credential.type;
        this.credentialStatus = credential.credentialStatus;
        if (credential.proof != null) {
            this.proof = credential.proof.genJWTProof();
        }
        if (credential.issuer.getClass().isPrimitive() || credential.issuer.getClass().isArray() ||
                credential.issuer instanceof JSONArray) {
            throw new SDKException("illegal credential issuer");
        }
        if (!(credential.issuer instanceof String)) {
            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(credential.issuer);
            jsonObject.remove("id");
            if (jsonObject.size() > 0) {
                this.issuer = jsonObject;
            }
        }
        // remove id attribute
        if (credential.credentialSubject != null && !credential.credentialSubject.getClass().isArray()
                && !(credential.credentialSubject instanceof JSONArray)) {
            JSONObject credentialSubject = (JSONObject) JSONObject.toJSON(credential.credentialSubject);
            credentialSubject.remove("id");
            if (credentialSubject.size() > 0) {
                this.credentialSubject = credentialSubject;
            }
        } else {
            this.credentialSubject = credential.credentialSubject;
        }
    }
}

package com.github.DNAProject.dnaid.jwt;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.DNAProject.dnaid.Proof;
import com.github.DNAProject.dnaid.VerifiableCredential;
import com.github.DNAProject.dnaid.VerifiablePresentation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@JSONType(orders = {"iss", "sub", "aud", "exp", "nbf", "iat", "jti", "nonce", "vc", "vp"})
public class JWTPayload {
    public String iss; // VerifiableCredential issuer
    // VerifiableCredential credential id, if there are more than 1 credentialSubject, cannot parse to JWTPayload
    public String sub;
    public Object aud; // audience, may not present, String or String[]


    @JSONField(serialzeFeatures = SerializerFeature.NotWriteDefaultValue)
    public long exp; // VerifiableCredential expiration, for example 1541493724
    @JSONField(serialzeFeatures = SerializerFeature.NotWriteDefaultValue)
    public long nbf; // VerifiableCredential issue date, for example 1541493724, 定义在什么时间之前,该jwt都是不可用的
    @JSONField(serialzeFeatures = SerializerFeature.NotWriteDefaultValue)
    public long iat; // created date time, same with nbf, for example 1541493724, may not present, jwt的签发时间

    public String jti; // VerifiableCredential id
    public String nonce; // in case of replay attack, generated form proof, may not present
    public JWTVC vc;
    public JWTVP vp;

    public JWTPayload() {
    }

//    public JWTPayload(VerifiableCredential credential, String audience) throws Exception {
//        this(credential);
//        this.aud = audience;
//    }

    public JWTPayload(VerifiableCredential credential) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (credential.expirationDate != null && !credential.expirationDate.isEmpty()) {
            Date exp = formatter.parse(credential.expirationDate);
            this.exp = exp.getTime() / 1000;
        }
        this.iss = credential.fetchIssuerDnaId();
        if (credential.issuanceDate != null && !credential.issuanceDate.isEmpty()) {
            Date nbf = formatter.parse(credential.issuanceDate);
            this.nbf = nbf.getTime() / 1000;
            this.iat = this.nbf;
        }
        this.jti = credential.id;
        String credentialSubjectId = credential.findSubjectId();
        if (!"".equals(credentialSubjectId)) {
            this.sub = credentialSubjectId;
        }
        if (credential.proof != null) {
            this.aud = credential.proof.domain;
            this.nonce = credential.proof.challenge;
        }
        this.vc = new JWTVC(credential);
    }

//    public JWTPayload(VerifiablePresentation presentation, String audience) throws Exception {
//        this(presentation);
//        this.aud = audience;
//    }

    public JWTPayload(VerifiablePresentation presentation, Proof proof) throws Exception {
        if (proof != null) {
            this.aud = proof.domain;
            this.nonce = proof.challenge;
        }
        this.iss = presentation.fetchHolderDnaId();
        this.jti = presentation.id;
        this.vp = new JWTVP(presentation, proof);
    }
}

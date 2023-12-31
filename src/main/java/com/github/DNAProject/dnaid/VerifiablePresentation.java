package com.github.DNAProject.dnaid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.DNAProject.dnaid.jwt.JWTCredential;

import java.util.UUID;

@JSONType(orders = {"@context", "id", "type", "verifiableCredential", "holder", "proof"})
public class VerifiablePresentation {
    @JSONField(name = "@context")
    public String[] context;
    public String id;
    public String[] type;
    public VerifiableCredential[] verifiableCredential;
    public Object holder; // holder may not use
    public Proof[] proof;

    public VerifiablePresentation() {
        this.id = "urn:uuid:" + UUID.randomUUID().toString();
    }

    public byte[] genNeedSignData(Proof needSignProof) {
        Proof[] proofs = this.proof;
        this.proof = new Proof[]{needSignProof.genNeedSignProof()};
        String jsonStr = JSON.toJSONString(this, SerializerFeature.MapSortField);
        this.proof = proofs;
        return jsonStr.getBytes();
    }

    public String fetchHolderDnaId() {
        return Util.fetchId(holder);
    }

    public static VerifiablePresentation deserializeFromJWT(JWTCredential jwtCred)
            throws Exception {
        VerifiablePresentation presentation = new VerifiablePresentation();
        presentation.context = jwtCred.payload.vp.context;
        presentation.id = jwtCred.payload.jti;
        presentation.type = jwtCred.payload.vp.type;
        // assign issuer
        if (jwtCred.payload.vp.holder == null) {
            presentation.holder = jwtCred.payload.iss;
        } else {
            JSONObject jsonHolder = (JSONObject) JSONObject.toJSON(jwtCred.payload.vp.holder);
            jsonHolder.put("id", jwtCred.payload.iss);
            presentation.holder = jsonHolder;
        }
        presentation.proof = new Proof[]{jwtCred.parseProof()};
        int vcLength = jwtCred.payload.vp.verifiableCredential.length;
        VerifiableCredential[] credentials = new VerifiableCredential[vcLength];
        for (int i = 0; i < vcLength; i++) {
            String vc = jwtCred.payload.vp.verifiableCredential[i];
            JWTCredential vcJWT = JWTCredential.deserializeToJWTCred(vc);
            VerifiableCredential credential = VerifiableCredential.deserializeFromJWT(vcJWT);
            credentials[i] = credential;
        }
        presentation.verifiableCredential = credentials;
        return presentation;
    }
}

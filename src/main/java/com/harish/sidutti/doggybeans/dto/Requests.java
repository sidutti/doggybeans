package com.harish.sidutti.doggybeans.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Requests implements JacksonSerializable {

    private String guid = UUID.randomUUID().toString();
    private List<Document> documentList;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public List<Document> getDocumentsList() {
        if (documentList == null) {
            documentList = new ArrayList<>();
        }
        return documentList;
    }

    public void setDocumentsList(List<Document> documentList) {
        this.documentList = documentList;
    }
}

package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.Document;
import com.harish.sidutti.doggybeans.dto.Operation;

public interface CrudService {
    boolean canI(Operation operation);

    Document process(Document document);
}

package com.harish.sidutti.doggybeans.service;

import com.harish.sidutti.doggybeans.dto.Document;
import com.harish.sidutti.doggybeans.dto.Operation;
import org.springframework.stereotype.Component;

@Component
public class CreateService implements CrudService {
    @Override
    public boolean canI(Operation operation) {
        return operation.equals(Operation.CREATE);
    }

    @Override
    public Document process(Document document) {
        document.getProperties().put("Operation", Operation.CREATE.name());
        return document;
    }
}

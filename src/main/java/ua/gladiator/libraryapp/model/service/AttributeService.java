package ua.gladiator.libraryapp.model.service;

import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Attribute;

import java.util.*;

@Service
public interface AttributeService {

    List<Attribute> getAllAttributes();
    Set<Attribute> getAllAttributesByNames(List<String> names);
    Attribute createAttribute(Attribute attribute);

    void deleteAttributeById(Long id);
}

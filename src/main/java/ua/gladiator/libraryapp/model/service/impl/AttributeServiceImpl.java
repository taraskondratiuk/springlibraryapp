package ua.gladiator.libraryapp.model.service.impl;

import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Attribute;
import ua.gladiator.libraryapp.model.repository.AttributeRepository;
import ua.gladiator.libraryapp.model.service.AttributeService;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Resource
    AttributeRepository attributeRepository;

    public List<Attribute> getAllAttributes() {
        return attributeRepository.findAll();
    }
    public Set<Attribute> getAllAttributesByNames(List<String> names) {

        return attributeRepository.findAllByNameIn(names);
    }

    public Attribute createAttribute(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    public void deleteAttributeById(Long id) {
        attributeRepository.deleteById(id);
    }


}

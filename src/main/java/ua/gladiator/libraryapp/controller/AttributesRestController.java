package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Attribute;
import ua.gladiator.libraryapp.model.service.impl.AttributeServiceImpl;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping(value = "/attributes")
public class AttributesRestController {

    @Resource
    private AttributeServiceImpl attributeServiceImpl;
//todo add exceptions
    @GetMapping
    public ResponseEntity<List<Attribute>> getAllAttributes() {
        return new ResponseEntity<>(attributeServiceImpl.getAllAttributes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Attribute> createAttribute(Attribute attribute) {
        System.out.println(attribute);
        return new ResponseEntity<>(attributeServiceImpl.createAttribute(attribute), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAttribute(@PathVariable Long id) {
        attributeServiceImpl.deleteAttributeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/bynames")
    public ResponseEntity<Set<Attribute>> getAttributesByNames(@RequestParam List<String> names) {
        return new ResponseEntity<>(attributeServiceImpl.getAllAttributesByNames(names), HttpStatus.OK);
    }
}

package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Attribute;
import ua.gladiator.libraryapp.model.service.AttributeService;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping(value = "/attributes")
public class AttributesRestController {

    @Resource
    private AttributeService attributeService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    @GetMapping
    public ResponseEntity<List<Attribute>> getAllAttributes() {
        return new ResponseEntity<>(attributeService.getAllAttributes(), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Attribute> createAttribute(Attribute attribute) {
        return new ResponseEntity<>(attributeService.createAttribute(attribute), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity deleteAttribute(@PathVariable Long id) {
        attributeService.deleteAttributeById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    @GetMapping("/bynames")
    public ResponseEntity<Set<Attribute>> getAttributesByNames(@RequestParam List<String> names) {
        return new ResponseEntity<>(attributeService.getAllAttributesByNames(names), HttpStatus.OK);
    }
}

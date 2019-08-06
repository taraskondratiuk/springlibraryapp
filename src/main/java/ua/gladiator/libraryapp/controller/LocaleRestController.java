package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gladiator.libraryapp.model.service.impl.LocaleServiceImpl;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/locale")
public class LocaleRestController {

    @Resource
    private LocaleServiceImpl localeServiceImpl;

    @GetMapping(value = "/{localeName}")
    public ResponseEntity<Object> getEn(@PathVariable String localeName) {
        return new ResponseEntity<>(localeServiceImpl.getJSON(localeName), HttpStatus.OK);
    }

}
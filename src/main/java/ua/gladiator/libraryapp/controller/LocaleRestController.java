package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gladiator.libraryapp.model.service.LocaleService;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/locale")
public class LocaleRestController {

    @Resource
    private LocaleService localeService;

    @GetMapping(value = "/{localeName}")
    public ResponseEntity<Object> getEn(@PathVariable String localeName) {
        return new ResponseEntity<>(localeService.getJSON(localeName), HttpStatus.OK);
    }

}
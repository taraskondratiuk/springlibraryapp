package ua.gladiator.libraryapp.model.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.exception.LocaleException;
import ua.gladiator.libraryapp.model.service.LocaleService;

import java.io.FileReader;
import java.io.IOException;

@Slf4j
@Service
public class LocaleServiceImpl implements LocaleService {

    @Value("${locale.path}")
    private String LOCALE_PATH;

    @Value("${locale.extension}")
    private String LOCALE_EXTENSION;

    public Object getJSON(String locale) {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(LOCALE_PATH + locale + LOCALE_EXTENSION)) {
            return jsonParser.parse(reader);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            log.error("invalid locale request {}", locale);
            throw new LocaleException();
        }
    }
}

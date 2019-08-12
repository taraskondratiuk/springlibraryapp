package ua.gladiator.libraryapp.model.service;

import org.springframework.stereotype.Service;

@Service
public interface LocaleService {
    Object getJSON(String locale);
}

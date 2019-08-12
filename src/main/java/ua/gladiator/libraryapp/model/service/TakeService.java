package ua.gladiator.libraryapp.model.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.entity.User;

@Service
public interface TakeService {
    Take makeTakeReturned(Long id, User user);

    Take takeBook(User currentUser, Book book);

    Page<Take> getFilteredTakes(Boolean isReturned, Long id, String email, Integer page);
}

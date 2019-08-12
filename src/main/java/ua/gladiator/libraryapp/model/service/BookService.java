package ua.gladiator.libraryapp.model.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.entity.dto.BookDto;

import java.util.*;

@Service
public interface BookService {
    Book addBook(BookDto bookDto);
    Book takeBook(Long id, User user);

    void deleteBookById(Long id);
    Page<Book> getBooksByParams(List<String> attributes, String line, String author, Integer pageNum);
}

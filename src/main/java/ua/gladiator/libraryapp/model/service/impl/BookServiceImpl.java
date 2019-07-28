package ua.gladiator.libraryapp.model.service.impl;

import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.exception.BookNotFoundException;
import ua.gladiator.libraryapp.model.repository.BookRepository;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BookServiceImpl {

    @Resource
    private BookRepository bookRepository;


    public Set<Book> getBooksByAttributes(List<String> attributes) {
        Set<Book> books = new HashSet<>();
        attributes.forEach(v -> books.addAll(bookRepository.findAllByAttributes_AttributeIs(v)));
        return books;
        //todo
    }

    public Set<Book> getBooksByTextOrName(String text) {
        return Stream.concat(
                bookRepository.findAllByTextIgnoreCaseContaining(text).stream(),
                bookRepository.findAllByNameIgnoreCaseContaining(text).stream())
                .collect(Collectors.toSet());
        //todo rework
    }

    public List<Book> getBooksByAuthor(String author) {
        return  bookRepository.findAllByAuthorIgnoreCaseContaining(author);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }



    public Book updateBook(Book newBook) {
        Book oldBook = bookRepository
                .findById(newBook.getId())
                .orElseThrow(BookNotFoundException::new);


            oldBook.setAttributes(newBook.getAttributes());
            oldBook.setAuthor(newBook.getAuthor());
            oldBook.setDaysToReturn(newBook.getDaysToReturn());
            oldBook.setIsAvailable(newBook.getAvailable());
            oldBook.setTakes(newBook.getTakes());
            oldBook.setName(newBook.getName());
            oldBook.setPicUrl(newBook.getPicUrl());
            oldBook.setText(newBook.getText());
            return bookRepository.save(oldBook);
    }

    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    public List<Book> getBooksByParams(List<String> attributes, String line, String author) {
        List<Book> books = bookRepository.findAllByIsAvailableTrue();

        if (attributes != null) {
            books.retainAll(getBooksByAttributes(attributes));
        }
        if (line != null) {
            books.retainAll(getBooksByTextOrName(line));
        }
        if (author != null) {
            books.retainAll(getBooksByAuthor(author));
        }

        return books;
    }
}

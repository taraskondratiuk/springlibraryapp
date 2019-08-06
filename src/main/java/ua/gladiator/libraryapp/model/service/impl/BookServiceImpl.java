package ua.gladiator.libraryapp.model.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Attribute;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.dto.BookDto;
import ua.gladiator.libraryapp.model.exception.BookNotFoundException;
import ua.gladiator.libraryapp.model.repository.AttributeRepository;
import ua.gladiator.libraryapp.model.repository.BookRepository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

@Service
public class BookServiceImpl {

    @Resource
    private AttributeRepository attributeRepository;

    @Resource
    private BookRepository bookRepository;

    @Value("${page.size.books}")
    private Integer DEFAULT_BOOKS_PAGE_SIZE;


    public Book addBook(BookDto bookDto) {
        return bookRepository.save(
                Book.builder()
                .addDate(LocalDate.now())
                .attributes(attributeRepository.findAllByNameIn(bookDto.getAttributes()))
                .author(bookDto.getAuthor())
                .daysToReturn(bookDto.getDaysToReturn())
                .isAvailable(true)
                .name(bookDto.getName())
                .picUrl(bookDto.getPicUrl())
                .text(bookDto.getText())
                .build());
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

    public Page<Book> getBooksByParams(List<String> attributes, String line, String author, Integer pageNum) {
        if (attributes == null) {
            attributes = attributeRepository
                    .findAll()
                    .stream()
                    .map(Attribute::getName)
                    .collect(Collectors.toList());
        }
        Pageable pageable = PageRequest.of(pageNum - 1, DEFAULT_BOOKS_PAGE_SIZE, Sort.by("id").descending());

        return bookRepository.findDistinctByAttributes_NameInAndAuthorIgnoreCaseContainingAndTextIgnoreCaseContainingAndNameIgnoreCaseContainingAndIsAvailableTrue(
                attributes,
                author,
                line,
                line,
                pageable);
    }

    public Book addBookDto(BookDto bookDto) {
        return null;
    }
}

package ua.gladiator.libraryapp.model.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Attribute;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.entity.dto.BookDto;
import ua.gladiator.libraryapp.model.exception.BookNotFoundException;
import ua.gladiator.libraryapp.model.repository.AttributeRepository;
import ua.gladiator.libraryapp.model.repository.BookRepository;
import ua.gladiator.libraryapp.model.repository.TakeRepository;
import ua.gladiator.libraryapp.model.service.BookService;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.*;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Resource
    private AttributeRepository attributeRepository;

    @Resource
    private BookRepository bookRepository;

    @Resource
    private TakeRepository takeRepository;


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

    public Book takeBook(Long id, User user) {
        Book oldBook = bookRepository
                .findByIdAndIsAvailableTrue(id)
                .orElseThrow(BookNotFoundException::new);


            oldBook.setIsAvailable(false);

        takeRepository.save(Take.builder()
                .takeDate(LocalDate.now())
                .isReturned(false)
                .returnDeadline(LocalDate.now().plusDays(oldBook.getDaysToReturn()))
                .user(user)
                .book(oldBook)
                .build());
        log.info("book {} taken", oldBook);
            return bookRepository.save(oldBook);
    }

    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        log.info("book {} deleted", book);
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
}

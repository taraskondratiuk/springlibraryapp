
package ua.gladiator.libraryapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.dto.BookDto;
import ua.gladiator.libraryapp.model.service.BookService;
import ua.gladiator.libraryapp.model.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/books")
public class BooksRestController {

    @Resource
    private BookService bookService;

    @Resource
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'READER')")
    @GetMapping
    public ResponseEntity<Page<Book>> getAvailableBooksWithFilter(@RequestParam(required = false) List<String> attributes, @RequestParam(required = false, defaultValue = "") String line,
                                                         @RequestParam(required = false, defaultValue = "") String author,
                                                         @RequestParam(required = false, defaultValue = "1") Integer page) {
        return new ResponseEntity<>(bookService.getBooksByParams(attributes, line, author, page), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.addBook(bookDto), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READER')")
    @PutMapping("/take/{id}")
    public ResponseEntity<Book> takeBook(@PathVariable Long id,
                                         @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(bookService.takeBook(id, userService.getUserByToken(token)), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}

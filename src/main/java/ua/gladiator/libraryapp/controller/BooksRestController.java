
package ua.gladiator.libraryapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.dto.BookDto;
import ua.gladiator.libraryapp.model.service.impl.BookServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping(value = "/books")
public class BooksRestController {

    @Resource
    private BookServiceImpl bookServiceImpl;


    @GetMapping
    public ResponseEntity<Page<Book>> getAvailableBooksWithFilter(@RequestParam(required = false) List<String> attributes,
                                                         @RequestParam(required = false, defaultValue = "") String line,
                                                         @RequestParam(required = false, defaultValue = "") String author,
                                                         @RequestParam(required = false, defaultValue = "1") Integer page) {
        return new ResponseEntity<>(bookServiceImpl.getBooksByParams(attributes, line, author, page), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookServiceImpl.addBook(bookDto), HttpStatus.OK);
    }

    @PutMapping("/take/{id}")
    public ResponseEntity<Book> takeBook(@PathVariable Long id) {
        System.out.println(123);
        return new ResponseEntity<>(bookServiceImpl.takeBook(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
            bookServiceImpl.deleteBookById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}

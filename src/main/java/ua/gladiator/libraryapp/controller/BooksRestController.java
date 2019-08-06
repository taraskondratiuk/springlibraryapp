
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
    public ResponseEntity<Page<Book>> getBooksWithFilter(@RequestParam(required = false) List<String> attribute,
                                                         @RequestParam(required = false, defaultValue = "") String line,
                                                         @RequestParam(required = false, defaultValue = "") String author,
                                                         @RequestParam(required = false, defaultValue = "1") Integer page) {
        return new ResponseEntity<>(bookServiceImpl.getBooksByParams(attribute, line, author, page), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookDto bookDto) {
        System.out.println("skadjflkasdjflkasdj");
        System.out.println(bookDto);

        return new ResponseEntity<>(bookServiceImpl.addBook(bookDto), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookServiceImpl.updateBook(book), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
            bookServiceImpl.deleteBookById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}

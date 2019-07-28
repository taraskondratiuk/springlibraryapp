
package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.service.impl.BookServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/admin/books", "/reader/books"})
public class BooksRestController {

    @Resource
    private BookServiceImpl bookServiceImpl;

    @GetMapping
    public ResponseEntity<List<Book>> getBooksWithFilter(@RequestParam(required = false) List<String> attribute,
                                                      @RequestParam(required = false) String line,
                                                      @RequestParam(required = false) String author) {
        return new ResponseEntity<>(bookServiceImpl.getBooksByParams(attribute, line, author), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Book>> addBook(@Valid @RequestBody Book book) {
        bookServiceImpl.addBook(book);
        return new ResponseEntity<>(bookServiceImpl.getAllBooks(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<Book>> updateBook(@Valid @RequestBody Book book) {
        bookServiceImpl.updateBook(book);
        return new ResponseEntity<>(bookServiceImpl.getAllBooks(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBook(@PathVariable Long id) {
            bookServiceImpl.deleteBookById(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}

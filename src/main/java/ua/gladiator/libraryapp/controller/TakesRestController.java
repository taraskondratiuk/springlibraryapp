package ua.gladiator.libraryapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.service.impl.TakeServiceImpl;
import ua.gladiator.libraryapp.model.service.impl.UserServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/takes")
public class TakesRestController {

    @Resource
    private TakeServiceImpl takeServiceImpl;

    @Resource
    private UserServiceImpl userServiceImpl;


    @GetMapping(path = "/my")
    public ResponseEntity<Page<Take>> getMyTakes(@RequestParam(required = false, defaultValue = "") Boolean returned,
                                                 @RequestParam(required = false, defaultValue = "1") Integer page) {
        //todo
        return new ResponseEntity<>(takeServiceImpl.getFilteredTakes(returned, userServiceImpl.getCurrentUser().getId(), "", page), HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<Page<Take>> getFilteredTakes(@RequestParam(required = false, defaultValue = "") Boolean returned,
                                                       @RequestParam(required = false, defaultValue = "1") Integer page,
                                                       @RequestParam(required = false, defaultValue = "") Long id,
                                                       @RequestParam(required = false, defaultValue = "") String email) {
        return new ResponseEntity<>(takeServiceImpl.getFilteredTakes(returned, id, email, page), HttpStatus.OK);
    }


    @PutMapping("/return/{id}")
    public ResponseEntity<Take> returnBook(@PathVariable Long id) {
        return new ResponseEntity<>(takeServiceImpl.makeTakeReturned(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Take> takeBook(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(takeServiceImpl.takeBook(userServiceImpl.getCurrentUser(), book), HttpStatus.OK);
    }
}

package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.service.impl.TakeServiceImpl;
import ua.gladiator.libraryapp.model.service.impl.UserServiceImpl;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = {"/reader/takes"})
public class TakesRestController {

    @Resource
    private TakeServiceImpl takeServiceImpl;

    @Resource
    private UserServiceImpl userServiceImpl;


    @GetMapping
    public ResponseEntity<List<Take>> getMyTakes() {
        //todo
        return new ResponseEntity<>(takeServiceImpl.getActiveTakesByUserId(userServiceImpl.getCurrentUser().getId()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<Take>> returnBook(@Valid @RequestBody Take take) {
        takeServiceImpl.makeTakeReturned(take);
        return new ResponseEntity<>(takeServiceImpl.getActiveTakesByUserId(userServiceImpl.getCurrentUser().getId()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<List<Take>> takeBook(@Valid @RequestBody Book book) {
        takeServiceImpl.takeBook(userServiceImpl.getCurrentUser(), book);
        return new ResponseEntity<>(takeServiceImpl.getActiveTakesByUserId(userServiceImpl.getCurrentUser().getId()), HttpStatus.OK);
    }
}

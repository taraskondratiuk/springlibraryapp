package ua.gladiator.libraryapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.service.TakeService;
import ua.gladiator.libraryapp.model.service.UserService;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/takes")
public class TakesRestController {

    @Resource
    private TakeService takeService;

    @Resource
    private UserService userService;


    @PreAuthorize("hasAnyAuthority('READER')")
    @GetMapping(path = "/my")
    public ResponseEntity<Page<Take>> getMyTakes(@RequestParam(required = false, defaultValue = "") Boolean returned,
                                                 @RequestParam(required = false, defaultValue = "1") Integer page,
                                                 @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(takeService.getFilteredTakes(returned, userService.getUserByToken(token).getId(), "", page), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<Take>> getFilteredTakes(@RequestParam(required = false, defaultValue = "") Boolean returned,
                                                       @RequestParam(required = false, defaultValue = "1") Integer page,
                                                       @RequestParam(required = false, defaultValue = "") Long id,
                                                       @RequestParam(required = false, defaultValue = "") String email) {
        return new ResponseEntity<>(takeService.getFilteredTakes(returned, id, email, page), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('READER')")
    @PutMapping("/return/{id}")
    public ResponseEntity<Take> returnBook(@PathVariable Long id,
                                           @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(takeService.makeTakeReturned(id, userService.getUserByToken(token)), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READER')")
    @PostMapping
    public ResponseEntity<Take> takeBook(@Valid @RequestBody Book book,
                                         @RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(takeService.takeBook(userService.getUserByToken(token), book), HttpStatus.OK);
    }
}

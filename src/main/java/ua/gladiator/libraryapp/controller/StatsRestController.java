package ua.gladiator.libraryapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.service.impl.TakeServiceImpl;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/stats")
public class StatsRestController {

    @Resource
    private TakeServiceImpl takeServiceImpl;
//todo make stats dto

    @GetMapping("/user/{id}/takes")
    public ResponseEntity<List<Take>> getTakesByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(takeServiceImpl.getActiveTakesByUserId(id), HttpStatus.OK);
    }

    @GetMapping("/takes")
    public ResponseEntity<List<Take>> getAllTakes() {
        return new ResponseEntity<>(takeServiceImpl.getAllActiveTakes(), HttpStatus.OK);
    }
}

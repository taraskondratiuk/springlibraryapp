package ua.gladiator.libraryapp.model.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.exception.TakeNotFoundException;
import ua.gladiator.libraryapp.model.repository.BookRepository;
import ua.gladiator.libraryapp.model.repository.TakeRepository;

import javax.annotation.Resource;
import java.time.LocalDate;

@Service
public class TakeServiceImpl {

    @Value("${page.size.takes}")
    private Integer DEFAULT_TAKE_PAGE_SIZE;

    @Resource
    private TakeRepository takeRepository;

    @Resource
    private BookRepository bookRepository;



    //todo throw exc

    public Take makeTakeReturned(Long id) {

        Take take = takeRepository.findById(id)
                .orElseThrow(TakeNotFoundException::new);

        take.setIsReturned(true);
        take.setReturnDate(LocalDate.now());

        Book book = take.getBook();

        book.setIsAvailable(true);
        bookRepository.save(book);

        return takeRepository.save(take);
    }
//todo test expire date
    public Take takeBook(User currentUser, Book book) {

        Take newTake = Take
                .builder()
                .book(book)
                .isReturned(false)
                .takeDate(LocalDate.now())
                .returnDeadline(LocalDate.now().plusDays(book.getDaysToReturn()))
                .user(currentUser)
                .build();

        book.setIsAvailable(false);
        bookRepository.save(book);
        return takeRepository.save(newTake);
    }

    public Page<Take> getFilteredTakes(Boolean isReturned, Long id, String email, Integer page) {

        Pageable pageable = PageRequest.of(page - 1, DEFAULT_TAKE_PAGE_SIZE, Sort.by("id").descending());

        return takeRepository.findAllByUser_IdAndIsReturned(id, isReturned, email, pageable );

    }
}

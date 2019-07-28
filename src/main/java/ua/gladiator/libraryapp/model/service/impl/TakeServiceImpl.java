package ua.gladiator.libraryapp.model.service.impl;

import org.springframework.stereotype.Service;
import ua.gladiator.libraryapp.model.entity.Book;
import ua.gladiator.libraryapp.model.entity.Take;
import ua.gladiator.libraryapp.model.entity.User;
import ua.gladiator.libraryapp.model.repository.TakeRepository;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class TakeServiceImpl {

    @Resource
    private TakeRepository takeRepository;

    public List<Take> getAllTakesByUserId(Long id) {
        return takeRepository.findAllByUser_Id(id);
    }

    public List<Take> getActiveTakesByUserId(Long id) {
        return takeRepository.findAllByUser_IdAndIsReturnedFalse(id);
    }

    //todo maybe add more stats
    public List<Take> getAllActiveTakes() {
        return takeRepository.findAllByIsReturnedFalse();
    }

    public Take makeTakeReturned(Take take) {
        take.setIsReturned(true);
        return take;
    }
//todo test expire date
    public Take takeBook(User currentUser, Book book) {

        Take newTake = Take
                .builder()
                .book(book)
                .isReturned(false)
                .returnDeadline(LocalDate.now().plusDays(book.getDaysToReturn()))
                .user(currentUser)
                .build();

        return takeRepository.save(newTake);
    }
}

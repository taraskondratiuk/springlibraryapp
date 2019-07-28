package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAttributes_AttributeIs(String attribute);
    List<Book> findAllByIsAvailableTrue();
    List<Book> findAllByIsAvailableFalse();
    List<Book> findAllByTextIgnoreCaseContaining(String text);
    List<Book> findAllByAuthorIgnoreCaseContaining(String author);
    List<Book> findAllByNameIgnoreCaseContaining(String name);
}
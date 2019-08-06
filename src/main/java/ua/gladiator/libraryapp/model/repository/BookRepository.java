package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Book;

import java.util.*;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    List<Book> findAllByAttributes_NameIs(String attribute);
    List<Book> findAllByAttributes_NameIn(List<String> attribute);
    List<Book> findAllByAttributes_NameInAndAuthorIgnoreCaseContainingAndTextIgnoreCaseContainingAndNameIgnoreCaseContaining(List<String> attributes, String author, String text, String name);
    Page<Book> findDistinctByAttributes_NameInAndAuthorIgnoreCaseContainingAndTextIgnoreCaseContainingAndNameIgnoreCaseContainingAndIsAvailableTrue(List<String> attributes, String author, String text, String name, Pageable pageable);
   /* Page<Book> findAllByAttributes_AttributeInAndAuthorIgnoreCaseContainingAndTextIgnoreCaseContainingAndNameIgnoreCaseContaining(List<String> attributes, String author, String text, String name, Pageable pageable);
    List<Book> findAllByIsAvailableTrue();
    Page<Book> findAllByIsAvailableTrue(Pageable pageable);
    List<Book> findAllByIsAvailableFalse();
    List<Book> findAllByTextIgnoreCaseContaining(String text);
    Page<Book> findAllByTextIgnoreCaseContaining(String text, Pageable pageable);
    List<Book> findAllByAuthorIgnoreCaseContaining(String author);
    Page<Book> findAllByAuthorIgnoreCaseContaining(String author, Pageable pageable);
    List<Book> findAllByNameIgnoreCaseContaining(String name);
    Page<Book> findAllByNameIgnoreCaseContaining(String name, Pageable pageable);
    *///List<Book> findAllByNameAndAuthorAndTextAndAttributesIgnoreCaseContaining(String name, String author, String text, List<String> attribute);
}
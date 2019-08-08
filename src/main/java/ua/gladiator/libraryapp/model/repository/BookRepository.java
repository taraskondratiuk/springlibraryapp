package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Book;

import java.util.*;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Page<Book> findDistinctByAttributes_NameInAndAuthorIgnoreCaseContainingAndTextIgnoreCaseContainingAndNameIgnoreCaseContainingAndIsAvailableTrue(List<String> attributes, String author, String text, String name, Pageable pageable);
    Optional<Book> findByIdAndIsAvailableTrue(Long id);
}
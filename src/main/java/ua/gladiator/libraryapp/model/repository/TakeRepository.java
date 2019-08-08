package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Take;

@Repository
public interface TakeRepository extends PagingAndSortingRepository<Take, Long> {
    @Query("SELECT t FROM Take t WHERE (:isReturned IS NULL OR t.isReturned =: isReturned) AND (:id IS NULL OR t.user.id =:id) AND (t.user.email LIKE %:email%) ORDER BY t.id DESC")
    Page<Take> findAllByUser_IdAndIsReturned(Long id, Boolean isReturned, String email, Pageable pageable);
}

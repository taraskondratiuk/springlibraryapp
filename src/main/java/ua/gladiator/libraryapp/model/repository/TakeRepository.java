package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Take;

import java.util.List;

@Repository
public interface TakeRepository extends JpaRepository<Take, Long> {
    List<Take> findAllByUser_Id(Long id);
    List<Take> findAllByIsReturnedFalse();
    List<Take> findAllByUser_IdAndIsReturnedFalse(Long id);

}

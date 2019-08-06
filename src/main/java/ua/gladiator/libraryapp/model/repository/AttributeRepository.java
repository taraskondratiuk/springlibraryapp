package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Attribute;

import java.util.*;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
    Set<Attribute> findAllByNameIn(List<String> attributes);
}

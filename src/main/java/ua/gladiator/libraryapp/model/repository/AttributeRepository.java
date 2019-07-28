package ua.gladiator.libraryapp.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.gladiator.libraryapp.model.entity.Attribute;

@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {
}

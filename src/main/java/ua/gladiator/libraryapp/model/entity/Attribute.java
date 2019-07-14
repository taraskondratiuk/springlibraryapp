package ua.gladiator.libraryapp.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "attributes")
@Data
public class Attribute {
    @Id
    @NotEmpty
    private String attribute;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attributes")
    private Set<Book> books;
}

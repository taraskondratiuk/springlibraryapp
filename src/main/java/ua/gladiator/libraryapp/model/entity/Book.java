package ua.gladiator.libraryapp.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Data
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String author;

    private String name;

    @NotEmpty
    @Basic(optional = false)
    @Lob
    private String text;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "days_to_return")
    private Integer daysToReturn;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "books_attributes",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute"))
    Set<Attribute> attributes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    Set<BookUser> issues;
}

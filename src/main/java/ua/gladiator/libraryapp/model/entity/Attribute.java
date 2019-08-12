package ua.gladiator.libraryapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Table(name = "attributes")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;


    @NotEmpty
    @Column(unique = true, nullable = false, name = "attribute")
    private String name;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attributes", cascade = CascadeType.MERGE)
    private Set<Book> books;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @JsonIgnore
    public Set<Book> getBooks() {
        return books;
    }
}

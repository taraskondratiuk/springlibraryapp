package ua.gladiator.libraryapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Table(name = "attributes")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attribute_id")
    private Long id;


    @NotEmpty
    @Column(unique = true, nullable = false)
    private String attribute;


    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "attributes", cascade = CascadeType.ALL)
    private Set<Book> books;

    public Long getId() {
        return id;
    }

    public String getAttribute() {
        return attribute;
    }

    @JsonIgnore
    public Set<Book> getBooks() {
        return books;
    }
}

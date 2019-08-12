package ua.gladiator.libraryapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Builder
@Table(name = "books")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String author;

    @NotEmpty
    @Basic(optional = false)
    @Column(unique = true)
    private String name;

    @NotEmpty
    @Basic(optional = false)
    @Lob
    private String text;

    @NotNull
    @Basic(optional = false)
    @Column(name = "days_to_return")
    private Integer daysToReturn;

    @Column(name = "pic_url")
    private String picUrl;

    @Basic(optional = false)
    @Column(name = "is_available")
    @ColumnDefault("1")
    private Boolean isAvailable;

    @Basic(optional = false)
    @Column(name = "add_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate addDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "books_attributes",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "attribute_id"))
    private Set<Attribute> attributes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
    private Set<Take> takes;

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public Integer getDaysToReturn() {
        return daysToReturn;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public LocalDate getAddDate() {
        return addDate;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    @JsonIgnore
    public Set<Take> getTakes() {
        return takes;
    }
}

package ua.gladiator.libraryapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
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

    @Column(name = "pic_url")
    private String picUrl;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "is_available")
    @ColumnDefault("1")
    private Boolean isAvailable;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "add_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate addDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

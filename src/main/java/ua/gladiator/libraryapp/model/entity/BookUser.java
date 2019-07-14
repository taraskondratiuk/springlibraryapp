package ua.gladiator.libraryapp.model.entity;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "books_users")
public class BookUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_issue_id")
    private Long id;

    @NotEmpty
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotEmpty
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "return_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime returnDeadline;

    @NotEmpty
    @Basic(optional = false)
    @ColumnDefault("0")
    @Column(name = "is_returned")
    private Boolean isReturned;
}

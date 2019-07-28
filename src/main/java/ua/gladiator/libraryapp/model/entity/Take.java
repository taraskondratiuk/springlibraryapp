package ua.gladiator.libraryapp.model.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "books_users")
@Builder
public class Take {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "take_id")
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
    private LocalDate returnDeadline;

    @NotEmpty
    @Basic(optional = false)
    @ColumnDefault("0")
    @Column(name = "is_returned")
    private Boolean isReturned;
}

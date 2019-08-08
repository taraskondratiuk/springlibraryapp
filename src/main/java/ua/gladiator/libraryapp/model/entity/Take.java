package ua.gladiator.libraryapp.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "books_users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Take {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "take_id")
    private Long id;

    @NotNull
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @Basic(optional = false)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Basic(optional = false)
    @Column(name = "take_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate takeDate;

    @Column(name = "return_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDate;

    @NotNull
    @Basic(optional = false)
    @Column(name = "return_deadline")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate returnDeadline;

    @NotNull
    @Basic(optional = false)
    @ColumnDefault("0")
    @Column(name = "is_returned")
    private Boolean isReturned;
}

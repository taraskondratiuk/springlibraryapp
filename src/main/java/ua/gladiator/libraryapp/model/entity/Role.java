package ua.gladiator.libraryapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.*;

@Entity
@Data
@ToString
@Table(name = "roles")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    Long id;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "role_name", unique = true)
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @Override
    public String getAuthority() {
        return name;
    }
}

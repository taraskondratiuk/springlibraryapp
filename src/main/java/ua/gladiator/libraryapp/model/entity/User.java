package ua.gladiator.libraryapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;

    @NotEmpty
    @Basic(optional = false)
    @Column(unique = true)
    private String email;

    @NotNull
    @Basic(optional = false)
    @Column(name = "phone_number", unique = true)
    private Integer phoneNumber;


    @Basic(optional = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "role_id")})
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    Set<Take> takes;


    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    @JsonIgnore
    public List<Role> getRoles() {
        return roles;
    }

    @JsonIgnore
    public Set<Take> getTakes() {
        return takes;
    }
}

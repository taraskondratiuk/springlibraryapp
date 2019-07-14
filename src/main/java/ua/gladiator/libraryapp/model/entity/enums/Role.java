package ua.gladiator.libraryapp.model.entity.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_READER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}

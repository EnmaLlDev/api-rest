package com.fp.api_rest.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fp.api_rest.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Implementación de UserDetails que envuelve la entidad User para Spring Security.
 */
public class UserPrincipal implements UserDetails {

    @JsonIgnore
    private final User user;
    private final Collection<? extends GrantedAuthority> authorities;

    /**
     * Construye el principal a partir de un usuario, generando sus autoridades.
     * @param user entidad usuario
     */
    public UserPrincipal(User user) {
        this.user = user;
        this.authorities = user.getRoles().stream()
                .map(r -> {
                    String raw = r.getName() == null ? "" : r.getName().trim();
                    String normalized = raw.startsWith("ROLE_") ? raw.substring(5) : raw;
                    return new SimpleGrantedAuthority(normalized);
                })
                .collect(Collectors.toSet());
    }

    /**
     * Devuelve la entidad usuario subyacente.
     * @return entidad User
     */
    public User getUser() {
        return user;
    }

    /**
     * Devuelve el ID del usuario.
     * @return identificador del usuario
     */
    public Long getId() {
        return user.getId();
    }

    /**
     * Devuelve los roles del usuario como GrantedAuthority.
     * @return colección de autoridades
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Devuelve la contraseña del usuario.
     * @return contraseña encriptada
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Devuelve el nombre de usuario.
     * @return nombre de usuario
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * Indica si la cuenta no ha expirado.
     * @return siempre true
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indica si la cuenta no está bloqueada.
     * @return siempre true
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indica si las credenciales no han expirado.
     * @return siempre true
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indica si el usuario está activo.
     * @return estado activo del usuario
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}

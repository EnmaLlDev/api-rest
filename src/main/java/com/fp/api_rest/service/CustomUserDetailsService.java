package com.fp.api_rest.service;

import com.fp.api_rest.model.User;
import com.fp.api_rest.repository.dao.UserDAO;
import com.fp.api_rest.security.UserPrincipal;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

/**
 * Servicio que carga los detalles del usuario para Spring Security.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Busca un usuario por nombre y lo envuelve en un UserPrincipal.
     * @param username nombre de usuario
     * @return detalles del usuario para Spring Security
     * @throws UsernameNotFoundException si el usuario no existe
     */
    @Override
    @Cacheable(value = "userDetails", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new UserPrincipal(user);
    }
}

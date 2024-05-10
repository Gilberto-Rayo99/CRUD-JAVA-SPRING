package med.voll.api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Retrieves the user details for the given login.
     *
     * @param login the login of the user
     * @return the user details for the given login
     */
    UserDetails findByLogin(String username);
    
}

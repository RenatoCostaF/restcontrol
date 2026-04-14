package repositories;

import entities.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String name);
    /*List<User> findAll(int size, int offset);*/
    UserDetails findByLogin(String login);
    Integer save(User user); //Criar novo usuario
    Integer changePassword(Long id, String password); //Trocar senha
    Integer update(User user, Long id); //Atualizar demais informacoes
    Integer delete(Long id);//Remove usuario existente
}

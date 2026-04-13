package repositories;

import entities.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String name);
    /*List<User> findAll(int size, int offset);*/
    Integer save(User user); //Criar novo usuario
    Integer changePassword(Long id, String password); //Trocar senha
    Integer update(User user, Long id); //Atualizar demais informacoes
    Integer delete(Long id);//Remove usuario existente
}

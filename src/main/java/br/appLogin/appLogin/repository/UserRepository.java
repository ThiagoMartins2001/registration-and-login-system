package br.appLogin.appLogin.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.appLogin.appLogin.model.User;

public interface UserRepository extends CrudRepository<User,  Long> {

    User findById(long id);

    @Query (value = "select * from webgestao. user where email = :email and password = :password", nativeQuery = true)
    public User login(String email, String password); 
}

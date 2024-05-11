package org.example.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.example.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public boolean existsByEmail(String email);
    public Optional<User> findByEmail(String email);
}

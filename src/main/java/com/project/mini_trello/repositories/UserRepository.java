package com.project.mini_trello.repositories;

import com.project.mini_trello.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche par nom (utile pour éviter les doublons)
    Optional<User> findByNom(String nom);

    // Vérifie l'existence d'un nom (validation)
    boolean existsByNom(String nom);

}

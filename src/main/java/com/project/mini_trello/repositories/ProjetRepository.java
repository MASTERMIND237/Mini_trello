package com.project.mini_trello.repositories;

import com.project.mini_trello.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {

    // Tous les projets d'un user
    List<Projet> findByUser_Id_User(Long userId);

    // Filtrer par statut (ex: "EN_COURS")
    List<Projet> findByStatut(String statut);

    List<Projet> findByNom(String nom);

    // Projets d'un user filtrés par statut
    List<Projet> findByUser_Id_UserAndStatut(Long userId, String statut);
}

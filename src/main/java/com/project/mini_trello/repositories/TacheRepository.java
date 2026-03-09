package com.project.mini_trello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mini_trello.entities.Tache;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TacheRepository extends JpaRepository<Tache, Integer> {

    // Toutes les tâches d'un projet
    List<Tache> findByProjet_Id_Projet(Long projetId);

    // Tâches dont la date de fin est dépassée (retard)
    List<Tache> findByDate_finBefore(LocalDateTime now);

    //pour la recherche selon la date de debut de la tache
    List<Tache> findByDate_debut(LocalDateTime date_debut);

    //recherche selon le nom de la tache
    List<Tache> findByNom(String nom);

    // Tâches d'un projet dont la date de fin est dépassée
    List<Tache> findByProjet_Id_ProjetAndDate_finBefore(Long projetId, LocalDateTime now);
}

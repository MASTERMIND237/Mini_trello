package com.project.mini_trello.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.mini_trello.entities.Categorie;

import java.util.Optional;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Integer> {

    // Recherche par nom exact
    Optional<Categorie> findByNom(String nom);

    // Vérifie existence (éviter les doublons de catégorie)
    boolean existsByNom(String nom);
}
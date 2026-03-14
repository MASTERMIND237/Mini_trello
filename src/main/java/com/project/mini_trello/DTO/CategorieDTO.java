package com.project.mini_trello.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
 
/**
 * DTO pour l'entité Categorie.
 * — Volontairement léger : une catégorie n'expose pas ses listes de projets/tâches
 *   pour éviter la surcharge JSON. Les relations sont gérées via des endpoints dédiés
 *   dans CategorieController (assignerAuProjet, assignerATache, etc.)
 */
public class CategorieDTO {
 
    // Lecture seule
    private int id_categorie;
 
    @NotBlank(message = "Le nom de la catégorie est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;
 
    // ── Constructors ──────────────────────────────────────────────────────────
    public CategorieDTO() {}
 
    public CategorieDTO(int id_categorie, String nom) {
        this.id_categorie = id_categorie;
        this.nom          = nom;
    }
 
    // ── Getters & Setters ─────────────────────────────────────────────────────
    public int getId_categorie() { return id_categorie; }
    public void setId_categorie(int id_categorie) { this.id_categorie = id_categorie; }
 
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
}
 
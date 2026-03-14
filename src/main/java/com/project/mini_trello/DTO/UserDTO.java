package com.project.mini_trello.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO pour l'entité User.
 * — id_User en lecture seule (jamais fourni par le client en création)
 * — @NotBlank + @Size pour la validation automatique via @Valid dans le Controller
 */
public class UserDTO {

    // Lecture seule — renvoyé dans les réponses, ignoré en création
    private Long id_User;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    private String nom;

    @Size(max = 500, message = "La bibliographie ne peut pas dépasser 500 caractères")
    private String bibliographie;

    // ── Constructors ──────────────────────────────────────────────────────────
    public UserDTO() {}

    public UserDTO(Long id_User, String nom, String bibliographie) {
        this.id_User       = id_User;
        this.nom           = nom;
        this.bibliographie = bibliographie;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getId_User() { return id_User; }
    public void setId_User(Long id_User) { this.id_User = id_User; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getBibliographie() { return bibliographie; }
    public void setBibliographie(String bibliographie) { this.bibliographie = bibliographie; }
}

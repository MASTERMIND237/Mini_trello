package com.project.mini_trello.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
import java.time.LocalDateTime;
 
/**
 * DTO pour l'entité Tache.
 * — projetId expose l'id du projet parent sans exposer l'objet Projet entier
 * — @NotNull sur les dates car LocalDateTime n'est pas une String → @NotBlank ne s'applique pas
 */
public class TacheDTO {
 
    // Lecture seule
    private int id_Taches;
 
    @NotBlank(message = "Le nom de la tâche est obligatoire")
    @Size(min = 2, max = 150, message = "Le nom doit contenir entre 2 et 150 caractères")
    private String nom;
 
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;
 
    @NotNull(message = "La date de début est obligatoire")
    private LocalDateTime date_debut;
 
    @NotNull(message = "La date de fin est obligatoire")
    private LocalDateTime date_fin;
 
    @NotNull(message = "La durée est obligatoire")
    private LocalDateTime duree;
 
    // Référence légère vers le projet parent
    private Long projetId;
 
    // ── Constructors ──────────────────────────────────────────────────────────
    public TacheDTO() {}
 
    public TacheDTO(int id_Taches, String nom, String description,
                    LocalDateTime date_debut, LocalDateTime date_fin,
                    LocalDateTime duree, Long projetId) {
        this.id_Taches   = id_Taches;
        this.nom         = nom;
        this.description = description;
        this.date_debut  = date_debut;
        this.date_fin    = date_fin;
        this.duree       = duree;
        this.projetId    = projetId;
    }
 
    // ── Getters & Setters ─────────────────────────────────────────────────────
    public int getId_Taches() { return id_Taches; }
    public void setId_Taches(int id_Taches) { this.id_Taches = id_Taches; }
 
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
 
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
 
    public LocalDateTime getDate_debut() { return date_debut; }
    public void setDate_debut(LocalDateTime date_debut) { this.date_debut = date_debut; }
 
    public LocalDateTime getDate_fin() { return date_fin; }
    public void setDate_fin(LocalDateTime date_fin) { this.date_fin = date_fin; }
 
    public LocalDateTime getDuree() { return duree; }
    public void setDuree(LocalDateTime duree) { this.duree = duree; }
 
    public Long getProjetId() { return projetId; }
    public void setProjetId(Long projetId) { this.projetId = projetId; }
}
 

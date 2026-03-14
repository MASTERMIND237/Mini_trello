package com.project.mini_trello.DTO;

import jakarta.validation.constraints.*;
 
/**
 * DTO pour l'entité Projet.
 * — userId expose l'id du propriétaire sans exposer l'objet User entier
 *   → évite la récursion infinie lors de la sérialisation JSON
 */
public class ProjetDTO {
 
    // Lecture seule
    private Long id_Projet;
 
    @NotBlank(message = "Le nom du projet est obligatoire")
    @Size(min = 2, max = 150, message = "Le nom doit contenir entre 2 et 150 caractères")
    private String nom;
 
    @Size(max = 1000, message = "La description ne peut pas dépasser 1000 caractères")
    private String description;
 
    @NotBlank(message = "Le statut est obligatoire")
    @Pattern(
        regexp = "EN_COURS|EN_ATTENTE|TERMINE|ANNULE",
        message = "Statut invalide — valeurs acceptées : EN_COURS, EN_ATTENTE, TERMINE, ANNULE"
    )
    private String statut;
 
    @Min(value = 0,   message = "L'avancement ne peut pas être inférieur à 0")
    @Max(value = 100, message = "L'avancement ne peut pas dépasser 100")
    private int avancement;
 
    // Référence légère vers le propriétaire — pas l'objet User complet
    private Long userId;
 
    // ── Constructors ──────────────────────────────────────────────────────────
    public ProjetDTO() {}
 
    public ProjetDTO(Long id_Projet, String nom, String description,
                     String statut, int avancement, Long userId) {
        this.id_Projet   = id_Projet;
        this.nom         = nom;
        this.description = description;
        this.statut      = statut;
        this.avancement  = avancement;
        this.userId      = userId;
    }
 
    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getId_Projet() { return id_Projet; }
    public void setId_Projet(Long id_Projet) { this.id_Projet = id_Projet; }
 
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
 
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
 
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
 
    public int getAvancement() { return avancement; }
    public void setAvancement(int avancement) { this.avancement = avancement; }
 
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
 
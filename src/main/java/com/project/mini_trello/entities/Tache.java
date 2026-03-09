package com.project.mini_trello.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taches")
public class Tache {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_Taches;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime date_debut;

    @Column(nullable = false)
    private LocalDateTime date_fin;

    @Column(nullable = false)
    private LocalDateTime duree;      // ou Duration si tu préfères calculer dynamiquement

    // ── Relations ─────────────────────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;

    @ManyToMany(mappedBy = "taches")
    private List<Categorie> categories = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────────
    public Tache() {}

    public Tache(String nom, String description,
                 LocalDateTime date_debut, LocalDateTime date_fin, LocalDateTime duree) {
        this.nom = nom;
        this.description = description;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.duree = duree;
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

    public Projet getProjet() { return projet; }
    public void setProjet(Projet projet) { this.projet = projet; }

    public List<Categorie> getCategories() { return categories; }
    public void setCategories(List<Categorie> categories) { this.categories = categories; }
}
package com.project.mini_trello.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projets")
public class Projet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_Projet;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String statut;          // ex: "EN_COURS", "TERMINE", "EN_ATTENTE"

    @Column(nullable = false)
    private int avancement;         // 0 → 100 (%)

    // ── Relations ─────────────────────────────────────────────────────────────

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches = new ArrayList<>();

    @ManyToMany(mappedBy = "projets")
    private List<Categorie> categories = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────────
    public Projet() {}

    public Projet(String nom, String description, String statut, int avancement) {
        this.nom = nom;
        this.description = description;
        this.statut = statut;
        this.avancement = avancement;
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

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public List<Tache> getTaches() { return taches; }
    public void setTaches(List<Tache> taches) { this.taches = taches; }

    public List<Categorie> getCategories() { return categories; }
    public void setCategories(List<Categorie> categories) { this.categories = categories; }

    // ── Helper ────────────────────────────────────────────────────────────────
    public void addTache(Tache tache) {
        taches.add(tache);
        tache.setProjet(this);
    }

    public void removeTache(Tache tache) {
        taches.remove(tache);
        tache.setProjet(null);
    }
}
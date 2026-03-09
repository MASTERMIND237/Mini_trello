package com.project.mini_trello.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_categorie;

    @Column(nullable = false)
    private String nom;

    // ── Relations ─────────────────────────────────────────────────────────────

    /**
     * Categorie ↔ Projets  (Many-to-Many)
     * Table de jointure : categorie_projet
     */
    @ManyToMany
    @JoinTable(
        name = "categorie_projet",
        joinColumns        = @JoinColumn(name = "categorie_id"),
        inverseJoinColumns = @JoinColumn(name = "projet_id")
    )
    private List<Projet> projets = new ArrayList<>();

    /**
     * Categorie ↔ Taches  (Many-to-Many)
     * Table de jointure : categorie_tache
     */
    @ManyToMany
    @JoinTable(
        name = "categorie_tache",
        joinColumns        = @JoinColumn(name = "categorie_id"),
        inverseJoinColumns = @JoinColumn(name = "tache_id")
    )
    private List<Tache> taches = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────────
    public Categorie() {}

    public Categorie(String nom) {
        this.nom = nom;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public int getId_categorie() { return id_categorie; }
    public void setId_categorie(int id_categorie) { this.id_categorie = id_categorie; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public List<Projet> getProjets() { return projets; }
    public void setProjets(List<Projet> projets) { this.projets = projets; }

    public List<Tache> getTaches() { return taches; }
    public void setTaches(List<Tache> taches) { this.taches = taches; }

    // ── Helpers ───────────────────────────────────────────────────────────────
    public void addProjet(Projet projet) {
        projets.add(projet);
        projet.getCategories().add(this);
    }

    public void removeProjet(Projet projet) {
        projets.remove(projet);
        projet.getCategories().remove(this);
    }

    public void addTache(Tache tache) {
        taches.add(tache);
        tache.getCategories().add(this);
    }

    public void removeTache(Tache tache) {
        taches.remove(tache);
        tache.getCategories().remove(this);
    }
}
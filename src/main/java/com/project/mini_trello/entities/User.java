package com.project.mini_trello.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_User;

    @Column(nullable = false)
    private String nom;

    @Column(columnDefinition = "TEXT")
    private String bibliographie;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Projet> projets = new ArrayList<>();

    // ── Constructors ──────────────────────────────────────────────────────────
    public User() {}

    public User(String nom, String bibliographie) {
        this.nom = nom;
        this.bibliographie = bibliographie;
    }

    // ── Getters & Setters ─────────────────────────────────────────────────────
    public Long getId_User() { return id_User; }
    public void setId_User(Long id_User) { this.id_User = id_User; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getBibliographie() { return bibliographie; }
    public void setBibliographie(String bibliographie) { this.bibliographie = bibliographie; }

    public List<Projet> getProjets() { return projets; }
    public void setProjets(List<Projet> projets) { this.projets = projets; }

    // ── Helper ────────────────────────────────────────────────────────────────
    public void addProjet(Projet projet) {
        projets.add(projet);
        projet.setUser(this);
    }

    public void removeProjet(Projet projet) {
        projets.remove(projet);
        projet.setUser(null);
    }
}

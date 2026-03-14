package com.project.mini_trello.services;

import com.project.mini_trello.DTO.CategorieDTO;
import com.project.mini_trello.entities.Categorie;
import com.project.mini_trello.entities.Projet;
import com.project.mini_trello.entities.Tache;
import com.project.mini_trello.exceptions.ResourceAlreadyExistsException;
import com.project.mini_trello.exceptions.ResourceNotFoundException;
import com.project.mini_trello.repositories.CategorieRepository;
import com.project.mini_trello.repositories.ProjetRepository;
import com.project.mini_trello.repositories.TacheRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@Transactional
public class CategorieService {
 
    private final CategorieRepository categorieRepository;
    private final ProjetRepository    projetRepository;
    private final TacheRepository     tacheRepository;
    private final ModelMapper         modelMapper;
 
    public CategorieService(CategorieRepository categorieRepository,
                            ProjetRepository projetRepository,
                            TacheRepository tacheRepository,
                            ModelMapper modelMapper) {
        this.categorieRepository = categorieRepository;
        this.projetRepository    = projetRepository;
        this.tacheRepository     = tacheRepository;
        this.modelMapper         = modelMapper;
    }
 
    // ── GET ALL ───────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<CategorieDTO> findAll() {
        return categorieRepository.findAll()
                .stream()
                .map(cat -> modelMapper.map(cat, CategorieDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── GET BY ID ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public CategorieDTO findById(int id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", id));
        return modelMapper.map(categorie, CategorieDTO.class);
    }
 
    // ── CREATE ────────────────────────────────────────────────────────────────
    public CategorieDTO create(CategorieDTO dto) {
        if (categorieRepository.existsByNom(dto.getNom())) {
            throw new ResourceAlreadyExistsException("Categorie", "nom", dto.getNom());
        }
        Categorie saved = categorieRepository.save(modelMapper.map(dto, Categorie.class));
        return modelMapper.map(saved, CategorieDTO.class);
    }
 
    // ── UPDATE ────────────────────────────────────────────────────────────────
    public CategorieDTO update(int id, CategorieDTO dto) {
        Categorie existing = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", id));
 
        existing.setNom(dto.getNom());
 
        return modelMapper.map(categorieRepository.save(existing), CategorieDTO.class);
    }
 
    // ── DELETE ────────────────────────────────────────────────────────────────
    public void delete(int id) {
        Categorie existing = categorieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", id));
        categorieRepository.delete(existing);
    }
 
    // ── ASSIGNER Catégorie → Projet ───────────────────────────────────────────
    public void assignerAuProjet(int categorieId, Long projetId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", categorieId));
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", "id", projetId));
 
        categorie.addProjet(projet);
        categorieRepository.save(categorie);
    }
 
    // ── RETIRER Catégorie → Projet ────────────────────────────────────────────
    public void retirerDuProjet(int categorieId, Long projetId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", categorieId));
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", "id", projetId));
 
        categorie.removeProjet(projet);
        categorieRepository.save(categorie);
    }
 
    // ── ASSIGNER Catégorie → Tâche ────────────────────────────────────────────
    public void assignerATache(int categorieId, int tacheId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", categorieId));
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", "id", tacheId));
 
        categorie.addTache(tache);
        categorieRepository.save(categorie);
    }
 
    // ── RETIRER Catégorie → Tâche ─────────────────────────────────────────────
    public void retirerDeTache(int categorieId, int tacheId) {
        Categorie categorie = categorieRepository.findById(categorieId)
                .orElseThrow(() -> new ResourceNotFoundException("Categorie", "id", categorieId));
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", "id", tacheId));
 
        categorie.removeTache(tache);
        categorieRepository.save(categorie);
    }
}
 

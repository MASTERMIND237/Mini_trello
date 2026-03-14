package com.project.mini_trello.services;

import com.project.mini_trello.DTO.TacheDTO;
import com.project.mini_trello.entities.Projet;
import com.project.mini_trello.entities.Tache;
import com.project.mini_trello.exceptions.ResourceNotFoundException;
import com.project.mini_trello.repositories.ProjetRepository;
import com.project.mini_trello.repositories.TacheRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@Transactional
public class TacheService {
 
    private final TacheRepository  tacheRepository;
    private final ProjetRepository projetRepository;
    private final ModelMapper      modelMapper;
 
    public TacheService(TacheRepository tacheRepository,
                        ProjetRepository projetRepository,
                        ModelMapper modelMapper) {
        this.tacheRepository  = tacheRepository;
        this.projetRepository = projetRepository;
        this.modelMapper      = modelMapper;
    }
 
    // ── GET ALL ───────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<TacheDTO> findAll() {
        return tacheRepository.findAll()
                .stream()
                .map(tache -> modelMapper.map(tache, TacheDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── GET BY ID ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public TacheDTO findById(int id) {
        Tache tache = tacheRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", "id", id));
        return modelMapper.map(tache, TacheDTO.class);
    }
 
    // ── GET BY PROJET ─────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<TacheDTO> findByProjet(Long projetId) {
        if (!projetRepository.existsById(projetId)) {
            throw new ResourceNotFoundException("Projet", "id", projetId);
        }
        return tacheRepository.findByProjet_Id_Projet(projetId)
                .stream()
                .map(tache -> modelMapper.map(tache, TacheDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── GET TACHES EN RETARD ──────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<TacheDTO> findTachesEnRetard() {
        return tacheRepository.findByDate_finBefore(LocalDateTime.now())
                .stream()
                .map(tache -> modelMapper.map(tache, TacheDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── CREATE ────────────────────────────────────────────────────────────────
    public TacheDTO create(Long projetId, TacheDTO dto) {
        Projet projet = projetRepository.findById(projetId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", "id", projetId));
 
        Tache tache = modelMapper.map(dto, Tache.class);
        projet.addTache(tache);  // helper bidirectionnel
 
        return modelMapper.map(tacheRepository.save(tache), TacheDTO.class);
    }
 
    // ── UPDATE ────────────────────────────────────────────────────────────────
    public TacheDTO update(int id, TacheDTO dto) {
        Tache existing = tacheRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", "id", id));
 
        existing.setNom(dto.getNom());
        existing.setDescription(dto.getDescription());
        existing.setDate_debut(dto.getDate_debut());
        existing.setDate_fin(dto.getDate_fin());
        existing.setDuree(dto.getDuree());
 
        return modelMapper.map(tacheRepository.save(existing), TacheDTO.class);
    }
 
    // ── DELETE ────────────────────────────────────────────────────────────────
    public void delete(int id) {
        Tache existing = tacheRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache", "id", id));
        tacheRepository.delete(existing);
    }
}
 

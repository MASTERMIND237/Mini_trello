package com.project.mini_trello.services;

import com.project.mini_trello.DTO.ProjetDTO;
import com.project.mini_trello.entities.Projet;
import com.project.mini_trello.entities.User;
import com.project.mini_trello.exceptions.ResourceNotFoundException;
import com.project.mini_trello.repositories.ProjetRepository;
import com.project.mini_trello.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@Transactional
public class ProjetService {
 
    private final ProjetRepository projetRepository;
    private final UserRepository   userRepository;
    private final ModelMapper      modelMapper;
 
    public ProjetService(ProjetRepository projetRepository,
                         UserRepository userRepository,
                         ModelMapper modelMapper) {
        this.projetRepository = projetRepository;
        this.userRepository   = userRepository;
        this.modelMapper      = modelMapper;
    }
 
    // ── GET ALL ───────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProjetDTO> findAll() {
        return projetRepository.findAll()
                .stream()
                .map(projet -> modelMapper.map(projet, ProjetDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── GET BY ID ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProjetDTO findById(Long id) {
        Projet projet = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", "id", id));
        return modelMapper.map(projet, ProjetDTO.class);
    }
 
    // ── GET BY USER ───────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProjetDTO> findByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
        return projetRepository.findByUser_Id_User(userId)
                .stream()
                .map(projet -> modelMapper.map(projet, ProjetDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── CREATE ────────────────────────────────────────────────────────────────
    public ProjetDTO create(Long userId, ProjetDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
 
        Projet projet = modelMapper.map(dto, Projet.class);
        user.addProjet(projet);  // helper bidirectionnel — synchronise les deux côtés
 
        return modelMapper.map(projetRepository.save(projet), ProjetDTO.class);
    }
 
    // ── UPDATE ────────────────────────────────────────────────────────────────
    public ProjetDTO update(Long id, ProjetDTO dto) {
        Projet existing = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", "id", id));
 
        existing.setNom(dto.getNom());
        existing.setDescription(dto.getDescription());
        existing.setStatut(dto.getStatut());
        existing.setAvancement(dto.getAvancement());
 
        return modelMapper.map(projetRepository.save(existing), ProjetDTO.class);
    }
 
    // ── DELETE ────────────────────────────────────────────────────────────────
    public void delete(Long id) {
        Projet existing = projetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet", "id", id));
        projetRepository.delete(existing);
    }
}
 

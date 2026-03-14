package com.project.mini_trello.services;

import com.project.mini_trello.DTO.UserDTO;
import com.project.mini_trello.entities.User;
import com.project.mini_trello.exceptions.ResourceAlreadyExistsException;
import com.project.mini_trello.exceptions.ResourceNotFoundException;
import com.project.mini_trello.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
 
import java.util.List;
import java.util.stream.Collectors;
 
@Service
@Transactional
public class UserService {
 
    private final UserRepository userRepository;
    private final ModelMapper    modelMapper;
 
    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper    = modelMapper;
    }
 
    // ── GET ALL ───────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
 
    // ── GET BY ID ─────────────────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapper.map(user, UserDTO.class);
    }
 
    // ── CREATE ────────────────────────────────────────────────────────────────
    public UserDTO create(UserDTO dto) {
        if (userRepository.existsByNom(dto.getNom())) {
            throw new ResourceAlreadyExistsException("User", "nom", dto.getNom());
        }
        User saved = userRepository.save(modelMapper.map(dto, User.class));
        return modelMapper.map(saved, UserDTO.class);
    }
 
    // ── UPDATE ────────────────────────────────────────────────────────────────
    public UserDTO update(Long id, UserDTO dto) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
 
        // On met à jour uniquement les champs modifiables
        existing.setNom(dto.getNom());
        existing.setBibliographie(dto.getBibliographie());
 
        return modelMapper.map(userRepository.save(existing), UserDTO.class);
    }
 
    // ── DELETE ────────────────────────────────────────────────────────────────
    public void delete(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(existing);
    }
}
 
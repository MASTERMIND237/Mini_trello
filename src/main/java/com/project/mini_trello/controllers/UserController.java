package com.project.mini_trello.controllers;

import com.project.mini_trello.assemblers.UserModelAssembler;
import com.project.mini_trello.DTO.UserDTO;
import com.project.mini_trello.services.UserService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
/**
 * @CrossOrigin ici est un filet de sécurité — la config globale
 * dans AppConfig.addCorsMappings() couvre déjà tous les /api/**
 * Mais si un jour ce controller est testé hors contexte global,
 * cette annotation garantit que CORS reste actif.
 */
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/users")
public class UserController {
 
    private final UserService        userService;
    private final UserModelAssembler assembler;
 
    public UserController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler   = assembler;
    }
 
    // ── GET ALL  →  GET /api/users ────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<UserDTO>>> getAll() {
        List<EntityModel<UserDTO>> users = userService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                users,
                linkTo(methodOn(UserController.class).getAll()).withSelfRel()
            )
        );
    }
 
    // ── GET BY ID  →  GET /api/users/{id} ────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(userService.findById(id)));
    }
 
    // ── CREATE  →  POST /api/users ────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<EntityModel<UserDTO>> create(@Valid @RequestBody UserDTO dto) {
        EntityModel<UserDTO> model = assembler.toModel(userService.create(dto));
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }
 
    // ── UPDATE  →  PUT /api/users/{id} ───────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(assembler.toModel(userService.update(id, dto)));
    }
 
    // ── DELETE  →  DELETE /api/users/{id} ────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

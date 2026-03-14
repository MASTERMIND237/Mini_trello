package com.project.mini_trello.controllers;

import com.project.mini_trello.assemblers.ProjetModelAssembler;
import com.project.mini_trello.DTO.ProjetDTO;
import com.project.mini_trello.services.ProjetService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/projets")
public class ProjetController {
 
    private final ProjetService        projetService;
    private final ProjetModelAssembler assembler;
 
    public ProjetController(ProjetService projetService, ProjetModelAssembler assembler) {
        this.projetService = projetService;
        this.assembler     = assembler;
    }
 
    // ── GET ALL  →  GET /api/projets ──────────────────────────────────────────
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ProjetDTO>>> getAll() {
        List<EntityModel<ProjetDTO>> projets = projetService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                projets,
                linkTo(methodOn(ProjetController.class).getAll()).withSelfRel()
            )
        );
    }
 
    // ── GET BY ID  →  GET /api/projets/{id} ──────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ProjetDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(projetService.findById(id)));
    }
 
    // ── GET BY USER  →  GET /api/projets/user/{userId} ───────────────────────
    @GetMapping("/user/{userId}")
    public ResponseEntity<CollectionModel<EntityModel<ProjetDTO>>> getByUser(
            @PathVariable Long userId) {
 
        List<EntityModel<ProjetDTO>> projets = projetService.findByUser(userId)
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                projets,
                linkTo(methodOn(ProjetController.class).getByUser(userId)).withSelfRel()
            )
        );
    }
 
    // ── CREATE  →  POST /api/projets/user/{userId} ────────────────────────────
    @PostMapping("/user/{userId}")
    public ResponseEntity<EntityModel<ProjetDTO>> create(
            @PathVariable Long userId,
            @Valid @RequestBody ProjetDTO dto) {
 
        EntityModel<ProjetDTO> model = assembler.toModel(projetService.create(userId, dto));
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }
 
    // ── UPDATE  →  PUT /api/projets/{id} ─────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ProjetDTO>> update(
            @PathVariable Long id,
            @Valid @RequestBody ProjetDTO dto) {
        return ResponseEntity.ok(assembler.toModel(projetService.update(id, dto)));
    }
 
    // ── DELETE  →  DELETE /api/projets/{id} ──────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        projetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
 

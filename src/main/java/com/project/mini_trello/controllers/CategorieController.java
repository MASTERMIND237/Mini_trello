package com.project.mini_trello.controllers;

import com.project.mini_trello.assemblers.CategorieModelAssembler;
import com.project.mini_trello.DTO.CategorieDTO;
import com.project.mini_trello.services.CategorieService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/categories")
public class CategorieController {
 
    private final CategorieService        categorieService;
    private final CategorieModelAssembler assembler;
 
    public CategorieController(CategorieService categorieService,
                               CategorieModelAssembler assembler) {
        this.categorieService = categorieService;
        this.assembler        = assembler;
    }
 
    // ── GET ALL  →  GET /api/categories ──────────────────────────────────────
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<CategorieDTO>>> getAll() {
        List<EntityModel<CategorieDTO>> categories = categorieService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                categories,
                linkTo(methodOn(CategorieController.class).getAll()).withSelfRel()
            )
        );
    }
 
    // ── GET BY ID  →  GET /api/categories/{id} ───────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<CategorieDTO>> getById(@PathVariable int id) {
        return ResponseEntity.ok(assembler.toModel(categorieService.findById(id)));
    }
 
    // ── CREATE  →  POST /api/categories ──────────────────────────────────────
    @PostMapping
    public ResponseEntity<EntityModel<CategorieDTO>> create(
            @Valid @RequestBody CategorieDTO dto) {
 
        EntityModel<CategorieDTO> model = assembler.toModel(categorieService.create(dto));
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }
 
    // ── UPDATE  →  PUT /api/categories/{id} ──────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<CategorieDTO>> update(
            @PathVariable int id,
            @Valid @RequestBody CategorieDTO dto) {
        return ResponseEntity.ok(assembler.toModel(categorieService.update(id, dto)));
    }
 
    // ── DELETE  →  DELETE /api/categories/{id} ───────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        categorieService.delete(id);
        return ResponseEntity.noContent().build();
    }
 
    // ════════════════════════════════════════════════════════════════════════
    //  Relations Many-to-Many
    // ════════════════════════════════════════════════════════════════════════
 
    // ── POST /api/categories/{id}/projets/{projetId} ──────────────────────────
    @PostMapping("/{id}/projets/{projetId}")
    public ResponseEntity<Void> assignerAuProjet(
            @PathVariable int id,
            @PathVariable Long projetId) {
        categorieService.assignerAuProjet(id, projetId);
        return ResponseEntity.noContent().build();
    }
 
    // ── DELETE /api/categories/{id}/projets/{projetId} ────────────────────────
    @DeleteMapping("/{id}/projets/{projetId}")
    public ResponseEntity<Void> retirerDuProjet(
            @PathVariable int id,
            @PathVariable Long projetId) {
        categorieService.retirerDuProjet(id, projetId);
        return ResponseEntity.noContent().build();
    }
 
    // ── POST /api/categories/{id}/taches/{tacheId} ───────────────────────────
    @PostMapping("/{id}/taches/{tacheId}")
    public ResponseEntity<Void> assignerATache(
            @PathVariable int id,
            @PathVariable int tacheId) {
        categorieService.assignerATache(id, tacheId);
        return ResponseEntity.noContent().build();
    }
 
    // ── DELETE /api/categories/{id}/taches/{tacheId} ─────────────────────────
    @DeleteMapping("/{id}/taches/{tacheId}")
    public ResponseEntity<Void> retirerDeTache(
            @PathVariable int id,
            @PathVariable int tacheId) {
        categorieService.retirerDeTache(id, tacheId);
        return ResponseEntity.noContent().build();
    }
}
 

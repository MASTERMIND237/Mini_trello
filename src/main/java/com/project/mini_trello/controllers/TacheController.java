package com.project.mini_trello.controllers;

import com.project.mini_trello.assemblers.TacheModelAssembler;
import com.project.mini_trello.DTO.TacheDTO;
import com.project.mini_trello.services.TacheService;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
 
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
 
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
@RestController
@RequestMapping("/api/taches")
public class TacheController {
 
    private final TacheService        tacheService;
    private final TacheModelAssembler assembler;
 
    public TacheController(TacheService tacheService, TacheModelAssembler assembler) {
        this.tacheService = tacheService;
        this.assembler    = assembler;
    }
 
    // ── GET ALL  →  GET /api/taches ───────────────────────────────────────────
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TacheDTO>>> getAll() {
        List<EntityModel<TacheDTO>> taches = tacheService.findAll()
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                taches,
                linkTo(methodOn(TacheController.class).getAll()).withSelfRel()
            )
        );
    }
 
    // ── GET BY ID  →  GET /api/taches/{id} ───────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TacheDTO>> getById(@PathVariable int id) {
        return ResponseEntity.ok(assembler.toModel(tacheService.findById(id)));
    }
 
    // ── GET BY PROJET  →  GET /api/taches/projet/{projetId} ──────────────────
    @GetMapping("/projet/{projetId}")
    public ResponseEntity<CollectionModel<EntityModel<TacheDTO>>> getByProjet(
            @PathVariable Long projetId) {
 
        List<EntityModel<TacheDTO>> taches = tacheService.findByProjet(projetId)
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                taches,
                linkTo(methodOn(TacheController.class).getByProjet(projetId)).withSelfRel()
            )
        );
    }
 
    // ── GET TACHES EN RETARD  →  GET /api/taches/retard ──────────────────────
    @GetMapping("/retard")
    public ResponseEntity<CollectionModel<EntityModel<TacheDTO>>> getTachesEnRetard() {
        List<EntityModel<TacheDTO>> taches = tacheService.findTachesEnRetard()
                .stream()
                .map(assembler::toModel)
                .toList();
 
        return ResponseEntity.ok(
            CollectionModel.of(
                taches,
                linkTo(methodOn(TacheController.class).getTachesEnRetard()).withSelfRel()
            )
        );
    }
 
    // ── CREATE  →  POST /api/taches/projet/{projetId} ────────────────────────
    @PostMapping("/projet/{projetId}")
    public ResponseEntity<EntityModel<TacheDTO>> create(
            @PathVariable Long projetId,
            @Valid @RequestBody TacheDTO dto) {
 
        EntityModel<TacheDTO> model = assembler.toModel(tacheService.create(projetId, dto));
        return ResponseEntity
                .created(model.getRequiredLink("self").toUri())
                .body(model);
    }
 
    // ── UPDATE  →  PUT /api/taches/{id} ──────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TacheDTO>> update(
            @PathVariable int id,
            @Valid @RequestBody TacheDTO dto) {
        return ResponseEntity.ok(assembler.toModel(tacheService.update(id, dto)));
    }
 
    // ── DELETE  →  DELETE /api/taches/{id} ───────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        tacheService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
 
package com.project.mini_trello.assemblers;

import com.project.mini_trello.controllers.TacheController;
import com.project.mini_trello.controllers.ProjetController;
import com.project.mini_trello.dto.TacheDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class TacheModelAssembler implements RepresentationModelAssembler<TacheDTO, EntityModel<TacheDTO>> {

    @Override
    public EntityModel<TacheDTO> toModel(TacheDTO dto) {
        return EntityModel.of(dto,

            // self : GET /api/taches/{id}
            linkTo(methodOn(TacheController.class).getById(dto.getId_Taches())).withSelfRel(),

            // collection tâches du projet : GET /api/projets/{projetId}/taches
            linkTo(methodOn(TacheController.class).getByProjet(dto.getProjetId())).withRel("taches-projet"),

            // projet parent : GET /api/projets/{projetId}
            linkTo(methodOn(ProjetController.class).getById(dto.getProjetId())).withRel("projet")
        );
    }
}

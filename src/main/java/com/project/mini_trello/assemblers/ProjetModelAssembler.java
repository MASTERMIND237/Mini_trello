package com.project.mini_trello.assemblers;

import com.project.mini_trello.controllers.ProjetController;
import com.project.mini_trello.controllers.TacheController;
import com.project.mini_trello.controllers.UserController;
import com.project.mini_trello.dto.ProjetDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ProjetModelAssembler implements RepresentationModelAssembler<ProjetDTO, EntityModel<ProjetDTO>> {

    @Override
    public EntityModel<ProjetDTO> toModel(ProjetDTO dto) {
        return EntityModel.of(dto,

            // self : GET /api/projets/{id}
            linkTo(methodOn(ProjetController.class).getById(dto.getId_Projet())).withSelfRel(),

            // collection : GET /api/projets
            linkTo(methodOn(ProjetController.class).getAll()).withRel("projets"),

            // user propriétaire : GET /api/users/{userId}
            linkTo(methodOn(UserController.class).getById(dto.getUserId())).withRel("user"),

            // tâches du projet : GET /api/projets/{id}/taches
            linkTo(methodOn(TacheController.class).getByProjet(dto.getId_Projet())).withRel("taches")
        );
    }
}
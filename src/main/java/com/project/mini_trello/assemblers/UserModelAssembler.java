package com.project.mini_trello.assemblers;

import com.project.mini_trello.controllers.UserController;
import com.project.mini_trello.controllers.ProjetController;
import com.project.mini_trello.dto.UserDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDTO, EntityModel<UserDTO>> {

    @Override
    public EntityModel<UserDTO> toModel(UserDTO dto) {
        return EntityModel.of(dto,

            // self : GET /api/users/{id}
            linkTo(methodOn(UserController.class).getById(dto.getId_User())).withSelfRel(),

            // collection : GET /api/users
            linkTo(methodOn(UserController.class).getAll()).withRel("users"),

            // projets du user : GET /api/users/{id}/projets
            linkTo(methodOn(ProjetController.class).getByUser(dto.getId_User())).withRel("projets")
        );
    }
}

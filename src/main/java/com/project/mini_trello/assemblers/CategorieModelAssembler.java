package com.project.mini_trello.assemblers;

import com.project.mini_trello.controllers.CategorieController;
import com.project.mini_trello.DTO.CategorieDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CategorieModelAssembler implements RepresentationModelAssembler<CategorieDTO, EntityModel<CategorieDTO>> {

    @Override
    public EntityModel<CategorieDTO> toModel(CategorieDTO dto) {
        return EntityModel.of(dto,

            // self : GET /api/categories/{id}
            linkTo(methodOn(CategorieController.class).getById(dto.getId_categorie())).withSelfRel(),

            // collection : GET /api/categories
            linkTo(methodOn(CategorieController.class).getAll()).withRel("categories")
        );
    }
}

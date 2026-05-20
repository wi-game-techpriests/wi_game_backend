package agh.edu.pl.demo.util.dto;

import agh.edu.pl.demo.model.ConnectionsCategory;

import java.util.List;

public record ConnectionsDTO(CategoryDTO categoryA,
                             CategoryDTO categoryB,
                             CategoryDTO categoryC,
                             CategoryDTO categoryD) {
}

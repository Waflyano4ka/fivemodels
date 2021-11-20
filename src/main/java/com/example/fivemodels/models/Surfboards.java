package com.example.fivemodels.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Surfboards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Поле обязательно для заполнения")
    private String name, model, brand;
    @NotNull(message = "Поле обязательно для заполнения")
    @Min(value = 0, message = "Значение не может быть отрицательным")
    private Double size_height, size_width;

    public Surfboards(String name, String model, String brand, Double size_height, Double size_width) {
        this.name = name;
        this.model = model;
        this.brand = brand;
        this.size_height = size_height;
        this.size_width = size_width;
    }

    public Surfboards() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getSize_height() {
        return size_height;
    }

    public void setSize_height(Double size_height) {
        this.size_height = size_height;
    }

    public Double getSize_width() {
        return size_width;
    }

    public void setSize_width(Double size_width) {
        this.size_width = size_width;
    }
}

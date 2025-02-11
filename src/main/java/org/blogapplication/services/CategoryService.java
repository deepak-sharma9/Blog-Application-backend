package org.blogapplication.services;

import org.blogapplication.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    //Create
    CategoryDto createCategory(CategoryDto categoryDto);

    //Update
    CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);

    //Delete
    void deleteCategory(Integer categoryId);

    //Get
    CategoryDto getCategory(Integer CategoryId);

    //Get All
    List<CategoryDto> getCategories();



}

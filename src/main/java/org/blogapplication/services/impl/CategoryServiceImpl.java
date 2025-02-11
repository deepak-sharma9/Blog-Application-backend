package org.blogapplication.services.impl;

import org.blogapplication.entities.Category;
import org.blogapplication.exceptions.ResourceNotFoundException;
import org.blogapplication.mappers.CategoryMapper;
import org.blogapplication.payloads.CategoryDto;
import org.blogapplication.repositories.CategoryRepository;
import org.blogapplication.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryMapper.mapToCategory(categoryDto);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.mapToCategoryDto(savedCategory);
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",categoryId));
        return categoryMapper.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> category = categoryRepository.findAll();
        return category.stream().map(categories ->categoryMapper.mapToCategoryDto(categories)).toList();
    }
}

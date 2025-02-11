package org.blogapplication.mappers;


import org.blogapplication.entities.Category;
import org.blogapplication.payloads.CategoryDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Category mapToCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto mapToCategoryDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }

}

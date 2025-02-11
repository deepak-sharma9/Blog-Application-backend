package org.blogapplication.controllers;


import jakarta.validation.Valid;
import org.blogapplication.exceptions.ApiResponse;
import org.blogapplication.payloads.CategoryDto;
import org.blogapplication.payloads.UserDto;
import org.blogapplication.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {


    private CategoryService categoryService;
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Create API
    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    // Update API
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@Valid @PathVariable Integer id, @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.updateCategory(id,categoryDto);
        return ResponseEntity.ok(categoryDto1);
    }

    //Delete API
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully",true),HttpStatus.OK);
    }

    //Get API
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id){
     CategoryDto categoryDto = categoryService.getCategory(id);
     return ResponseEntity.ok(categoryDto);
    }

    //Get All API
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getCategories(){
        List<CategoryDto> categoryDtoList = categoryService.getCategories();
        return ResponseEntity.ok(categoryDtoList);
    }


}

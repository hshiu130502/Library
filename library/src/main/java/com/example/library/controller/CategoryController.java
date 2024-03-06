package com.example.library.controller;

import com.example.library.entity.Category;
import com.example.library.more.Sale;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getAllCategory")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategory();
    }
    @GetMapping("/getCategory/{count}/{size}")
    public List<Category> getCategory(@PathVariable int count, @PathVariable int size){
        return categoryService.getCategory(count, size);
    }
    @GetMapping("/getCategoryByTitle/{title}/{count}/{size}")
    public List<Category> getCategoryByTitle(@PathVariable String title, @PathVariable int count, @PathVariable int size){
        return categoryService.getCategoryByTitle(title, count, size);
    }


    @PutMapping("/updateCategorySale")
    public Category updateCategorySale(@RequestBody Sale sale){
        return categoryService.findFirstById(sale.getId(), sale.getSale());
    }


    @PostMapping("/addNewCategory")
    public Category addNewCategory(@RequestBody Category category){
        return categoryService.addNewCategory(category);
    }
}

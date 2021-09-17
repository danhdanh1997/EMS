package com.xuandanh.ems.restapi;

import com.xuandanh.ems.common.ApiResponse;
import com.xuandanh.ems.domain.Category;
import com.xuandanh.ems.service.CategoryService;
import com.xuandanh.ems.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.listCategories();
        return new ResponseEntity<List<Category>>(body, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
        if (Helper.notNull(categoryService.readCategory(category.getCategoryName()))) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category already exists"), HttpStatus.CONFLICT);
        }
        categoryService.createCategory(category);
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "created the category"), HttpStatus.CREATED);
    }

    //TODO create an UPDATE method Giridhar
    @PostMapping("/update/{categoryID}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryID") Integer categoryID, @Valid @RequestBody Category category) {
        // Check to see if the category exists.
        if (Helper.notNull(categoryService.readCategory(categoryID))) {
            // If the category exists then update it.
            categoryService.updateCategory(categoryID, category);
            return new ResponseEntity<ApiResponse>(new ApiResponse(true, "updated the category"), HttpStatus.OK);
        }

        // If the category doesn't exist then return a response of unsuccessful.
        return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
    }
}

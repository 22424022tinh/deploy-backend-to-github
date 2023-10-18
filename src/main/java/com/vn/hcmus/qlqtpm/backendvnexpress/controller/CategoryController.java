package com.vn.hcmus.qlqtpm.backendvnexpress.controller;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.CategoryDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.CategoryPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.response.APIResponseSuccess;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.CategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/category")
public class CategoryController {

    public static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private CategoryService categoryService;

    @GetMapping(value = "")
    public ResponseEntity<APIResponseSuccess<List<CategoryDTO>>> getAllCategory() {
        return ResponseEntity.ok(new APIResponseSuccess<>(categoryService.getAllCategory()));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<CategoryDTO>> getCategoryById(Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(categoryService.getCategoryById(id)));
    }

    @PostMapping(value = "")
    public ResponseEntity<APIResponseSuccess<String>> createCategory(@RequestBody CategoryPayload payload) {
        return ResponseEntity.ok(new APIResponseSuccess<>(categoryService.createCategory(payload)));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<String>> updateCategory(@RequestBody CategoryPayload payload, Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(categoryService.updateCategory(payload, id)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<String>> deleteCategory(Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(categoryService.deleteCategory(id)));
    }
}

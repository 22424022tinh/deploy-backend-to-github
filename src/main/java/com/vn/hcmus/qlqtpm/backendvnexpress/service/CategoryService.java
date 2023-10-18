package com.vn.hcmus.qlqtpm.backendvnexpress.service;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.CategoryDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.CategoryPayload;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCategory();

    CategoryDTO getCategoryById(Long id);

    String createCategory(CategoryPayload payload);

    String updateCategory(CategoryPayload payload, Long id);

    String deleteCategory(Long id);
}

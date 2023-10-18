package com.vn.hcmus.qlqtpm.backendvnexpress.service.impl;

import com.vn.hcmus.qlqtpm.backendvnexpress.entity.CategoryEntity;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.HttpClientErrorException;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.CategoryDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.CategoryPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.repository.CategoryRepository;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper mapper;

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> categoryEntities = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = categoryEntities.stream().map((item) -> {
            CategoryDTO categoryDTO = CategoryDTO
                    .builder()
                    .categoryId(item.getCategoryId())
                    .title(item.getTitle())
                    .slug(item.getSlug())
                    .build();
            return categoryDTO;
        }).collect(Collectors.toList());
        return categoryDTOs;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
            if (categoryEntity.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "categoryId không tồn tại!");
            }
            CategoryDTO categoryDTO = CategoryDTO
                    .builder()
                    .categoryId(categoryEntity.get().getCategoryId())
                    .title(categoryEntity.get().getTitle())
                    .slug(categoryEntity.get().getSlug())
                    .build();

            return categoryDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String createCategory(CategoryPayload payload) {
        try {
            CategoryEntity categoryEntity = CategoryEntity.builder()
                    .title(payload.getTitle())
                    .slug(payload.getSlug())
                    .createdBy(payload.getUserId())
                    .updatedBy(payload.getUserId())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .build();
            categoryRepository.save(categoryEntity);
            return "Created success";
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String updateCategory(CategoryPayload payload, Long id) {
        try {
            Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
            if (categoryEntity.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "userId không tồn tại!");
            }
            categoryEntity.get().setTitle(payload.getTitle());
            categoryEntity.get().setSlug(payload.getSlug());
            categoryEntity.get().setUpdatedBy(payload.getUserId());
            categoryEntity.get().setUpdatedAt(new Date());

            categoryRepository.save(categoryEntity.get());

            return "Update success";
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deleteCategory(Long id) {
        try {
            categoryRepository.deleteById(id);

            return "Delete success";
        } catch (Exception e) {
            throw e;
        }
    }
}

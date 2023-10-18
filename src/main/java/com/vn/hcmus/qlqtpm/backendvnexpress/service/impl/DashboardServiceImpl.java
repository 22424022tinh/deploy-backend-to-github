package com.vn.hcmus.qlqtpm.backendvnexpress.service.impl;

import com.vn.hcmus.qlqtpm.backendvnexpress.entity.CategoryEntity;
import com.vn.hcmus.qlqtpm.backendvnexpress.entity.PostEntity;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.DashboardDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.PostDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.repository.CategoryRepository;
import com.vn.hcmus.qlqtpm.backendvnexpress.repository.PostRepository;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.DashboardService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    ModelMapper mapper;

    @Override
    public List<DashboardDTO> getDashboard() {
        List<CategoryEntity> list = categoryRepository.findAll();
        List<PostEntity> postEntityList = postRepository.findPostEntitiesByHome();
        List<DashboardDTO> dashboardDTOList = list.stream().map((parent) -> {
            List<PostEntity> postEntities = postEntityList.stream().filter((children -> children.getCategoryEntity().getCategoryId() == parent.getCategoryId())).collect(Collectors.toList());
            return DashboardDTO.builder()
                    .title(parent.getTitle())
                    .slug(parent.getSlug())
                    .categoryId(parent.getCategoryId())
                    .postDTOList(List.of(mapper.map(postEntities, PostDTO[].class)))
                    .build();
        }).filter((dto) -> dto.getPostDTOList().size() > 0).collect(Collectors.toList());
        return dashboardDTOList;
    }
}

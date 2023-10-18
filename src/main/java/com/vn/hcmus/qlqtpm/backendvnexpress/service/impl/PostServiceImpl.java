package com.vn.hcmus.qlqtpm.backendvnexpress.service.impl;

import com.vn.hcmus.qlqtpm.backendvnexpress.constants.STATUS;
import com.vn.hcmus.qlqtpm.backendvnexpress.entity.CategoryEntity;
import com.vn.hcmus.qlqtpm.backendvnexpress.entity.PostEntity;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.BadRequestException;
import com.vn.hcmus.qlqtpm.backendvnexpress.exception.HttpClientErrorException;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.PaginationDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.PostDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.PostPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.repository.CategoryRepository;
import com.vn.hcmus.qlqtpm.backendvnexpress.repository.PostRepository;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public PaginationDTO<PostDTO> getAllPost(Pageable pageable) {
        try {
            Page<PostEntity> postEntityPage = postRepository.findAll(pageable);
            List<PostDTO> productDTOList = postEntityPage.getContent().stream().map((productEntity -> mapper.map(productEntity, PostDTO.class))).collect(Collectors.toList());
            return PaginationDTO
                    .<PostDTO>builder()
                    .list(productDTOList)
                    .totalItem(postEntityPage.getTotalElements())
                    .isNext(postEntityPage.hasNext())
                    .isPrevious(postEntityPage.hasPrevious())
                    .totalPage((long) postEntityPage.getTotalPages())
                    .size((long) pageable.getPageSize())
                    .totalItemPerPage((long) postEntityPage.getNumberOfElements())
                    .currentPage((long) (postEntityPage.getPageable().getPageNumber() + 1))
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public PostDTO getPostById(Long id) {
        try {
            Optional<PostEntity> postEntity = postRepository.findById(id);
            if (postEntity.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "postId không tồn tại!");
            }
            PostDTO postDTO = PostDTO
                    .builder()
                    .title(postEntity.get().getTitle())
                    .slug(postEntity.get().getSlug())
                    .summary(postEntity.get().getSummary())
                    .content(postEntity.get().getContent())
                    .view(postEntity.get().getView())
                    .createdAt(postEntity.get().getCreatedAt())
                    .createdBy(postEntity.get().getCreatedBy())
                    .build();

            return postDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String createPost(PostPayload payload) {
        Optional<CategoryEntity> optional = categoryRepository.findCategoryEntityByCategoryId(payload.getCategoryId());
        if (optional.isEmpty()) {
            throw new BadRequestException("CategoryId is not found");
        }
        try {
            PostEntity postEntity = PostEntity.builder()
                    .title(payload.getTitle())
                    .slug(payload.getSlug())
                    .summary(payload.getSummary())
                    .content(payload.getContent())
                    .view(0)
                    .status(STATUS.PENDING)
                    .createdBy(payload.getUserId())
                    .updatedBy(payload.getUserId())
                    .createdAt(new Date())
                    .updatedAt(new Date())
                    .categoryEntity(optional.get())
                    .build();
            postRepository.save(postEntity);
            return "Created success";
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String updatePost(PostPayload payload, Long id) {
        try {
            Optional<PostEntity> postEntity = postRepository.findById(id);
            if (postEntity.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "postId không tồn tại!");
            }
            postEntity.get().setTitle(payload.getTitle());
            postEntity.get().setSummary(payload.getSummary());
            postEntity.get().setContent(payload.getContent());
            postEntity.get().setSlug(payload.getSlug());
            postEntity.get().setUpdatedBy(payload.getUserId());
            postEntity.get().setUpdatedAt(new Date());

            postRepository.save(postEntity.get());

            return "Update success";
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deletePost(Long id) {
        try {
            postRepository.deleteById(id);

            return "Delete success";
        } catch (Exception e) {
            throw e;
        }
    }
}

package com.vn.hcmus.qlqtpm.backendvnexpress.service;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.PaginationDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.PostDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.PostPayload;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PaginationDTO<PostDTO> getAllPost(Pageable pageable);

    PostDTO getPostById(Long id);

    String createPost(PostPayload payload);

    String updatePost(PostPayload payload, Long id);

    String deletePost(Long id);
}

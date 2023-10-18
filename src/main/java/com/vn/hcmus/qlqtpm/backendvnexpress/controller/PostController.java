package com.vn.hcmus.qlqtpm.backendvnexpress.controller;

import com.vn.hcmus.qlqtpm.backendvnexpress.model.PaginationDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.model.PostDTO;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.request.PostPayload;
import com.vn.hcmus.qlqtpm.backendvnexpress.payload.response.APIResponseSuccess;
import com.vn.hcmus.qlqtpm.backendvnexpress.service.PostService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/post")
public class PostController {
    public static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private PostService postService;

    @GetMapping(value = "")
    public ResponseEntity<APIResponseSuccess<PaginationDTO<PostDTO>>> getAllPost(
            @RequestParam(name = "page", defaultValue = "1", required = false) Long page) {
        try {
            Sort sortable = Sort.by(Sort.Order.asc("createdAt"));
            Pageable pageable = PageRequest.of(Math.toIntExact(page - 1), Math.toIntExact(10), sortable);
            return ResponseEntity.ok(new APIResponseSuccess<>(postService.getAllPost(pageable)));
        } catch (Exception e) {
            throw e;
        }

    }

    @GetMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<PostDTO>> getPostById(Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(postService.getPostById(id)));
    }

    @PostMapping(value = "")
    public ResponseEntity<APIResponseSuccess<String>> createPost(@RequestBody PostPayload payload) {
        return ResponseEntity.ok(new APIResponseSuccess<>(postService.createPost(payload)));
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<String>> updatePost(@RequestBody PostPayload payload, Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(postService.updatePost(payload, id)));
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<APIResponseSuccess<String>> deletePost(Long id) {
        return ResponseEntity.ok(new APIResponseSuccess<>(postService.deletePost(id)));
    }
}

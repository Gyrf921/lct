package com.example.lct.service.impl;

import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Department;
import com.example.lct.model.Post;
import com.example.lct.repository.PostRepository;
import com.example.lct.web.dto.request.admin.PostsDTO;
import com.example.lct.web.dto.request.admin.obj.PostDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostServiceImpl {

    private final PostRepository postRepository;

    private final DepartmentServiceImpl departmentService;


    public List<Post> saveAllPostForCompany(Long companyId, PostsDTO postsDTO) {

        List<Post> posts = new ArrayList<>();
        Department department;

        for (PostDTO postDTO : postsDTO.getPostDTOList()) {
            department = departmentService.getDepartmentByNameAndCompanyId(companyId, postDTO.getDepartmentName());

            posts.add(Post.builder()
                    .companyId(companyId)
                    .department(department)
                    .name(postDTO.getName()).build());
        }

        return postRepository.saveAll(posts);
    }

    public Post getPostByNameAndCompanyId(Long companyId, String postName) {
        log.info("[getPostByNameAndCompanyId] >> companyId: {}, postName: {}", companyId, postName);

        Post post = postRepository.findByNameAndCompanyId(postName, companyId)
                .orElseThrow(() -> {
                    log.error("Post not found by this name on your company :: :{} ", postName);
                    return new ResourceNotFoundException("Post not found by this name on your company :: " + postName);
                });

        log.info("[getPostByNameAndCompanyId] << result: {}", post);

        return post;
    }

    public List<Post> createBasePostForCompany(Long companyId) {
        log.info("[PostService|createBasePostForCompany] post with name: 'none'");
        return postRepository.saveAll(List.of(Post.builder()
                .companyId(companyId)
                .department(departmentService.getDepartmentByNameAndCompanyId(companyId, "none"))
                .name("none").build()));
    }
}

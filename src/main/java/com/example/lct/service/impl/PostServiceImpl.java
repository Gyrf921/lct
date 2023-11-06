package com.example.lct.service.impl;

import com.example.lct.model.Post;
import com.example.lct.model.status.Department;
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
        //TODO можно убрать запрос и сделать передачу сразу id
        for (PostDTO postDTO : postsDTO.getPostDTOList()){
            department = departmentService.getDepartmentByNameAndCompanyId(postDTO.getDepartmentName(), companyId);

            posts.add(Post.builder()
                    .companyId(companyId)
                    .department(department)
                    .name(postDTO.getName()).build());
        }

        return postRepository.saveAll(posts);
    }

    public Post getPostByNameAndCompanyId(Long companyId, String postName) {
        return null;
    }
}

package com.example.lct.web.dto.response;

import com.example.lct.model.Post;
import com.example.lct.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePersonalityResponseDTO {

    private Long employeeId;

    private String imagePath;

    private String email;

    private Post post;

    private List<Role> roles;

    private String name;

    private String phone;

    private String socialNetwork;

    private String city;

    private Long account;

    private Integer countCompletedTask;
}

package com.example.lct.web.dto.response;

import com.example.lct.model.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  EmployeePersonalityResponseDTO {

    private Long employeeId;

    private String imagePath;

    private String email;

    private Post post;

    private String name;

    private String phone;

    private String socialNetwork;

    private String city;

    private Long account;
}

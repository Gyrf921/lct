package com.example.lct.web.dto.response;

import com.example.lct.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmployeeTeamResponseDTO {

    private Long employeeId;

    private String imagePath;

    private String name;

    private String city;

    private String postName;

    private List<Role> roles;

    private String network;

    private String email;

    private String phone;
}

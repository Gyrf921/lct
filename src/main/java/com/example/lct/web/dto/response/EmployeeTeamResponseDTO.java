package com.example.lct.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class EmployeeTeamResponseDTO {

    private Long employeeId;

    private String name;

    private String city;

    private String postName;

    private String network;

    private String email;

    private String phone;
}

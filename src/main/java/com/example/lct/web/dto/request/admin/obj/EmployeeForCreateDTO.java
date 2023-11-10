package com.example.lct.web.dto.request.admin.obj;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeForCreateDTO {

    private String name;

    private String email;

    private String postName;

    private String roleName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String curatorEmail;
}

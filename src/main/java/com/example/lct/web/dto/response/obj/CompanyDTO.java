package com.example.lct.web.dto.response.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO {

    private String name;

    private String description;

    private String email;

    private String phone;

    private String website;

}

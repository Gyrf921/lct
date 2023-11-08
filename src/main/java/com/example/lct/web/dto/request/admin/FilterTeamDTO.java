package com.example.lct.web.dto.request.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilterTeamDTO {

    private String departmentName;

    private String postName;

    private String cityName;

    private String employeeName;

}

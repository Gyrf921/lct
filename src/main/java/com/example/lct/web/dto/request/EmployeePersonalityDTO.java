package com.example.lct.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeePersonalityDTO {

    private String imagePath;

    private String name;

    private String phone;

    private String socialNetwork;

    private String city;
}

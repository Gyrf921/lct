package com.example.lct.web.dto.request.admin.obj;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

    private String imagePath;

    private String name;

    private String description;

    private Long cost;
}

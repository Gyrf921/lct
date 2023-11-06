package com.example.lct.web.dto.response;

import com.example.lct.web.dto.CompanyDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyAndJwtResponseDTO {

    private CompanyDTO companyDTO;

    private JwtResponseDTO jwtResponseDTO;


}

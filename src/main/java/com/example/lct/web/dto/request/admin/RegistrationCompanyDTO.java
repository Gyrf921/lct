package com.example.lct.web.dto.request.admin;

import com.example.lct.web.dto.response.obj.CompanyDTO;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationCompanyDTO {

    private CompanyDTO companyDTO;

    private RegistrationUserDTO registrationAdminDTO;

}

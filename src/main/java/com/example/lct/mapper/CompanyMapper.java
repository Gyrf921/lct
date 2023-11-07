package com.example.lct.mapper;

import com.example.lct.model.Company;
import com.example.lct.web.dto.response.obj.CompanyDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CompanyMapper {

    Company companyDTOToCompany(CompanyDTO requestDTO);

    CompanyDTO companyToCompanyDTO(Company requestDTO);

}

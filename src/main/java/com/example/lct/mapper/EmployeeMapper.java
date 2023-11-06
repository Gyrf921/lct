package com.example.lct.mapper;

import com.example.lct.model.Employee;
import com.example.lct.web.dto.request.RegistrationUserDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface EmployeeMapper {

    Employee registrationUserDTOToEmployee(RegistrationUserDTO requestDTO);


}

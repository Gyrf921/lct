package com.example.lct.web.controller.notyet;


import com.example.lct.service.AdminService;
import com.example.lct.util.UserPrincipalUtils;
import com.example.lct.web.dto.request.admin.RegistrationCompanyDTO;
import com.example.lct.web.dto.response.CompanyAndJwtResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/general")
public class GeneralController {


    /* TODO
     * редактирование ЛК
     * просмотр базы знаний
     * добавление стажёров с привязкой к HR
     * вывод всех задач для проверки
     * проверка задач привязанных к нему стажеров (смена статуса)
     * */
}

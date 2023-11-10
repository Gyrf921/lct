package com.example.lct.web.dto.response.obj;

import com.example.lct.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponseDTO {
    private String token;
    private List<Role> roles;
}

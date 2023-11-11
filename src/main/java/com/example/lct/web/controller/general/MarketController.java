package com.example.lct.web.controller.general;

import com.example.lct.model.Product;
import com.example.lct.service.impl.ProductServiceImpl;
import com.example.lct.util.UserPrincipalUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/market")
public class MarketController {

    private final ProductServiceImpl productService;
    private final UserPrincipalUtils userPrincipalUtils;

    @Operation(summary = "get all products")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(Principal principal) {
        return ResponseEntity.ok().body(productService.getAllProduct(userPrincipalUtils.getCompanyByUserPrincipal(principal)));
    }

    @Operation(summary = "buy product in market by id")
    @PostMapping("/products/{productId}")
    public ResponseEntity<Boolean> buyProduct(@PathVariable(value = "productId") Long productId,
                                              Principal principal) {
        productService.buyProductForEmployeeAndNotifyCurator(productId, userPrincipalUtils.getEmployeeByUserPrincipal(principal));

        return ResponseEntity.ok().body(true);
    }
}

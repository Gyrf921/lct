package com.example.lct.web.controller.general;

import com.example.lct.model.Product;
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

    @Operation(summary = "get all products")
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(Principal principal) {

        return ResponseEntity.ok().body(null);
    }

    @Operation(summary = "buy product in market by id")
    @PostMapping("/products/{productId}")
    public ResponseEntity<Boolean> buyProduct(@PathVariable(value = "productId") Long productId,
                                              Principal principal) {

        return ResponseEntity.ok().body(null);
    }
}

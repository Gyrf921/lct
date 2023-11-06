package com.example.lct.service.impl;

import com.example.lct.model.status.Product;
import com.example.lct.model.status.Question;
import com.example.lct.repository.ProductRepository;
import com.example.lct.repository.QuestionRepository;
import com.example.lct.web.dto.request.admin.ProductsDTO;
import com.example.lct.web.dto.request.admin.QuestionsDTO;
import com.example.lct.web.dto.request.admin.obj.ProductDTO;
import com.example.lct.web.dto.request.admin.obj.QuestionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;


    public List<Product> saveAllProductsForCompany(Long companyId, ProductsDTO productsDTO) {
        List<Product> products = new ArrayList<>();

        for (ProductDTO productDTO: productsDTO.getProductsDTO()) {
            //TODO mapper
            products.add(Product.builder().companyId(companyId)
                            .imagePath(productDTO.getImagePath())
                            .name(productDTO.getName())
                            .description(productDTO.getDescription())
                            .cost(productDTO.getCost()).build());
        }

        List<Product> savedProducts = productRepository.saveAll(products);

        log.info("[saveAllProductsForCompany] << result: {}", savedProducts);

        return savedProducts;
    }

}

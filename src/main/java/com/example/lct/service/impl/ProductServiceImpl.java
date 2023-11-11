package com.example.lct.service.impl;

import com.example.lct.exception.InsufficientFundsException;
import com.example.lct.exception.ResourceNotFoundException;
import com.example.lct.model.Company;
import com.example.lct.model.Employee;
import com.example.lct.model.Product;
import com.example.lct.model.enumformodel.HistoryType;
import com.example.lct.repository.ProductRepository;
import com.example.lct.service.EmailService;
import com.example.lct.service.EmployeeService;
import com.example.lct.service.HistoryService;
import com.example.lct.web.dto.request.admin.ProductsDTO;
import com.example.lct.web.dto.request.admin.obj.ProductDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final HistoryService historyService;

    private final ProductRepository productRepository;

    private final EmployeeService employeeService;

    private final EmailService emailService;

    public Boolean buyProductForEmployeeAndNotifyCurator(Long productId, Employee buyer) {

        Product product = getProductById(productId);

        if (buyer.getAccount() < product.getCost()) {
            throw new InsufficientFundsException("You don't have enough money to buy this product");
        }

        long employeeAccount = buyer.getAccount() - product.getCost();

        buyer.setAccount(employeeAccount);

        employeeService.saveEmployee(buyer);

        historyService.createHistoryActionOther(buyer, HistoryType.OTHER, "Покупка товара: " + product.getProductId());

        if(employeeService.getEmployeeById(buyer.getCuratorId()) != null){
            Employee curator = employeeService.getEmployeeById(buyer.getCuratorId());
            notifyCuratorAboutBuyingProduct(curator.getEmail(), buyer, product);
        }
        return true;
    }

    private void notifyCuratorAboutBuyingProduct(String emailCurator, Employee buyer, Product product) {
        emailService.sendBuyEmail(emailCurator, buyer, product);
    }

    public List<Product> saveAllProductsForCompany(Long companyId, ProductsDTO productsDTO) {
        List<Product> products = new ArrayList<>();

        for (ProductDTO productDTO : productsDTO.getProductsDTO()) {
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

    public Product getProductById(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found by this id :{} ", productId);
                    return new ResourceNotFoundException("Product not found by this id :: " + productId);
                });

        log.info("[getProductById] << result: {}", product);

        return product;
    }

    public List<Product> getAllProduct(Company company) {
        return productRepository.findAllByCompanyId(company.getCompanyId());
    }
}

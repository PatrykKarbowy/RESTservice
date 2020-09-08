package com.example.RestAPIApp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
@RequiredArgsConstructor
public class ProductAPI {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Product product){
        return ResponseEntity.ok(productService.save(product));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id){
        Optional<Product> stock = productService.findById(id);
        if(!stock.isPresent()){
            log.error("Id " + id + " does not exist");
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(stock.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        if (!productService.findById(id).isPresent()){
            log.error("Id " + id + " does not exist");
            ResponseEntity.badRequest().build();
        }
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}

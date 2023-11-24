package ecommerce.api.controllers;
import ecommerce.api.entities.Product.*;
import ecommerce.api.services.Product.ProductService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/save")
    @Transactional
    public ResponseEntity<ProductShowData> saveProduct(@RequestBody @Valid ProductSaveData productSaveData){
        var Product = this.productService.saveProduct(productSaveData);
        return ResponseEntity.ok(Product);
    }
    
    @GetMapping("/id/{id}")
    public ResponseEntity<ProductShowData> findProductByIdByActive(@PathVariable Long id) {
        try {
            var Product = this.productService.findProductById(true, id);
            return ResponseEntity.ok(Product);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/inactive/id/{id}")
    public ResponseEntity<ProductShowData> findProductByIdByInactive(@PathVariable Long id) {
        try {
            var Product = this.productService.findProductById(false, id);
            return ResponseEntity.ok(Product);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductShowData> findProductByNameByActive(@PathVariable String name) {
        try {
            var Product = this.productService.findProductByName(true, name);
            return ResponseEntity.ok(Product);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/inactive/name/{name}")
    public ResponseEntity<ProductShowData> findProductByNameByInactive(@PathVariable String name) {
        try {
            var Product = this.productService.findProductByName(false, name);
            return ResponseEntity.ok(Product);
        } catch (Exception e) {  return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @GetMapping("/all")
    ResponseEntity<Page<ProductShowData>> findProductListByActive (@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(productService.findAllProductsByActive(true, paging));
    }

    @GetMapping("/inactive/all")
    ResponseEntity<Page<ProductShowData>> findProductListByInactive (@PageableDefault(size = 3) Pageable paging) {
        return ResponseEntity.ok(productService.findAllProductsByActive(false, paging));
    }


    @PutMapping("/update/{id}")
    @Transactional
    public ResponseEntity<ProductShowData> updateProduct(@RequestBody @Valid ProductUpdateData ProductUpdateData){
        var result = this.productService.updateProduct(ProductUpdateData);
        if (result != null) { return ResponseEntity.ok(result);}
        else {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
    }

    @PutMapping("/hide/{id}")
    @Transactional
    public ResponseEntity<ProductShowData> hideProduct(@PathVariable Long id){
        var result = this.productService.hideProduct(id);
        if (result) { return ResponseEntity.ok().build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @PutMapping("/reactive/{id}")
    @Transactional
    public ResponseEntity<ProductShowData> reactiveProduct(@PathVariable Long id){
        var result = this.productService.reactiveProduct(id);
        if (result) { return ResponseEntity.ok().build();}
        else {return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> toggleProduct(@PathVariable Long id){
        var toggledProduct = productService.toggleProduct(id);
        if (toggledProduct) { return ResponseEntity.ok(toggledProduct);
        }  else { return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


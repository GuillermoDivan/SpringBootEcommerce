package ecommerce.api.services.Product;
import ecommerce.api.entities.Product.ProductSaveData;
import ecommerce.api.entities.Product.ProductShowData;
import ecommerce.api.entities.Product.ProductUpdateData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface
ProductService {
    ProductShowData saveProduct(ProductSaveData ProductSaveData);
    ProductShowData findProductById(Boolean active, Long id) throws EntityNotFoundException;
    ProductShowData findProductByName(Boolean active, String name) throws EntityNotFoundException;
    Page<ProductShowData> findAllProductsByActive(Boolean active, Pageable paging);
    ProductShowData updateProduct(ProductUpdateData ProductUpdateData) throws EntityNotFoundException;
    boolean hideProduct(Long id) throws EntityNotFoundException;
    boolean reactiveProduct(Long id) throws EntityNotFoundException;
    boolean toggleProduct(Long id) throws EntityNotFoundException;
    boolean deleteProduct(Long id) throws EntityNotFoundException;
}


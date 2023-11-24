package ecommerce.api.services.Product;
import ecommerce.api.entities.Product.*;
import ecommerce.api.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public ProductShowData saveProduct(ProductSaveData ProductSaveData) {
        Product Product = new Product(ProductSaveData);
        this.productRepository.save(Product);
        return new ProductShowData(Product);
    }

    @Override
    public ProductShowData findProductById(Boolean active, Long id) throws EntityNotFoundException {
        var productShowData = this.productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return new ProductShowData(productShowData);
    }

    @Override
    public ProductShowData findProductByName(Boolean active, String name) throws EntityNotFoundException {
        var productShowData = this.productRepository.findByNameAndActive(name, active).orElseThrow(EntityNotFoundException::new);
        return new ProductShowData(productShowData);
    }

    @Override
    public Page<ProductShowData> findAllProductsByActive(Boolean active, Pageable paging) {
        return this.productRepository.findAllProductByActive(active, paging).map(ProductShowData::new);
    }

    @Override
    public ProductShowData updateProduct(ProductUpdateData ProductUpdateData) throws EntityNotFoundException {
        var Product = this.productRepository.findById(ProductUpdateData.id()).orElseThrow(EntityNotFoundException::new);
        if (Product.isActive()) {
            if (ProductUpdateData.name() != null && !ProductUpdateData.name().isEmpty()) {
                Product.setName(ProductUpdateData.name());
            }
            /*if (ProductUpdateData.price() != null) {
                Product.setPrice(ProductUpdateData.price());
            }*/
            if (ProductUpdateData.description() != null && !ProductUpdateData.description().isEmpty()) {
                Product.setDescription(ProductUpdateData.description());
            }
            this.productRepository.save(Product);
            return new ProductShowData(Product);
        } else {
            return null;
        }
    }

    @Override
    public boolean hideProduct(Long id) throws EntityNotFoundException {
        var Product = this.productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (Product.isActive()) {
            Product.setActive(false);
            this.productRepository.save(Product);
            return true;
        } else { return false; }
    }

    @Override
    public boolean reactiveProduct(Long id) throws EntityNotFoundException {
        var Product = this.productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (Product.isActive()) {
            Product.setActive(true);
            this.productRepository.save(Product);
            return true;
        } else { return false; }
    }

    @Override
    public boolean toggleProduct(Long id) throws EntityNotFoundException {
        var product = this.productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (product != null) {
            product.setActive(!product.isActive());
            this.productRepository.save(product);
            return true;
        } else { return false; }
    }

    @Override
    public boolean deleteProduct(Long id) throws EntityNotFoundException {
        var client = this.productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (client != null) {
            this.productRepository.delete(client);
            return true;
        } else { return false; }
    }
}

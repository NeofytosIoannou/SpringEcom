package com.neo.springecom.repo;

import com.neo.springecom.model.Product;
import com.neo.springecom.model.dto.ProductSearchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo  extends JpaRepository<Product,Integer> {

    @Query("""
SELECT new com.neo.springecom.dto.ProductSearchDto(
  p.id, p.name, p.description, p.brand, p.price, p.category, p.productAvailable, p.stockQuantity
)
FROM Product p
WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
   OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))
   OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :keyword, '%'))
   OR LOWER(p.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    List<ProductSearchDto> searchProducts(@Param("keyword") String keyword);
}

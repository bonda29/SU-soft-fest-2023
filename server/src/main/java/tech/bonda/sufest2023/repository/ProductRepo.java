package tech.bonda.sufest2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.bonda.sufest2023.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    Optional<Object> findByName(String name);

    Optional<List<Product>> findAllByCompanyId(Integer companyId);
}

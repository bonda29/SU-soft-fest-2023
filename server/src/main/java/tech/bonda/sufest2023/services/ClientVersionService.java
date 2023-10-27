package tech.bonda.sufest2023.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.bonda.sufest2023.models.Company;
import tech.bonda.sufest2023.repository.CompanyRepo;
import tech.bonda.sufest2023.repository.ProductRepo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientVersionService {

    private final CompanyRepo companyRepo;
    private final ProductRepo productRepo;

    public ClientVersionService(CompanyRepo companyRepo, ProductRepo productRepo) {
        this.companyRepo = companyRepo;
        this.productRepo = productRepo;
    }

    public ResponseEntity<?> searchCompanyByKeyWord(String keyword) {
        Optional<List<Company>> companies = companyRepo.findAllByNameContaining(keyword);
        if (companies.isPresent())
        {
            return ResponseEntity.ok(companies.get());
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

}
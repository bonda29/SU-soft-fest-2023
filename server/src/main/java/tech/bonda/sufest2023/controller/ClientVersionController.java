package tech.bonda.sufest2023.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.bonda.sufest2023.services.ClientVersionService;

@RestController
@RequestMapping("/clientVersion")
@CrossOrigin("*")
public class ClientVersionController {

    private final ClientVersionService clientVersionService;

    public ClientVersionController(ClientVersionService clientVersionService) {
        this.clientVersionService = clientVersionService;
    }

    @GetMapping("/searchCompany/{keyword}")
    public ResponseEntity<?> searchCompanyByKeyWord(@PathVariable String keyword) {
        System.out.println("keyword = " + keyword);
        return clientVersionService.searchCompanyByKeyWord(keyword);
    }
}

package br.org.fundatec.SuperCevaJa.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Felipe Brandão e João Gabriel C. Da Cruz
 * Controller par verificar a condição da API
 */
@RestController
@RequestMapping("/cevaja/api/v1/health")
@CrossOrigin(origins = "*")
public class  HealthController {

    /**
     *
     * @return ResponseEntity.Ok
     * Verifica se a API está funcionando
     */
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hi! I'm RestTemplate POC!");
    }


}

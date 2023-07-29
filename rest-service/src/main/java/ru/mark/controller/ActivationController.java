package ru.mark.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mark.service.UserActivationService;

@RestController
@RequestMapping("/user")
public class ActivationController {
    private final UserActivationService userActivationService;

    public ActivationController(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }


    @GetMapping("/activation")
    public ResponseEntity<?> activation(@RequestParam("id") String id){
        var res = userActivationService.activation(id);
        if(res){
            return ResponseEntity.ok().body("Регистрация успешно завершена!");
        }
        return ResponseEntity.internalServerError().build();

    }
}


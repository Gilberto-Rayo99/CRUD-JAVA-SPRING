package med.voll.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/hello") // http://localhost:8080/hello
public class HelloController {

    @GetMapping
    public String HelloWorld() {
        return "Hello, World!";
    }

     

}

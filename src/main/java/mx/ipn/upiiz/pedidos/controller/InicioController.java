package mx.ipn.upiiz.pedidos.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Si entran a la raiz del sitio los mandamos directo a la documentacion de Swagger. */
@Hidden
@Controller
public class InicioController {

    @GetMapping("/")
    public String inicio() {
        return "redirect:/swagger-ui/index.html";
    }
}

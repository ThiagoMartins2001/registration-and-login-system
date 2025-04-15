package br.appLogin.appLogin.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.appLogin.appLogin.model.User;
import br.appLogin.appLogin.repository.UserRepository;
import br.appLogin.appLogin.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class LoginController {

    @Autowired
    private UserRepository ur;
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String dashboard(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/logar")
    public String loginuser(User usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException{
        User userLogado = this.ur.login(usuario.getEmail(), usuario.getPassword());
        if(userLogado != null){
            CookieService.setCookie (response, "usuarioLogado", String.valueOf(userLogado.getId()), 10000 );
            CookieService.setCookie (response, "nomeusuario", String.valueOf (userLogado.getName()), 10000 );
          return "redirect:/";
        }

        model.addAttribute("erro", "Usuárrio ou senha inválidos!");
        return "login";
    }

    @GetMapping("/cadastrarUsuario")
    public String cadastro(){
        return "cadastro";
    }

    @RequestMapping(value = "/cadastrarUsuario", method = RequestMethod.POST)
    public String cadastroUsuario(@Valid User usuario, BindingResult result){

        if(result.hasErrors()){
            return "redirect:/cadastrarUsuario";
        }
        ur.save(usuario);

    return "/login";
    }

}

package br.appLogin.appLogin.controller;

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
    public String loginuser(User usuario, Model model, HttpServletResponse response){
        User userLogado = this.ur.login(usuario.getEmail(), usuario.getPassword());
        if(userLogado != null){
          return "redirect:/";
        }

        model.addAttribute("erro", "Usuárrio ou senha inválidos!");
        return "login/login";
    }

    //--------------------------------

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

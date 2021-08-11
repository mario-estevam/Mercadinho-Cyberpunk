package com.example.alimentos.controller;

import com.example.alimentos.model.Alimento;
import com.example.alimentos.repository.FileStorageService;
import com.example.alimentos.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class alimentoController {

    AlimentoService service;
    FileStorageService fileStorageService;

    @Autowired
    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @Autowired
    public void setService(AlimentoService service) {
        this.service = service;
    }



    @RequestMapping(value = {"/index" , "/"}, method = RequestMethod.GET)
    public String homePage(Model model, HttpServletResponse response) {
        List<Alimento> listaAlimentos = this.service.findAll();
        model.addAttribute("listaAlimentos", listaAlimentos);
        SimpleDateFormat date_formatter = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss:SSS");
        Date date = new Date();
        Cookie cookie = new Cookie("visita", date_formatter.format(date));
        cookie.setMaxAge(60*60*24);
        response.addCookie(cookie);
        return "index";
    }



    @RequestMapping(value = {"/admin" }, method = RequestMethod.GET)
    public String getAdmin(Model model){
        var listaAlimentos = service.findAll();
        model.addAttribute("listaAlimentos", listaAlimentos);
        return "admin";
    }

    @RequestMapping("/cadastro")
    public String getFormCadastro(Model model){
        Alimento alimento = new Alimento();
        model.addAttribute("alimento", alimento);
        return "cadastro";
    }

    @RequestMapping(value = "/salvar", method = RequestMethod.POST)
    public String doSalvar(@ModelAttribute @Valid Alimento alimento, Errors errors, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        if (errors.hasErrors()){
            return "cadastro";
        }else{
            alimento.setImagemUri(file.getOriginalFilename());
            service.save(alimento);
            fileStorageService.save(file);

            redirectAttributes.addAttribute("msg", "Cadastro realizado com sucesso");
            return "redirect:/admin";
        }
    }

    @RequestMapping("/deletar/{id}")
    public String doDelete(@PathVariable(name = "id") Long id){
        service.delete(id);
        return "redirect:/admin";
    }

    @RequestMapping("/editar/{id}")
    public ModelAndView getFormEdicao(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("edicao");
        Alimento alimento = service.findById(id);
        modelAndView.addObject("alimento", alimento);
        return modelAndView;
    }


    @RequestMapping("/adicionarCarrinho/{id}")
    public String getCarrinho(@PathVariable(name = "id") Long id, HttpServletRequest request){

        HttpSession session = request.getSession();

        if(session.getAttribute("carrinho")==null){
            session.setAttribute("carrinho", new ArrayList<Alimento>());
        }
        System.out.println(service.findById(id));
        ArrayList<Alimento> productsCar = (ArrayList<Alimento>) session.getAttribute("carrinho");
        productsCar.add(service.findById(id));
        System.out.println("");
        return "redirect:/";

    }

    @RequestMapping("/verCarrinho")
    public String getCarrinho(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("carrinho")==null){
            return "redirect:/";
        }
        System.out.println(service.findAll());
        ArrayList<Alimento> ali = (ArrayList<Alimento>) session.getAttribute("carrinho");
        model.addAttribute("listarCarrinho",ali);
        System.out.println(ali);
        return "carrinho";

    }

    @RequestMapping("/finalizar")
    public void doFinalizar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect("/");
    }

}

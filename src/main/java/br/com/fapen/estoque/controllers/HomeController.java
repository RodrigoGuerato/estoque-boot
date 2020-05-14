package br.com.fapen.estoque.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET, name = "indexUrl")
	public String index() {
		return "redirect:/home";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET, name = "homeUrl")
	public String home(Principal usuarioPrincipal) {
		return "home";
	}
}

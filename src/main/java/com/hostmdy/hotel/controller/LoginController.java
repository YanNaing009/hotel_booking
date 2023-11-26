package com.hostmdy.hotel.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hostmdy.hotel.crypto.PasswordValidator;
import com.hostmdy.hotel.domain.Client;
import com.hostmdy.hotel.service.ClientService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/client")
public class LoginController {
	
	private final ClientService clientService;

	public LoginController(ClientService clientService) {
		super();
		this.clientService = clientService;
	}
	
	@GetMapping("/loginform")
	public String loginForm() {
		return "login/login";
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute Client client,HttpServletRequest request,Model model) {
		Optional<Client> clientOpt = clientService.getClientByEmail(client.getEmail());
		if(clientOpt.isEmpty()) {
			boolean success = true;
			model.addAttribute("success", success);
			return "login/login";
		}
		try {
			if(!PasswordValidator.validatePassword(client.getPassword(), clientOpt.get().getPassword())) {
				boolean success = true;
				model.addAttribute("success", success);
				return "login/login";
			}
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Client clientIsAdmin = clientOpt.get();
		if(clientIsAdmin.getRole() == "admin") {
			System.out.println("###");
			return "redirect:/admin/admin-dashboard";
		}
		Client user = clientOpt.get();
		String userName = user.getFirstName()+" "+user.getLastName();
		HttpSession session = request.getSession();
		session.setAttribute("userName",userName);
		session.setAttribute("client", user);
		return "redirect:/";
	}
	
	@GetMapping("/signupform")
	public String signupForm() {
		return "login/signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute Client client,Model model,HttpServletRequest request) {
		Optional<Client> clientOpt = clientService.getClientByEmail(client.getEmail());
		if(clientOpt.isPresent()) {
			boolean existUser = true;
			model.addAttribute("existUser", existUser);
			return "login/signup";
		}
		clientService.createClient(client);
		
		String userName = client.getFirstName()+" "+client.getLastName();
		HttpSession session = request.getSession();
		session.setAttribute("userName",userName);
		session.setAttribute("client", client);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}

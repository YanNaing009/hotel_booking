package com.hostmdy.hotel.controller;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hostmdy.hotel.domain.Client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {
	
	@GetMapping({"/","/home"})
	public String showHome(Model model,HttpSession session) {
		String username = (String) session.getAttribute("userName");
		Client client = (Client) session.getAttribute("client");
		LocalDate date = LocalDate.now();
		LocalDate nextday = date.plusDays(1);
		model.addAttribute("username", username);
		model.addAttribute("client", client);
		model.addAttribute("date", date);
		model.addAttribute("nextdate", nextday);
		return "index";
	}
}

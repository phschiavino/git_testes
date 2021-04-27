package br.com.teste.seguradora.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.teste.seguradora.models.Cliente;
import br.com.teste.seguradora.repositorys.ClienteRepository;

@Controller
public class ClienteController {

	@Autowired
	private ClienteRepository repository;

	@GetMapping("/cliente/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("cliente", new Cliente());
		return "cliente/cadastrar";
	}

	@PostMapping("/cliente/salvar")
	public String salvar(@ModelAttribute Cliente cliente) {
		System.out.println("Cliente para salvar: " + cliente);
		try {
			repository.salvar(cliente);
		} catch (Exception e) {
			System.out.println("Erro");
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@GetMapping("/cliente/listar")
	public String listar(Model model) {
		List<Cliente> clientes = repository.obterTodosClientes();
		model.addAttribute("clientes", clientes);
		return "cliente/listar";
	}

}
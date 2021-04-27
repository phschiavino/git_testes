package br.com.teste.seguradora.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.teste.seguradora.models.Apolice;
import br.com.teste.seguradora.repositorys.ApoliceRepository;

@Controller
public class ApoliceController {

	@Autowired
	private ApoliceRepository repository;

	@GetMapping("/apolice/cadastrar")
	public String cadastrar(Model model) {
		model.addAttribute("apolice", new Apolice());
		return "apolice/cadastrar";
	}

	@PostMapping("/apolice/salvar")
	public String salvar(@ModelAttribute Apolice apolice) {
		System.out.println("Apolice para salvar: " + apolice);
		try {
			repository.salvar(apolice);
		} catch (Exception e) {
			System.out.println("Apolice nao localizado");
			e.printStackTrace();
		}

		return "redirect:/";
	}

	@GetMapping("/apolice/listar")
	public String listar(Model model) {
		List<Apolice> apolices = repository.obterTodosApolice();
		model.addAttribute("apolices", apolices);
		return "apolice/listar";
	}

	@GetMapping("/apolice/visualizar/{id}")
	public String visualizar(@PathVariable String id, Model model) {

		Apolice apolice = repository.obterApolicePor(id);

		model.addAttribute("apolice", apolice);

		return "apolice/visualizar";
	}

	@DeleteMapping("/apolice/remover/{id}")
	public String deletar(@PathVariable("id") Apolice apolice) {
		repository.excluir(apolice);
		return "apolice/listar";
	}

	@GetMapping("/apolice/pesquisarapolice")
	public String pesquisarApolice() {
		return "apolice/pesquisarapolice";
	}
	
	@GetMapping("/apolice/pesquisar")
	public String pesquisar(@RequestParam("nApolice") int nApolice, Model model) {
		List<Apolice> apolices = repository.pesquisarPor(nApolice);

		model.addAttribute("apolices", apolices);

		return "apolice/pesquisarapolice";
	}
}

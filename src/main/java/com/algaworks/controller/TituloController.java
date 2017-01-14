package com.algaworks.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.model.StatusTitulo;
import com.algaworks.model.Titulo;
import com.algaworks.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	@Autowired
	Titulos titulos;

	private static final String CADASTRO_VIEW = "CadastroTitulo";

	@RequestMapping("/novo")
	public ModelAndView novo(){
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes){
		if (erros.hasErrors()){
			return CADASTRO_VIEW;
		}
		try{
			titulos.save(titulo);
			attributes.addFlashAttribute("mensagem","Título salvo com sucesso");
			return "redirect:/titulos/novo";
		}catch(DataIntegrityViolationException e){
			erros.rejectValue("dataVencimento",null,"Formato de data inválido.");
			return CADASTRO_VIEW;
		}
	}

	@RequestMapping
	public ModelAndView pesquisar(){
		List<Titulo> listaTitulos = titulos.findAll();
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", listaTitulos);
		return mv;
	}

	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo){
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;		
	}

	@RequestMapping(value="{codigo}",method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes){
		titulos.delete(codigo);
		attributes.addFlashAttribute("mensagem","Título excluído com sucesso.");
		return "redirect:/titulos";
	}

	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo> todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}

}

package br.edu.ifal.tarde.formularioweb;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class ControlerPrincipal{
    @Autowired
    AlunoRepositorio alunoRepositorio;

    @RequestMapping("/")
    public ModelAndView index(){
        return new ModelAndView("index.html");
    }

    @RequestMapping("/formulario")
    public ModelAndView Cadastro(){
        return new ModelAndView("FormularioWeb.html");
    }

    @RequestMapping("/novo_aluno")
    public ModelAndView cadastroNovoAluno(Aluno aluno,  RedirectAttributes redirect){

        alunoRepositorio.save(aluno);

        ModelAndView reposta = new ModelAndView("FormularioWeb.html");
        redirect.addFlashAttribute("mensagem", aluno.getNome() + " cadastrado com sucesso.");
        return reposta;
    }

    @RequestMapping("/listar_alunos")
    public ModelAndView listarAluno(){
        ModelAndView resposta = new ModelAndView("ListarAlunos.html");
        Iterable<Aluno> alunos = alunoRepositorio.findAll();
        resposta.addObject("alunos", alunos);
        return resposta;
    }

    @RequestMapping("/deletar_aluno/{idAluno}")
    public ModelAndView excluirAlunos(@PathVariable("idAluno") Long alunoID, RedirectAttributes redirect) {
        Optional<Aluno> opcao = alunoRepositorio.findById(alunoID);
        ModelAndView resposta = new ModelAndView("redirect:/listar_alunos");        
        if(opcao.isPresent()){
            Aluno a = opcao.get();
            alunoRepositorio.deleteById(a.getId());            
            redirect.addFlashAttribute("mensagem", a.getNome() + " excluído com sucesso.");
            return resposta;
        } else {
            redirect.addFlashAttribute("mensagem", "Aluno " + alunoID + " não existe no Banco de Dados.");
            return resposta;
        }
        
    }
}
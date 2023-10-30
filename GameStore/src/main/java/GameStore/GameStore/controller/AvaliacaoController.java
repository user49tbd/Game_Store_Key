package GameStore.GameStore.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import GameStore.GameStore.model.AvaliacaoModel;
import GameStore.GameStore.persistence.DaoAvaliacaoJ;

@Controller
public class AvaliacaoController {
	@Autowired
	DaoAvaliacaoJ daj;
	
	@RequestMapping(name = "avaliar", value = "/avaliar", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model,HttpSession session, @RequestParam Map<String,String>
	allParam) {
		System.out.println("Entrou no get");
		AvaliacaoModel avm = new AvaliacaoModel();
		String titulo = allParam.get("title");
		String ano = allParam.get("ano");
		try {
			avm.setUsr(session.getAttribute("usr").toString());
			avm.setTitulo(titulo);
			avm.setAno(Integer.parseInt(ano));
			if(!daj.getAvaliacaoJ(avm, "B").isEmpty()) {
				avm = daj.getAvaliacaoJ(avm, "B").get(0);
			}
		}catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.setAttribute("avmS", avm);
		}
		//session.setAttribute("avm",val);
		return new ModelAndView("avaliar");
	}
	@RequestMapping(name = "avaliar", value="/avaliar", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		AvaliacaoModel avm = new AvaliacaoModel();
		String title = allParam.get("title");
		String ano = allParam.get("ano");
		String nota = allParam.get("nota");
		String desc = allParam.get("desc");
		String bt = allParam.get("bt");
		String err="";
		String vl="D";
		List<AvaliacaoModel> ls = new ArrayList<>();
		if(ano == null || ano.equalsIgnoreCase("")) {
			ano = String.valueOf(LocalDate.now().getYear());
		}
		if(nota == null || nota.equalsIgnoreCase("")) {
			nota = "0";
		}
		try {
			avm.setUsr(session.getAttribute("usr").toString());
			avm.setTitulo(title);
			avm.setAno(Integer.parseInt(ano));
			if(bt.equalsIgnoreCase("Inserir") || bt.equalsIgnoreCase("Deletar") 
					|| bt.equalsIgnoreCase("Atualizar")) {
				//avaliacaoModel avm = new avaliacaoModel();
				//avm.setUsr(session.getAttribute("usr").toString());
				//avm.setTitulo(title);
				//avm.setAno(Integer.parseInt(ano));
				avm.setNota(Double.parseDouble(nota));
				avm.setDescricao(desc);
				if(bt.equalsIgnoreCase("Inserir")) {
					vl="I";
				}
				if(bt.equalsIgnoreCase("Atualizar")) {
					vl="U";
				}
				System.out.println("valor da operacao: "+vl);
				err=daj.AvOps(avm, vl);
				if(bt.equalsIgnoreCase("Deletar")) {
					avm = new AvaliacaoModel();
				}
			}
			if(bt.equalsIgnoreCase("Buscar") || bt.equalsIgnoreCase("Listar")) {
				//avaliacaoModel avm = new avaliacaoModel();
				//avm.setUsr(session.getAttribute("usr").toString());
				//avm.setTitulo(title);
				//avm.setAno(Integer.parseInt(ano));
				System.out.println("usuario : "+avm.getUsr()+" - "+
				"titulo: "+avm.getTitulo()+" - ano: "+avm.getAno());
				if(bt.equalsIgnoreCase("Buscar")) {
					vl="B";
				}
				if(bt.equalsIgnoreCase("Listar")) {
					vl="L";
				}
				System.out.println("op: "+vl);
				ls = daj.getAvaliacaoJ(avm, vl);
			}
			if(bt.equalsIgnoreCase("voltar")) {
				String rp = session.getAttribute("rp").toString();
				session.setAttribute("rp", "index");
				return new ModelAndView(rp);
			}
			if(!daj.getAvaliacaoJ(avm, "B").isEmpty()) {
				avm = daj.getAvaliacaoJ(avm, "B").get(0);
			}
			if(!bt.equalsIgnoreCase("Deletar")) {
				if(!daj.getAvaliacaoJ(avm, "B").isEmpty()) {
					avm = daj.getAvaliacaoJ(avm, "B").get(0);
				}
				avm.setNota(Double.parseDouble(nota));
				avm.setDescricao(desc);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			model.addAttribute("msg",err);
			model.addAttribute("lstav",ls);
			session.setAttribute("avmS", avm);
		}
		return new ModelAndView("avaliar");
	}
}

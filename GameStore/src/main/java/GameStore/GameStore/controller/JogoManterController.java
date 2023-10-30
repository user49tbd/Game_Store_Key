package GameStore.GameStore.controller;

import java.sql.SQLException;
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

import GameStore.GameStore.model.JogoModel;
import GameStore.GameStore.persistence.DaoJogo;

@Controller
public class JogoManterController {
	@Autowired
	DaoJogo dg;
	@RequestMapping(name = "JogoM", value = "/JogoM", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model,@RequestParam Map<String,String>
	allParam,HttpSession session) {
		System.out.println("entro no get");
		String bt = "";
		List<String> ls = new ArrayList<>();
		List<String> lstgms = new ArrayList<>();
		JogoModel jm= new JogoModel();
		bt = allParam.get("btt");
		//String bk = allParam.get("bk");
		try {
			ls = dg.getGen();
			if(bt != null && !bt.equalsIgnoreCase("")) {
				System.out.println("Entrou no botao");
				String title=bt.split("\\| ")[0];
				System.out.println("gara gun");
				int ano=Integer.parseInt(bt.split("\\| ")[1]);
				System.out.println(title);
				System.out.println(ano);
				jm.setAno(ano);
				jm.setTitulo(title);
				jm = dg.busca(jm);
				jm.setCgen(dg.getGenJogoM(jm,"V"));
				model.addAttribute("lstgms", null);
			}else {
				lstgms = dg.getJlistM(session.getAttribute("usr").toString(),"usr");
				model.addAttribute("lstgms", lstgms);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.setAttribute("lstgen", ls);
			session.setAttribute("gameM", jm);
		}
		return new ModelAndView("JogoM");
	}
	@RequestMapping(name = "JogoM", value="/JogoM", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		List<String> ls = new ArrayList<>();
		//List<String> ls = new ArrayList<>();
		List<JogoModel> ljm = new ArrayList<>();
		String bt = allParam.get("bt");
		String vl="D";
		String titulo = allParam.get("titulo");
		String ano = allParam.get("ano");
		if(ano == null || ano.equalsIgnoreCase("")) {
			ano = "0";
		}
		String genero = allParam.get("gen");
		String descricao= allParam.get("desc");
		String err ="";
		JogoModel jm = new JogoModel();
		try {
			jm.setPublicadora(session.getAttribute("usr").toString());
			jm.setAno(Integer.parseInt(ano));
			jm.setTitulo(titulo);
			ls = dg.getGen();
			if(bt.equalsIgnoreCase("Inserir") || bt.equalsIgnoreCase("Deletar") 
					|| bt.equalsIgnoreCase("Atualizar")) {
				//jogoModel jm = new jogoModel();
				
				jm.setCgen(genero);
				jm.setDescricao(descricao);
				if(bt.equalsIgnoreCase("Inserir")) {
					vl="I";
				}
				if(bt.equalsIgnoreCase("Atualizar")) {
					vl="U";
				}
				System.out.println("valor da operacao: "+vl);
				err=dg.opjogoM(jm, vl);
				if(bt.equalsIgnoreCase("Deletar")) {
					jm = new JogoModel();
				}
			}
			if(bt.equalsIgnoreCase("Buscar") || bt.equalsIgnoreCase("Listar")) {
				if(bt.equalsIgnoreCase("Buscar")) {
					vl="B";
				}
				if(bt.equalsIgnoreCase("Listar")) {
					vl="L";
				}
				System.out.println("op: "+vl);
				ljm = dg.getjogoMBL(jm, vl);
				//ls = da.getAvaliacaoJ(avm, vl);
			}
			if(bt.equalsIgnoreCase("Gerenciar Chaves")) {
				vl="B";
				ljm = dg.getjogoMBL(jm, vl);
				if(ljm.isEmpty()) {
					err = "titulo nao encontrado";
				}else {
					jm = dg.busca(jm);
					jm.setCgen(dg.getGenJogoM(jm,"V"));
					session.setAttribute("gameM", jm);
					session.setAttribute("rp", "JogoM");
					return new ModelAndView("chaves");
				}
			}
			//------------------------------------------------------------
			jm = dg.busca(jm);
			jm.setCgen(dg.getGenJogoM(jm,"V"));
			session.setAttribute("gameM", jm);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			model.addAttribute("lstgam",ljm);
			model.addAttribute("msgerr",err);
			session.setAttribute("lstgen", ls);

		}
		return new ModelAndView("JogoM");
	}
}

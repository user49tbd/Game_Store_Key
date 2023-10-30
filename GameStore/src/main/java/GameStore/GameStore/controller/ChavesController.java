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

import GameStore.GameStore.model.ChaveModel;
import GameStore.GameStore.model.JogoModel;
import GameStore.GameStore.persistence.DaoChaves;

@Controller
public class ChavesController {
	@Autowired
	DaoChaves dc;
	
	@RequestMapping(name = "chaves", value = "/chaves", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model,HttpSession session) {
		//session.setAttribute("rp", "index");
		return new ModelAndView("chaves");
	}
	@RequestMapping(name = "chaves", value="/chaves", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		List<ChaveModel> lsdc = new ArrayList<>();
		JogoModel jm = (JogoModel) session.getAttribute("gameM");
		String bt = allParam.get("bt");
		String qtd = allParam.get("qtd");
		String vl="D";
		if(qtd == null || qtd.equalsIgnoreCase("")) {
			qtd = "0";
		}
		String err="";
		try {
			if(bt.equalsIgnoreCase("Inserir") || bt.equalsIgnoreCase("Deletar")) {
				if(bt.equalsIgnoreCase("Inserir")) {
					vl="I";
				}
				err = dc.IsrDelChave(vl, jm, Integer.parseInt(qtd));
			}
			if(bt.equalsIgnoreCase("Buscar") || bt.equalsIgnoreCase("Listar")) {
				ChaveModel cm = new ChaveModel();
				cm.setUsr(session.getAttribute("usr").toString());
				cm.setTitulo(jm.getTitulo());//titulo);
				cm.setAno(jm.getAno());//Integer.parseInt(ano));
				if(bt.equalsIgnoreCase("Buscar")) {
					vl="B";
				}
				if(bt.equalsIgnoreCase("Listar")) {
					vl="D";
				}
				lsdc = dc.BLChaves(vl, cm);
				//err = dc.IsrDelChave(vl, jm, Integer.parseInt(qtd));
			}
			if(bt.equalsIgnoreCase("voltar")) {
				String rp = session.getAttribute("rp").toString();
				session.setAttribute("rp", "index");
				return new ModelAndView(rp);
			}
		}
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//session.setAttribute("game", jm);
			//model.addAttribute("lstgen",ls);
			model.addAttribute("lstK",lsdc);
			model.addAttribute("msgerr",err);
	
		}
		return new ModelAndView("chaves");
	}
}

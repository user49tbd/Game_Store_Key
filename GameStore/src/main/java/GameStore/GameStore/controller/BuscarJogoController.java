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

import GameStore.GameStore.persistence.DaoJogo;

@Controller
public class BuscarJogoController {
	@Autowired
	DaoJogo dg;
	
	@RequestMapping(name = "buscarJ", value = "/buscarJ", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model) {
		return new ModelAndView("buscarJ");
	}
	@RequestMapping(name = "buscarJ", value="/buscarJ", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		String title = allParam.get("nomeJogo");
		String bt = allParam.get("bt");
		List<String> ltitle = new ArrayList<>();
		try {
			if(bt != null && bt.equalsIgnoreCase("BUSCAR")) {
				ltitle = dg.getJlistM(title,"ALL");
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			model.addAttribute("Tlst", ltitle);
		}
		return new ModelAndView("buscarJ");
	}
}

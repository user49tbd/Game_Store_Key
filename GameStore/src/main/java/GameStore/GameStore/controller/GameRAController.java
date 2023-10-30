package GameStore.GameStore.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import GameStore.GameStore.model.AvaliacaoModel;
import GameStore.GameStore.model.BibliotecaModel;
import GameStore.GameStore.model.JogoModel;
import GameStore.GameStore.persistence.DaoAvaliacao;
import GameStore.GameStore.persistence.DaoAvaliacaoJ;
import GameStore.GameStore.persistence.DaoChaves;
import GameStore.GameStore.persistence.DaoJogo;
import javax.servlet.http.*;  

@Controller
public class GameRAController {
	@Autowired
	DaoJogo dg;
	@Autowired
	DaoAvaliacao da;
	@Autowired
	DaoAvaliacaoJ daj;
	@Autowired
	DaoChaves dc;
	
	@RequestMapping(name = "gameS", value = "/gameS", method = RequestMethod.GET)
	public ModelAndView get(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		JogoModel jm= new JogoModel();
		if(allParam.get("btt") != null) {
			session.setAttribute("gameN", allParam.get("btt"));
		}
		String bt = session.getAttribute("gameN").toString();//allParam.get("btt");
		try {
			if(bt != null) {
				String title=bt.split("\\| ")[0];
				int ano=Integer.parseInt(bt.split("\\| ")[1]);
				System.out.println("OMEGARAY BURNE--------------------");
				System.out.println(title);
				System.out.println(ano);
				jm.setAno(ano);
				jm.setTitulo(title);
				jm = dg.busca(jm);
			}
			//String dt = allParam.get("ipdt");
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			session.setAttribute("game", jm);

		}
		return new ModelAndView("gameS");
	}
	@RequestMapping(name = "gameS", value="/gameS", method = RequestMethod.POST)
	public ModelAndView post(ModelMap model, @RequestParam Map<String,String>
	allParam,HttpSession session) {
		String bt = allParam.get("res");
		List<AvaliacaoModel> ls = new ArrayList<>();
		String err="";
		double med=0;
		try {
		if(bt.equalsIgnoreCase("Listar Avaliacoes")) {
			JogoModel jm = (JogoModel) session.getAttribute("game");
			ls = da.getAvaliacoes(jm.getTitulo(), jm.getAno());
			//System.out.println(ls.get(0).getAno());
			JogoModel jmres = (JogoModel) session.getAttribute("game");
			jmres.setMedia(dg.getMed(jmres));
			med = jmres.getMedia();
			model.addAttribute("med",med);
			return new ModelAndView("gameS");
		}
		if((bt.equalsIgnoreCase("Resgate") || bt.equalsIgnoreCase("Avaliar"))
		&& (session.getAttribute("usr") !=null) 
		&& (session.getAttribute("tpusr").toString().equalsIgnoreCase("J"))) {
			if(bt.equalsIgnoreCase("Avaliar")) {
				session.setAttribute("rp", "gameS");
				JogoModel jm = (JogoModel) session.getAttribute("game");
				AvaliacaoModel avm = new AvaliacaoModel();
				avm.setTitulo(jm.getTitulo());
				avm.setAno(jm.getAno());
				avm.setUsr(session.getAttribute("usr").toString());
				if(!daj.getAvaliacaoJ(avm, "B").isEmpty()) {
					avm = daj.getAvaliacaoJ(avm, "B").get(0);
				}
				session.setAttribute("avmS", avm);
				
				return new ModelAndView("avaliar");
				//model.addAttribute("emsg", err);
			}if(bt.equalsIgnoreCase("Resgate")) {
				BibliotecaModel bm = new BibliotecaModel();
				JogoModel jmres = (JogoModel) session.getAttribute("game");
				bm.setUsr(session.getAttribute("usr").toString());
				bm.setTitulo(jmres.getTitulo());
				bm.setAno(jmres.getAno());
				err = dc.rescueKey(bm);
				//return new ModelAndView("avaliar");
				//model.addAttribute("emsg", err);
			}
			//return new ModelAndView("gameS");
		}else {
			session.setAttribute("rp", "gameS");
			err = "efetue  login em uma conta jogador para resgatar ou avaliar um jogo";
			System.out.println("VALORES TOTAIS");
			System.out.println(session.getAttribute("usr"));
			System.out.println(session.getAttribute("tpusr"));
			model.addAttribute("emsg", err);
			return new ModelAndView("logger");
		}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			model.addAttribute("lsav", ls);
			model.addAttribute("msgerr", err);
		}
		return new ModelAndView("gameS");
		
	}
}

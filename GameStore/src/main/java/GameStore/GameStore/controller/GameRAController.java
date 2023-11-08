package GameStore.GameStore.controller;
import org.openqa.selenium.JavascriptExecutor;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
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
import GameStore.GameStore.model.metaC;
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
		
		//---------------------------------------------------------------
		
		try {
			ArrayList<metaC> arr = rasparDados(bt.split("\\| ")[0]);//"gears of war");
			//model.addAttribute("raspD",arr);
			//model.addAttribute("fullN",arr.get(0).getFullG());
			session.setAttribute("raspD", arr);
			session.setAttribute("fullN",arr.get(0).getFullG());
		} catch (Exception e) {
			session.setAttribute("raspD", "");
			session.setAttribute("fullN","");
	    	model.addAttribute("errScrap","jogo não encontrado");
	      System.out.println("Something went wrong.");
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
		AvaliacaoModel avm = new AvaliacaoModel();
		try {
		if(bt.equalsIgnoreCase("Listar Avaliacoes")) {
			JogoModel jm = (JogoModel) session.getAttribute("game");
			ls = da.getAvaliacoes(jm.getTitulo(), jm.getAno());
			//System.out.println(ls.get(0).getAno());
			JogoModel jmres = (JogoModel) session.getAttribute("game");
			jmres.setMedia(dg.getMed(jmres));
			med = jmres.getMedia();
			model.addAttribute("med",med);
			
			/*
			try {
				JogoModel jmt = (JogoModel) session.getAttribute("game");
				System.out.println("here---------------------> "+jmt.getTitulo());
				System.out.println("here---------------------> "+bt);
				ArrayList<metaC> arr = rasparDados(jmt.getTitulo());//"gears of war");
				model.addAttribute("raspD",arr);
				model.addAttribute("fullN",arr.get(0).getFullG());
			} catch (Exception e) {
				model.addAttribute("errScrap","jogo não encontrado");
				System.out.println("Something went wrong.");
			}
			*/
			
			
			return new ModelAndView("gameS");
		}
		if((bt.equalsIgnoreCase("Resgate") || bt.equalsIgnoreCase("Avaliar"))
		&& (session.getAttribute("usr") !=null) 
		&& (session.getAttribute("tpusr").toString().equalsIgnoreCase("J"))) {
			if(bt.equalsIgnoreCase("Avaliar")) {
				session.setAttribute("rp", "gameS");
				JogoModel jm = (JogoModel) session.getAttribute("game");
				
				//------------
				//AvaliacaoModel avm = new AvaliacaoModel();
				//------------
				avm = new AvaliacaoModel();
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
		
		
		//---------------------------------------------------------------
		/*
		try {
			JogoModel jmt = (JogoModel) session.getAttribute("game");
			System.out.println("here---------------------> "+jmt.getTitulo());
			System.out.println("here---------------------> "+bt);
			ArrayList<metaC> arr = rasparDados(jmt.getTitulo());//"gears of war");
			model.addAttribute("raspD",arr);
			model.addAttribute("fullN",arr.get(0).getFullG());
		} catch (Exception e) {
			model.addAttribute("errScrap","jogo não encontrado");
			System.out.println("Something went wrong.");
		}
		*/
		
		return new ModelAndView("gameS");
		
	}
	
	
	//---------------------------------------------------------------------------
	
		private static ArrayList<metaC> rasparDados(String gn) {
			// caminho do driver para acessar o browser
			File f = new File("C:\\Users\\Maria\\Desktop\\webdriver\\msedgedriver.exe");
			System.setProperty("webdriver.edge.driver", f.getPath());
			
			EdgeOptions options = new EdgeOptions();
			
			
			options.addArguments("--headless");
			
			//options.addArguments("--charset=UTF-8");
			//corrigir falhas
			options.addArguments("--lang=pt");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--remote-allow-origins=*");
			
			// detecção bot
			options.addArguments("--disable-blink-features=AutomationControlled");
			options.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
			options.setExperimentalOption("useAutomationExtension", null);
			
			options.addArguments("window-size=800,600");
			options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
			WebDriver driver = new EdgeDriver(options = options);
			String val = gn;
			//val.length();
			
			//String [] a = val.split("");
			//a[val.length()];
			val = val.replaceAll("^ +| +$|( )+", "$1");
			val = val.replace(" ", "-");
			/*	
			String fval = "";
			ArrayList<String> ar = new ArrayList<>();
			ar.add(val.split(" ")[0]);
			for(int i = 0;i <= val.split(" ").length;i++) {
				fval.concat(val.split(val)[i-1]);
				if(i < val.split(" ").length) {
					fval.concat("-");
				}
			}
			*/
			String meta = "https://www.metacritic.com/game/";
			meta = meta.concat(val);
			meta = meta.concat("/");
			driver.get(meta);
			//driver.get("https://amazon.com.br");
			//waitEv(5000);
			waitEv(1050);
			WebElement inputP = driver.findElement(By.xpath("//*[@id=\"onetrust-close-btn-container\"]/button"));
			inputP.click();
			waitEv(150);
			inputP = driver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[1]/div[2]/div/div[2]/a[2]/div[1]"));
			inputP.click();
			
			//inputP = driver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[1]/div[1]/section/div[1]/div/div[2]/div/div/div/ul/li[2]/a"));
			//inputP.click();
			//---------
			//waitEv(2500);
			//inputP = driver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[1]/div[1]/section/div[3]/div/div[2]/div[1]"));
			//inputP.click();
			//waitEv(2300);
			//inputP = driver.findElement(By.xpath("//*[@id=\"__layout\"]/div/div[2]/div[1]/div[1]/section/div[3]/div/div[2]/div[2]/div[3]/span"));
			//inputP.click();
			//System.out.println(val);
			
			//*[@id="__layout"]/div/div[2]/div[1]/div[1]/section/div[6]/div[1]/div/div[1]/div[1]/a
			//a[@class="c-siteReviewHeader_username g-text-bold g-color-gray90 "]
			
			//*[@id="__layout"]/div/div[2]/div[1]/div[1]/section/div[6]/div[1]/div/div[1]/div[1]/div[2]/a
			
			//a[@class="c-siteReviewHeader_publicationName g-text-bold g-color-gray90"]
			//span[@data-v-4cdca868=""]
			
			waitEv(600);
			List<WebElement> nome = driver.findElements(By.xpath("//a[@class=\"c-siteReviewHeader_publicationName g-text-bold g-color-gray90\"]"));
			List<WebElement> nota = driver.findElements(By.xpath("//span[@data-v-4cdca868=\"\"]"));
			List<WebElement> critica = driver.findElements(By.xpath("//div[@class=\"c-siteReview_quote g-outer-spacing-bottom-small\"]/span"));
			
			System.out.println("list = "+nome.get(0).toString());
			ArrayList<metaC> mc = new ArrayList<>();
			for(int i = 0;i< nome.size();i++) {
				metaC mtc = new metaC();
				if(i < 1) {
					mtc.setFullG(nota.get(i).getText());
				}
				//System.out.println(nome.get(i).getText()+" , "+nota.get(i+1).getText());
				//System.out.println(critica.get(i).getText());
				mtc.setNome(nome.get(i).getText());
				mtc.setNota(nota.get(i+1).getText());
				mtc.setTexto(critica.get(i).getText());
				mc.add(mtc);
			}
			return mc;
		}

		private static void waitEv(int i) {
			// TODO Auto-generated method stub
			try {
				new Thread().sleep(i);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
}

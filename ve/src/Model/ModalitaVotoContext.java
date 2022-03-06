package Model;

import java.util.Map;

public class ModalitaVotoContext {
	
	private ModalitaVotoStrategy modalitaVoto;
	private String nomeModalitaVoto;
	
	public ModalitaVotoContext() {}
	
	public void setStrategy(String nomeModalitaVoto) {
		this.nomeModalitaVoto = nomeModalitaVoto;
		switch (nomeModalitaVoto) {
			case "Ordinario": // doveva essere ordinale, è da cambiare
				modalitaVoto = new ConcreteStrategyOrdinale();
				break;
			case "Categorico":
				modalitaVoto = new ConcreteStrategyCategorico();
				break;
			case "Referendum":
				modalitaVoto = new ConcreteStrategyReferendum();
				break;
			default:
				modalitaVoto = new ConcreteStrategyCategoricoConPreferenza();
		}
	}
	
	public String getNomeModalitaVoto() {
		return nomeModalitaVoto;
	}
	
	public Map<Votable, Integer> vota (){
		
	}
	
}

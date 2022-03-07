package Model;

import java.util.List;
import java.util.Map;

public class ModalitaVotoContext {
	
	private ModalitaVotoStrategy modalitaVoto;
	private String nomeModalitaVoto;
	
	public ModalitaVotoContext() {}
	
	public void setStrategy(String nomeModalitaVoto) {
		this.nomeModalitaVoto = nomeModalitaVoto;
		switch (nomeModalitaVoto) {
			case "Ordinale":
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
	
	public Map<Votable, Integer> vota (List<Votable> preferenze, Map<Votable, Integer> storicoPreferenze){
		return modalitaVoto.voto(preferenze, storicoPreferenze);
	}
	
}

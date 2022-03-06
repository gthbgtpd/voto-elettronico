package Model;

import java.util.Map;

public class ModalitaVincitaContext {

	private ModalitaVincitaStrategy strategiaVittoria;
	private String nomeModalitaVincita;
	
	public ModalitaVincitaContext() {}
	
	public void setStrategy(String nomeModalitaVincita) {
		
		this.nomeModalitaVincita = nomeModalitaVincita;
		switch (nomeModalitaVincita) {
			case "Maggioranza":
				strategiaVittoria = new Maggioranza();
				break;
			case "Maggioranza assoluta":
				strategiaVittoria = new MaggioranzaAssoluta();
				break;
			case "Referendum con quorum":
				strategiaVittoria = new ReferendumConQuorum();
				break;
			default:
				strategiaVittoria = new ReferendumSenzaQuorum();
		}
	}
	
	public Votable definizioneVincitore(Map<Votable, Integer> votes, int numVoters) {
		return strategiaVittoria.definizioneVincitore(votes, numVoters);
	}
	
	public String getNomeModalitaVincita() {
		return nomeModalitaVincita;
	}
}

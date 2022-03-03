
package Model;

import java.util.Map;

/**
 * 
 */
public interface ModalitaVincitaStrategy {

	public Votable definizioneVincitore(Map<Votable, Integer> votes, int numVoters);
	
}

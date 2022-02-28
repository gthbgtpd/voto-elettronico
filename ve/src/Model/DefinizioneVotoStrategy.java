// da ripensare

package Model;

import java.util.Map;

/**
 * 
 */
public interface DefinizioneVotoStrategy {

	public Votable definizioneVincitore(Map<Votable, Integer> votes, int numVoters);
	
}
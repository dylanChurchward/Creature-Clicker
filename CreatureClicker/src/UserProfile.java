
public class UserProfile {

	
	private final static int BASE_ATTACK_DAMAGE = 3;
	
	
	private int currentDamage;
	
	
	public UserProfile() {
		currentDamage = BASE_ATTACK_DAMAGE;
	}
	
	public int getCurrentDamage() {
		return currentDamage;
	}
	
	public void setCurrentDamage(final int theNewDamage) {
		currentDamage = theNewDamage;
	}
	
	
	
	
	
}


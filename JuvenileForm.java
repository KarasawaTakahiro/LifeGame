import java.util.Random;

public class JuvenileForm extends Plant {
	private int growthTiming;
	private Random rand = new Random();

	public JuvenileForm() {
		super();
		//System.out.println("I'm JUVENILE");
		setColor(109, 224, 81);
		growthTiming = rand.nextInt(30);
	}

	public void update(){
		super.update();
	}

	public AdultForm growup() {
		/*
		 * 成長
		 */
		AdultForm af = new AdultForm();
		af.setSize(getSize());
		af.setPosition(getX(), getY());
		af.setNutrients(getNutrients() - growthTiming);
		af.setNutrientsLimit(getNutrientsLimit());
		af.setActionFrequency(getActionFrequency());
		af.setVelocity(getVelocity());
		af.setCostTimeToStoreNutrients(getCostTimeToStoreNutrients());
		return af;
	}

	public boolean isGrowable() {
		if (getNutrients() > growthTiming) {
			return true;
		} else {
			return false;
		}
	}

}

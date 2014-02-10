import java.util.Random;

public class AdultForm extends Plant {
	private int  nutrientsNeededForBreeding = 30;
	private double breedRate;
	private Random rand;

	public AdultForm() {
		super();
		//System.out.println("I'm ADULT!");
		//System.out.println(this);
		breedRate = 0.6;
		setColor(00, 64, 00);
		rand = new Random();
	}

	public void update(){
		super.update();
	}

	public JuvenileForm breed(int width, int height){
		int size = getSize() * 2;
		JuvenileForm juvenile = new JuvenileForm();
		juvenile.setSize(size/2);
		juvenile.setNutrientsLimit(getNutrientsLimit());
		juvenile.setActionFrequency(getActionFrequency());
		juvenile.setCostTimeToStoreNutrients(getCostTimeToStoreNutrients());
		int x, y;
		do{
			x = size - 2 * rand.nextInt(size+1);
			y = size - Math.abs(x);
			if(rand.nextInt(2) == 1){
				y *= -1;
			}
			x += getX();
			y += getY();
		}while(((x-size/2)<0) || (width<(x+size/2)) || ((y-size/2)<0) || (height<(y+size/2)));
		//System.out.println("p: ("+x+", "+y+") c: ("+x+", "+y+")");
		juvenile.setPosition(x, y);
		consume(nutrientsNeededForBreeding);
		return juvenile;
	}

	public boolean isBreedable() {
	/*
	 * 繁殖
	 */
		if (getNutrients() > nutrientsNeededForBreeding
				&& breedRate < Math.random()) {
			return true;
		}
		return false;
	}

	public void setBreedRate(double rate) {
		breedRate = rate;
	}

}

import java.util.Vector;

class test{
	public static void main(String argv[]){
		Vector<Plant> plants = new Vector<Plant>();
		for(int i=0; i<10; i++){
			Plant plant = new Plant();
			plant.setNutrients(i);
			plants.add(plant);
			//System.out.println(i+": "+plants.get(i).getNutrients());
		}

		for(int i=0; i<10; i++){
			if(plants.get(i).getNutrients() % 2 == 0){
				plants.remove(i);
				i++;
			}
			System.out.println(i+": "+plants.get(i).getNutrients());
		}
	}
}

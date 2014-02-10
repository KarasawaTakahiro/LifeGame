import java.awt.Point;
import java.awt.Rectangle;
import java.util.ListIterator;

public class Animal extends Creature {

	public Animal(){
		super();
	}

	public void update(){
		super.update();
	}

	public boolean hitPlant(Plant plants[]){
		/**
		 *
		 */

		Rectangle animalRect = new Rectangle(getX()+getVx(), getY()+getVy(), getSize(), getSize());
		for(int i=0; i<plants.length; i++){
			if(plants[i] == null){
				break;
			}else if(animalRect.intersects(new Rectangle(plants[i].getX(), plants[i].getY(), plants[i].getSize(), plants[i].getSize()))){
				return true;
			}
		}
		return false;
	}



}

import java.awt.Dimension;
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

		Point movep = getMovePoint();
		Rectangle animalRect = new Rectangle(movep, new Dimension(getSize(), getSize()));
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

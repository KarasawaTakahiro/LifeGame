
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ListIterator;

public class Herbivore extends Animal {

	public Herbivore(){
		super();
		System.out.println("Herbivore");
		setColor(200, 10, 10);
		setActionFrequency(2);
	}

	public void update(){
		super.update();
	}

	public Point getMovePoint(Plant[] plants){
		if(hitPlant(plants)){
			reverse();
			return getMovePoint();
		}else{
			return super.getMovePoint();
		}
	}

	public void move(Plant[] plants){
		if(!moovable()){
			return;
		}
		setPosition(getMovePoint(plants));
		hitWall();
	}

}

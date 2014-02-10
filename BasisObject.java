import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class BasisObject{
	private int size;
	private Color color;
	private Point position;

	private long elapsedTime;  // 生まれてからの経過時間(フレーム)

	public BasisObject() {
		position = new Point(0, 0);
	}

	public void update() {
		/*
		 * called every frame
		 */
		elapsedTime += 1L;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval(getX(), getY(), size, size);
	}

	public long getElapsedTime(){
		return elapsedTime;
	}

	public void setColor(int r, int g, int b) {
		color = new Color(r, g, b);
	}

	public void setColor(Color c){
		color = c;
	}

	public Color getColor(){
		return color;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSize() {
		return this.size;
	}

	public void setPosition(Point pos){
		position = pos;
	}

	public void setPosition(int x, int y) {
		position.setLocation(x, y);
	}

	public Point getPosition(){
		return position;
	}

	public int getX() {
		return (int)(position.getX());
	}

	public int getY() {
		return (int)(position.getY());
	}

}

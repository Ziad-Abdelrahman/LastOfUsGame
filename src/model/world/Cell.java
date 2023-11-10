package model.world;

import java.io.Serializable;

public abstract class Cell implements Serializable{

	private static final long serialVersionUID = 1L;
	private boolean isVisible;
	private int x = 0;
	private int y = 0;
	public Cell() {
		
	}
	public boolean isVisible() {
		return isVisible;
	}
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}

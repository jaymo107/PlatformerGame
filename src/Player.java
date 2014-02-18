import java.awt.Color;
import java.awt.Graphics;


public class Player {
	
	private double x, y;
	private double dx, dy;
	
	private int width, height;
	private boolean left, right, jumping, falling;
	private double moveSpeed, maxSpeed, maxFallingSpeed, stopSpeed, jumpStart, gravity;
	private boolean topLeft, topRight, bottomLeft, bottomRight;
	
	private TileMap tm;
	
	public Player(TileMap tm){
		this.tm = tm;
		width = 20;
		height = 20;
		moveSpeed = 0.6;
		maxSpeed = 4;
		maxFallingSpeed = 12;
		stopSpeed = 0.30;
		jumpStart = -11.0;
		gravity = 0.64;
		
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public void setLeft(boolean b){
		left = b;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
	public void setRight(boolean b){
		right = b;
	}
	
	public void setJumping(boolean b){
		if(!falling){
			jumping = true;
		}
	}
	
	public void update(){
		//determine the next position
		if(left){
			dx -= moveSpeed;
			if(dx < -maxSpeed)
				dx = -maxSpeed;
		}else if(right){
			dx += moveSpeed;
			if(dx > maxSpeed)
				dx = maxSpeed;
		}else{
			if(dx > 0){
				dx -= stopSpeed;
				if(dx < 0){
					dx = 0;
				}
			}else if(dx < 0){
				dx += stopSpeed;
				if(dx > 0){
					dx = 0;
				}
			}
		}
		
		if(jumping){
			dy = jumpStart;
			falling = true;
			jumping = false;
		}
		
		if(falling){
			dy += gravity;
			if(dy > maxFallingSpeed){
				dy = maxFallingSpeed;
			}
		}else{
			dy = 0;
		}
		
		//check collisions
		int currCol = tm.getColTile((int)x);
		int currRow = tm.getRowTile((int)y);
		
		double toX = x + dx;
		double toY = y + dy;
		
		double tempx = x;
		double tempy = y;
		
		
		calculateCorners(x, toY);
		if(dy < 0){
			if(topLeft || topRight){
				dy = 0;
				tempy = currRow * tm.getTileSize() + height / 2;
			}else{
				tempy += dy;
			}
		}
		
		if(dy > 0){
			if(bottomLeft || bottomRight){
				dy = 0;
				falling = false;
				tempy = (currRow + 1) * tm.getTileSize() - height / 2;
			}else{
				tempy += dy;
			}
		}
		
		calculateCorners(toX, y);
		if(dx < 0){
			if(topLeft || bottomLeft){
				dx = 0;
				tempx = currCol * tm.getTileSize() + width / 2;
			}else{
				tempx += dx;
			}
		}
		
		if(dx > 0){
			if(topRight || bottomRight){
				dx = 0;
				tempx = (currCol + 1) * tm.getTileSize() - width / 2;
				
			}else{
				tempx += dx;
			}
		}
		
		if(!falling){
			calculateCorners(x, y + 1);
			if(!bottomLeft && !bottomRight){
				//we are falling
				falling = true;
			}
		}
		
		x = tempx;
		y = tempy;
	}
	
	private void calculateCorners(double x, double y){
		int leftTile = tm.getColTile((int) (x - width / 2));
		int rightTile = tm.getColTile((int) (x + width / 2) - 1);
		int topTile = tm.getRowTile((int)(y - height/ 2));
		int bottomTile = tm.getRowTile((int)(y + height / 2) - 1);
		
		topLeft = tm.getTile(topTile, leftTile) == 0;
		topRight = tm.getTile(topTile, rightTile) == 0;
		bottomLeft = tm.getTile(bottomTile, leftTile) == 0;
		bottomRight = tm.getTile(bottomTile, rightTile) == 0;
	}
	
	public void draw(Graphics g){
		int tx = tm.getX();
		int ty = tm.getY();
		
		g.setColor(Color.RED);
		g.fillRect((int)(tx + x - width / 2), (int)(ty + y - height / 2), width, height);
	}

}

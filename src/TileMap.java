import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;


public class TileMap {
	
	private int x = 0, y = 0;
	private int tileSize;
	private int[][] map;
	private int mapWidth;
	private int mapHeight;
	private BufferedReader br;
	
	public TileMap(String s, int tileSize){
		this.tileSize = tileSize;
		
		try{
			br = new BufferedReader(new FileReader(s));
			
			mapWidth = Integer.parseInt(br.readLine());
			mapHeight = Integer.parseInt(br.readLine());
			map = new int[mapHeight][mapWidth];
			String delimeter = " ";
			
			for(int row = 0; row < mapHeight; row++){
				String line = br.readLine();
				String[] tokens = line.split(delimeter);
				for(int col = 0; col < mapWidth; col++){
					map[row][col] = Integer.parseInt(tokens[col]);
					
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();		}
	}
	
	public void update(){
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public int getColTile(int x){
		return x / tileSize;
	}
	
	public int getRowTile(int y){
		return y / tileSize;
	}
	
	public int getTile(int row, int col){
		return map[row][col];
	}
	
	public int getTileSize(){
		return tileSize;
	}
	
	public void draw(Graphics g){
		for(int row = 0; row < mapHeight; row++){
			for(int col = 0; col < mapWidth; col++){
				int rc = map[row][col];
				if(rc == 0){
					g.setColor(Color.BLACK);
				}
				
				if(rc == 1){
					g.setColor(Color.WHITE);
				}
				
				g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
			}
		}
	}

}

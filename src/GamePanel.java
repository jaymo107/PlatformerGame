import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	public class Listener implements KeyListener{
		
		public boolean[] keys = new boolean[255];
		
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			keys[keyCode] = true;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			keys[keyCode] = false;
		}

		@Override
		public void keyTyped(KeyEvent e) {}
		
	}

	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 480, WIDTH = 640;
	private Thread thread;
	private boolean running;
	private Image dbImage;
	private Graphics dbg;
	private int fps;
	private int targetTime = fps/1000;
	private TileMap tm;
	private Player player;
	private Listener input;
	
	public GamePanel(){
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			thread.start();
		}
	}

	@Override
	public void run() {
		init();
		long startTime;
		long urdTime;
		long waitTime;
		
		while(running){
			startTime = System.nanoTime();
			update();
			urdTime = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - urdTime;
			
			try{
				Thread.sleep(waitTime);
				
			}catch(Exception e){}
		}
	}
	
	private void init(){
		running = true;
		new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		tm = new TileMap("src/testMap.txt", 32);
		player = new Player(tm);
		player.setX(50);
		player.setY(50);
		input = new Listener();
		addKeyListener(input);
	}
	
	private void update(){
		tm.update();
		player.update();
		
		if(input.keys[KeyEvent.VK_A] == true){
			player.setX(player.getX() + 0.1d);
		}
		
		if(input.keys[KeyEvent.VK_D] == true){
			player.setX(player.getX() - 0.1d);
		}
	}
	
	public void paintComponent(Graphics g){
		dbImage = createImage(WIDTH, HEIGHT);
		dbg = dbImage.getGraphics();
		g.drawImage(dbImage, 0,0,null);
		paint(dbg);
	}
	
	public void paint(Graphics g){
		tm.draw(g);
		player.draw(g);
		repaint();
	}
}

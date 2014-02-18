import javax.swing.JFrame;


public class Game extends JFrame{

	private static final long serialVersionUID = -7803629994015778818L;

	public Game(){
		setTitle("Demo Platformer");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setContentPane(new GamePanel());
		setResizable(false);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args){
		new Game();
	}

}

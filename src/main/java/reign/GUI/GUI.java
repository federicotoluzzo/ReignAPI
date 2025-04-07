package reign.GUI;

import reign.Reign;

import javax.swing.*;

//singleton per il meme giusto perchè ce l'ha spiegato e in questo contesto ha senso
public class GUI extends JFrame {
    private static GUI gui;
    private Reign reign;
    public static void main(String[] args) {
        gui = new GUI();
    }

    private GUI(){
        setContentPane(new CreatePanel());
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

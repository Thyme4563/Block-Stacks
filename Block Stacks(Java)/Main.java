import javax.swing.JFrame;

public class Main extends JFrame {
    public Main() {
        setTitle("Block Stacks");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        add(new Game());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setVisible(true);
    }
}
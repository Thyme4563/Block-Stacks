import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game extends JPanel implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
    }

    

    // Add these fields at class level
   private Timer timer;
   private static final int DELAY = 25;

   private void drawText(Graphics g) {
    g.setColor(Color.WHITE);
    g.setFont(new Font("Arial", Font.BOLD, 24));
    g.drawString("Block Stacks Game", 100, 100); // Display title text
    g.setFont(new Font("Arial", Font.PLAIN, 18));
    g.drawString("Use arrow keys to move the block", 100, 140); // Display instructions
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawText(g);
        drawBlocks(g);
    }

    private void drawBlocks(Graphics g) {
        // Draw current block
        if (block != null) {
            g.drawImage(block, block_x, block_y, this);
        }
        
        // Draw placed blocks
        for (int i = 0; i < blockCount; i++) {
            g.drawImage(block, x[i], y[i], this);
        }
    }

    private final int ALL_TILES = 1600;

    private final int x[] = new int[ALL_TILES];
    private final int y[] = new int[ALL_TILES];

    private Image block;
    private int block_x = 300;
    private int block_y = 200;
    private int blockCount = 0;

    public Game() {
        initBoard();
    }

    private void initBoard() {
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);
        setPreferredSize(new Dimension(600, 800));
        loadImages();
        initGame();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    private void loadImages() {
        try {
            ImageIcon iid = new ImageIcon(getClass().getResource("/block.png"));
            block = iid.getImage();
            block = block.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            if (block == null) {
                System.err.println("Failed to load block.png");
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    private void initGame() {
        saveCurrentBlock();
        block_x = 300;
        block_y = 200;
        blockCount = 0;
    }

    private void saveCurrentBlock() {
        x[blockCount] = block_x; // Use blockCount instead of 0
        y[blockCount] = block_y;
        blockCount++;
    }
    
    private void createNewBlock() {
        block_x = 300;
        block_y = 200;
    }

    private boolean falling = true;
    private final int FALL_SPEED = 5; // Adjust speed as needed
    private final int MOVE_SPEED = 10; // Adjust speed as needed

    private void move() {
        if (falling) {
            block_y += FALL_SPEED;
            if (block_y >= getHeight() - block.getHeight(null)) {
                falling = false;  // Stop falling when hitting bottom
                saveCurrentBlock();
                falling = true;   // Reset falling for next block
                createNewBlock();
            } else if (checkCollision()) {
                falling = false;  // Stop falling when hitting another block
                saveCurrentBlock();
                falling = true;   // Reset falling for next block
                createNewBlock();
            }
            repaint();
        }
    }
    

    private boolean checkCollision() {
        for (int i = 0; i < blockCount; i++) {
            // Check if blocks overlap
            if (Math.abs(block_x - x[i]) < block.getWidth(null) && 
                Math.abs(block_y - y[i]) < block.getHeight(null)) {
                return true;
            }
        }
        return false;
    }
    

    private final boolean rightDirection = false;
    private final boolean leftDirection = false;

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!rightDirection)) {
                block_x -= MOVE_SPEED;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!leftDirection)) {
                block_x += MOVE_SPEED;
            }
        }
    }
}
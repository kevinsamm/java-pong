import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window extends JFrame implements Runnable {
    private final Graphics2D g2;
    private String state = "start";
    private KL keyListener = new KL();
    private Rect playerOne;
    private Rect ia;
    private Rect ball;
    private PlayerController playerController;
    private Ball ballRect;
    private AiController aiController;
    private int leftScore;
    private int rightScore;
    private int scoreChanger;
    private Text leftScoreText;
    private Text rightScoreText;
    private final Font font;
    private Text pongTitle;
    private Text startGame;
    private Text exitGame;

    public Window() {
        this.setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        this.setTitle(Constants.SCREEN_TITLE);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(keyListener);
        Constants.TOOLBAR_HEIGHT = this.getInsets().top;
        Constants.INSETS_BOTTOM = this.getInsets().bottom;
        g2 = (Graphics2D) this.getGraphics();

        playerOne = new Rect(Constants.HZ_PADDING, 40, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ia = new Rect(Constants.SCREEN_WIDTH - Constants.HZ_PADDING - Constants.PADDLE_WIDTH, 40, Constants.PADDLE_WIDTH, Constants.PADDLE_HEIGHT, Constants.PADDLE_COLOR);
        ball = new Rect((double) Constants.SCREEN_WIDTH /2, (double) Constants.SCREEN_HEIGHT /2, Constants.BALL_WIDTH, Constants.BALL_WIDTH, Constants.PADDLE_COLOR);

        ballRect = new Ball(ball, playerOne, ia);

        playerController = new PlayerController(playerOne, keyListener);
        aiController = new AiController(new PlayerController(ia), ball);

        leftScore = 0;
        rightScore = 0;
        font = new Font("Consolas", Font.BOLD, 40);
        leftScoreText = new Text(String.valueOf(leftScore), font, (double) Constants.SCREEN_WIDTH / 2 - 50, 70);
        rightScoreText = new Text(String.valueOf(rightScore), font, (double) Constants.SCREEN_WIDTH / 2 + 30, 70);

        pongTitle = new Text("PONG GAME", new Font("Consolas", Font.BOLD, 70), 50.0, 220.0);
        startGame = new Text("start (S)", font, 50.0, 300.0);
        exitGame = new Text("exit (E)", font, 50.0, 350.0);
    }

    public void update(double dt) {
        Image dbImage = createImage(getWidth(), getHeight());
        Graphics dbg = dbImage.getGraphics();
        this.draw(dbg);
        g2.drawImage(dbImage, 0, 0, this);

        if (state.equals("game")) {
            playerController.update(dt);
            scoreChanger = ballRect.update(dt);
            aiController.update(dt);

            if (scoreChanger > 0) {
                leftScoreText.text = String.valueOf(++leftScore);
            } else if (scoreChanger < 0) {
                rightScoreText.text = String.valueOf(++rightScore);
            }
        } else {
            if (keyListener.isKeyPressed(KeyEvent.VK_S)) {
                state = "game";
            } else if (keyListener.isKeyPressed(KeyEvent.VK_E)) {
                System.exit(0);
            }
        }
    }

    public void draw (Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        if (state.equals("game")) {
            g2.setColor(Color.WHITE);
            g2.drawLine(Constants.SCREEN_WIDTH / 2, 0, Constants.SCREEN_WIDTH / 2, Constants.SCREEN_HEIGHT);
            playerOne.draw(g2);
            ia.draw(g2);
            ball.draw(g2);
            leftScoreText.draw(g2);
            rightScoreText.draw(g2);
        } else {
            pongTitle.draw(g2);
            startGame.draw(g2);
            exitGame.draw(g2);
        }
    }

    @Override
    public void run() {
        double lastFrameTime = 0.0;
        while (true) {
            double time = Time.getTime();
            double deltaTime = time - lastFrameTime;
            lastFrameTime = time;

            update(deltaTime);
        }
    }
}

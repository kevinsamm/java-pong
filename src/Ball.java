import java.util.Random;

public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    private Random random = new Random();
    private double vy = -120 + random.nextInt(240);
    private double vx = -180.0 + 360 * random.nextInt(2);

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle) {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
    }

    public void update(double dt) {
        if (vx < 0) {
            if (rect.x <= leftPaddle.x + leftPaddle.width && rect.y >= leftPaddle.y && rect.y <= leftPaddle.y + leftPaddle.height) {
                vx *= -1;
                //vy *= -1;
            } else if (rect.x < 10) {
                System.out.println("You lost a point");
                resetPosition();
            }
        } else if (vx > 0) {
            if (rect.x + rect.width >= rightPaddle.x && rect.y >= rightPaddle.y && rect.y <= rightPaddle.y + rightPaddle.height) {
                vx *= -1;
                //vy *= -1;
            } else if (rect.x > Constants.SCREEN_WIDTH - 10) {
                System.out.println("IA has lost a point");
                resetPosition();
            }
        }
        if (vy > 0) {
            if (rect.y + rect.height > Constants.SCREEN_HEIGHT) {
                vy *= -1;
            }
        } else if (vy < 0) {
            if (rect.y < Constants.TOOLBAR_HEIGHT) {
                vy *= -1;
            }
        }
        this.rect.x += vx * dt;
        this.rect.y += vy * dt;
    }

    public void resetPosition() {
        rect.x = (double) Constants.SCREEN_WIDTH /2;
        rect.y = (double) Constants.SCREEN_HEIGHT /2;
        vy = -120 + random.nextInt(240);
        vx = -180.0 + 360 * random.nextInt(2);
    }
}

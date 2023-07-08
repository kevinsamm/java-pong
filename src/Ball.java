import java.util.Random;

public class Ball {
    public Rect rect;
    public Rect leftPaddle, rightPaddle;
    private Random random = new Random();
    private double vy = -1 * Constants.BALL_SPEED + random.nextInt((int) Constants.BALL_SPEED);
    private double vx = -1 * Constants.BALL_SPEED;

    public Ball(Rect rect, Rect leftPaddle, Rect rightPaddle) {
        this.rect = rect;
        this.leftPaddle = leftPaddle;
        this.rightPaddle = rightPaddle;
    }

    public int update(double dt) {
        if (vx < 0) {
            if (rect.x <= leftPaddle.x + leftPaddle.width && rect.y >= leftPaddle.y && rect.y <= leftPaddle.y + leftPaddle.height) {
                double theta = calculateNewVelocityAngle(leftPaddle);
                double newVx = Math.abs(Math.cos(theta) * Constants.BALL_SPEED);
                double newVy = -Math.sin(theta) * Constants.BALL_SPEED;
                vx = newVx;
                vy = newVy;
                //vy *= -1;
            } else if (rect.x < 10) {
                System.out.println("You lost a point");
                resetPosition();
                return -1;
            }
        } else if (vx > 0) {
            if (rect.x + rect.width >= rightPaddle.x && rect.y >= rightPaddle.y && rect.y <= rightPaddle.y + rightPaddle.height) {
                double theta = calculateNewVelocityAngle(rightPaddle);
                double newVx = Math.abs(Math.cos(theta) * Constants.BALL_SPEED);
                double newVy = -Math.sin(theta) * Constants.BALL_SPEED;
                vx = -newVx;
                vy = newVy;
                //vy *= -1;
            } else if (rect.x > Constants.SCREEN_WIDTH - 10) {
                System.out.println("IA has lost a point");
                resetPosition();
                return 1;
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
        return 0;
    }

    public double calculateNewVelocityAngle(Rect paddle) {
        double relativeIntersectY = (paddle.y + (paddle.height / 2.0)) - (this.rect.y + (this.rect.height / 2.0));
        double normalIntersectY = relativeIntersectY / (paddle.height / 2.0);
        double theta = normalIntersectY * Constants.MAX_ANGLE;
        return Math.toRadians(theta);
    }

    public void resetPosition() {
        rect.x = (double) Constants.SCREEN_WIDTH /2;
        rect.y = (double) Constants.SCREEN_HEIGHT /2;
        vy = -1 * Constants.BALL_SPEED + random.nextInt((int) Constants.BALL_SPEED);
    }
}

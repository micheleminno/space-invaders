import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends Entity {

    private double speed;
    private boolean moveLeft;
    private boolean moveRight;

    public Player(double x, double y) {
        super(x, y, 50, 20);
        this.speed = 10;
    }

    public void setMoveLeft(boolean value) {
        moveLeft = value;
    }

    public void setMoveRight(boolean value) {
        moveRight = value;
    }

    @Override
    public void update() {
        if (moveLeft) {
            x -= speed;
        }
        if (moveRight) {
            x += speed;
        }

        if (x < 0) {
            x = 0;
        }
        if (x + width > 800) {
            x = 800 - width;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.DEEPSKYBLUE);
        gc.fillRect(x, y, width, height);
    }

    public Bullet shoot() {
        return new Bullet(x + width / 2 - 2, y - 10);
    }
}
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Entity {

    private double speed;

    public Bullet(double x, double y) {
        super(x, y, 5, 12);
        this.speed = 8;
    }

    @Override
    public void update() {
        y -= speed;
        if (y + height < 0) {
            alive = false;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GOLD);
        gc.fillRect(x, y, width, height);
    }
}
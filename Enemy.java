import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends Entity {

    private double speed;

    public Enemy(double x, double y, double speed) {
        super(x, y, 35, 35);
        this.speed = speed;
    }

    @Override
    public void update() {
        y += speed;
        if (y > 600) {
            alive = false;
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.CRIMSON);
        gc.fillRect(x, y, width, height);
    }
}
import javafx.scene.canvas.GraphicsContext;

public abstract class Entity {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected boolean alive;

    public Entity(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.alive = true;
    }

    public abstract void update();

    public abstract void draw(GraphicsContext gc);

    public boolean isAlive() {
        return alive;
    }

    public void destroy() {
        alive = false;
    }

    public boolean intersects(Entity other) {
        return x < other.x + other.width &&
               x + width > other.x &&
               y < other.y + other.height &&
               y + height > other.y;
    }
}
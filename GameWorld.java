import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class GameWorld {

    private GraphicsContext gc;
    private int width;
    private int height;

    private Player player;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;

    private AnimationTimer timer;

    private int score;
    private int level;
    private int enemiesDestroyedInLevel;
    private long lastShotTime;
    private long lastSpawnTime;
    private boolean gameOver;

    public GameWorld(GraphicsContext gc, int width, int height) {
        this.gc = gc;
        this.width = width;
        this.height = height;

        player = new Player(375, 540);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();

        score = 0;
        level = 1;
        enemiesDestroyedInLevel = 0;
        lastShotTime = 0;
        lastSpawnTime = 0;
        gameOver = false;

        createTimer();
    }

    private void createTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(now);
                draw();
            }
        };
    }

    public void start() {
        timer.start();
    }

    private void update(long now) {
        if (gameOver) {
            return;
        }

        player.update();

        spawnEnemy(now);

        for (Bullet b : bullets) {
            b.update();
        }

        for (Enemy e : enemies) {
            e.update();
        }

        checkCollisions();
        removeDeadEntities();
        checkGameOver();
        checkLevelUp();
    }

    private void spawnEnemy(long now) {
        long interval = Math.max(300_000_000L, 1_200_000_000L - (level - 1) * 150_000_000L);

        if (now - lastSpawnTime > interval) {
            double x = Math.random() * (width - 35);
            double speed = 1.2 + level * 0.4;
            enemies.add(new Enemy(x, 40, speed));
            lastSpawnTime = now;
        }
    }

    private void checkCollisions() {
        for (Bullet b : bullets) {
            for (Enemy e : enemies) {
                if (b.isAlive() && e.isAlive() && b.intersects(e)) {
                    b.destroy();
                    e.destroy();
                    score += 10;
                    enemiesDestroyedInLevel++;
                }
            }
        }
    }

    private void removeDeadEntities() {
        bullets.removeIf(b -> !b.isAlive());
        enemies.removeIf(e -> !e.isAlive());
    }

    private void checkGameOver() {
        for (Enemy e : enemies) {
            if (e.y + e.height >= player.y) {
                gameOver = true;
                timer.stop();
            }
        }
    }

    private void checkLevelUp() {
        if (enemiesDestroyedInLevel >= 10) {
            level++;
            enemiesDestroyedInLevel = 0;
        }
    }

    private void draw() {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);

        player.draw(gc);

        for (Enemy e : enemies) {
            e.draw(gc);
        }

        for (Bullet b : bullets) {
            b.draw(gc);
        }

        gc.setFill(Color.WHITE);
        gc.fillText("Punteggio: " + score, 10, 20);
        gc.fillText("Livello: " + level, 10, 40);

        if (gameOver) {
            gc.setFill(Color.ORANGE);
            gc.fillText("Hai perso ahahahahah", 350, 300);
        }
    }

    public void onKeyPressed(KeyCode code) {
        if (code == KeyCode.LEFT) {
            player.setMoveLeft(true);
        }

        if (code == KeyCode.RIGHT) {
            player.setMoveRight(true);
        }

        if (code == KeyCode.SPACE) {
            long now = System.nanoTime();
            if (now - lastShotTime > 250_000_000L && !gameOver) {
                bullets.add(player.shoot());
                lastShotTime = now;
            }
        }
    }

    public void onKeyReleased(KeyCode code) {
        if (code == KeyCode.LEFT) {
            player.setMoveLeft(false);
        }

        if (code == KeyCode.RIGHT) {
            player.setMoveRight(false);
        }
    }
}
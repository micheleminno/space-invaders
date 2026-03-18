import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameApp extends Application {

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        GameWorld world = new GameWorld(gc, 800, 600);

        Scene scene = new Scene(new StackPane(canvas));

        scene.setOnKeyPressed(e -> world.onKeyPressed(e.getCode()));
        scene.setOnKeyReleased(e -> world.onKeyReleased(e.getCode()));

        stage.setTitle("Mini Space Game");
        stage.setScene(scene);
        stage.show();

        world.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
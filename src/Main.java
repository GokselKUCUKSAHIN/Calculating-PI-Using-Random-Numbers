import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci Calculating PI";
    public static final int width = 800;
    public static final int height = 800;

    static GraphicsContext gc;

    private static Color backcolor = Color.rgb(51, 51, 51);

    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();

        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        child.addAll(canvas);
        //
        drawBorder();
        shootDots(400, 400, Color.RED);

        //
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            //System.out.println("loop test");
            for (int i = 0; i < 100; i++)
            {
                shootDots(Utils.getRandom(50, width - 50), Utils.getRandom(50, height - 50), Color.LIME);
            }
            drawBorder();
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root, width - 10, height - 10, backcolor));
        stage.show();
        root.requestFocus();
    }

    private static void drawBorder()
    {
        gc.setStroke(Color.SNOW);
        gc.setLineWidth(3);
        gc.strokeRect(50, 50, 700, 700);
        gc.setLineWidth(1);
        gc.strokeOval(50, 50, 700, 700);
    }

    private static void clearFrame()
    {
        gc.clearRect(0, 0, width, height);
    }

    private static void shootDots(double x, double y, Color color)
    {
        gc.setStroke(color);
        gc.setLineWidth(1.0);
        gc.strokeRect(x + 0.5, y + 0.5, 0.5, 0.5);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
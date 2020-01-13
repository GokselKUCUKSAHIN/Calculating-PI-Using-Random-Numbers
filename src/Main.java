import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
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

    private static final double CENTER_X = width / 2;
    private static final double CENTER_Y = height / 2;
    private static GraphicsContext gc;
    private static Color backcolor = Color.rgb(51, 51, 51);

    private static Timeline update;

    private static int circleCount = 0;
    private static int totalCount = 0;
    private static double estimation = 0;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        //
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);
        child.addAll(canvas);
        //
        drawBorder();
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
            for (int i = 0; i < 10000; i++)
            {
                shootDots();
            }
            makeEstimation();
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

    private static void drawDot(double x, double y, Color color)
    {
        gc.setStroke(color);
        gc.setLineWidth(1.0);
        gc.strokeRect(x + 0.5, y + 0.5, 0.5, 0.5);
    }

    private static boolean isInTheCircle(double x, double y)
    {
        return (Utils.fastDistance(x, y, CENTER_X, CENTER_Y) <= 122500) ? true : false;
    }

    private static void shootDots()
    {
        double rx = Utils.getRandom(50, 750);
        double ry = Utils.getRandom(50, 750);
        if (isInTheCircle(rx, ry))
        {
            drawDot(rx, ry, Color.BLUE);
            circleCount++;
        } else
        {
            drawDot(rx, ry, Color.LIME);
        }
        totalCount++;
    }

    private static void makeEstimation()
    {
        estimation = 4 * (circleCount / (double) totalCount);
        System.out.printf("PI: %1.25f\tC: %6d\tA: %6d\n", estimation, circleCount, totalCount);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
import javafx.application.Application;
import javafx.stage.Stage;
import model.map.World;
import presenter.Presenter;

public class DalekiApp extends Application {

	private World world;
	private Presenter presenter;

	private final int worldWidth = 11;
	private final int worldHeight = 11;

	@Override
	public void start(Stage primaryStage){
		this.world = new World(worldWidth, worldHeight);
		this.presenter = new Presenter(primaryStage, world);
	}

	public static void main(String[] args) {
		launch(args);
	}

}

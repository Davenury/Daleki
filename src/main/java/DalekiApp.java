import javafx.application.Application;
import javafx.stage.Stage;
import model.map.World;
import presenter.Presenter;

public class DalekiApp extends Application {

	private World world;
	private Presenter presenter;

	@Override
	public void start(Stage primaryStage){
		this.world = new World(10, 10);
		this.presenter = new Presenter(primaryStage, world);
	}

	public static void main(String[] args) {
		launch(args);
	}

}

import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.GuiceModule;
import javafx.application.Application;
import javafx.stage.Stage;
import model.map.World;
import presenter.Presenter;

import java.io.FileNotFoundException;

public class DalekiApp extends Application {

	private World world;
	private Presenter presenter;

	@Override
	public void start(Stage primaryStage) {
		Injector injector = Guice.createInjector(new GuiceModule());
		this.world = injector.getInstance(World.class);
		this.presenter = injector.getInstance(Presenter.class);
		this.presenter.setUpPresenter(primaryStage, world);
	}

	public static void main(String[] args) {
		launch(args);
	}

}

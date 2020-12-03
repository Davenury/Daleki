import com.google.inject.Guice;
import com.google.inject.Injector;
import guice.GuiceModule;
import javafx.application.Application;
import javafx.stage.Stage;
import model.map.World;
import model.map.WorldFactory;
import presenter.Presenter;
import presenter.PresenterFactory;

public class DalekiApp extends Application {

	private World world;
	private Presenter presenter;

	@Override
	public void start(Stage primaryStage){
		Injector injector = Guice.createInjector(new GuiceModule());
		this.world = WorldFactory.createWorld(injector);
		this.presenter = PresenterFactory.createPresenter(primaryStage, this.world, injector);
	}

	public static void main(String[] args) {
		launch(args);
	}

}

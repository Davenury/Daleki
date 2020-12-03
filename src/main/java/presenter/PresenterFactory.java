package presenter;

import com.google.inject.Injector;
import javafx.stage.Stage;
import model.map.World;

public class PresenterFactory {

    public static Presenter createPresenter(Stage primaryStage, World world, Injector injector){
        Presenter presenter = injector.getInstance(Presenter.class);
        presenter.setUpPresenter(primaryStage, world);
        return presenter;
    }
}

package model.map;

import com.google.inject.Injector;

public class WorldFactory {

    public static World createWorld(Injector injector){
        World world = injector.getInstance(World.class);
        world.setInjector(injector);
        world.generateExampleGame();
        return world;
    }
}

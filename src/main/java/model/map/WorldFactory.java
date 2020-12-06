package model.map;

import com.google.inject.Injector;

public class WorldFactory {

    private static World world;

    public static World createWorld(Injector injector){
        world = injector.getInstance(World.class);
        world.setInjector(injector);
        resetWorld();
        return world;
    }

    public static void resetWorld(){
        world.generateExampleGame();
    }
}

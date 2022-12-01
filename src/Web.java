import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Web extends ActivityEntity {

    private static final int Egg_MIN = 13000;
    private static final int Egg_MAX = 17000;
    private static final String Egg_KEY = "Egg";
    private static final String Egg_ID_PREFIX = "Egg -- ";
    public static final Random rand = new Random();

    public Web(String id, Point position,
                    List<PImage> images, int actionPeriod)
    {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround(this.getPosition());

        if (openPt.isPresent())
        {
            Egg Egg = new Egg(Egg_ID_PREFIX + this.getId(),
                    openPt.get(), Egg_MIN + rand.nextInt(Egg_MAX - Egg_MIN),
                    imageStore.getImageList(Egg_KEY));
            world.addEntity(Egg);
            (Egg).scheduleActions(scheduler, world, imageStore);
        }

        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }
}
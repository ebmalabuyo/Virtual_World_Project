import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Egg extends ActivityEntity{

    public static final Random rand = new Random();

    public Egg(String id, Point position, int actionPeriod, List<PImage> images) {
        super(id, position, images, actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPosition();  // store current position before removing

        this.removeEntity(world, this);
        scheduler.unscheduleAllEvents(this);

        Entity Spider = new Spider(this.getId() + " -- Spider", pos, this.getActionPeriod() / 20, 10 + rand.nextInt(10), imageStore.getImageList("Spider"), new SingleStepPathingStrategy());

        world.addEntity(Spider);
        ((ActivityEntity)Spider).scheduleActions(scheduler, world, imageStore);
    }
    //    private static final String Spider_KEY = "Spider";
//    private static final String Spider_ID_SUFFIX = " -- Spider";
//    private static final int Spider_PERIOD_SCALE = 4;
//    private static final int Spider_ANIMATION_MIN = 50;
//    private static final int Spider_ANIMATION_MAX = 150;
}
import processing.core.PImage;

import java.util.List;

public class Poof extends AnimationEntity{

    private static final String Poof_ID = "Poof";
    private static final int Poof_ACTION_PERIOD = 1100;
    private static final int Poof_ANIMATION_PERIOD = 100;
    public static final int Poof_ANIMATION_REPEAT_COUNT = 1;

    public Poof(Point position, List<PImage> images)
    {
        super(Poof_ID, position, images, Poof_ACTION_PERIOD, Poof_ANIMATION_PERIOD);

    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        this.removeEntity(world, this);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new ActivityAction(this, world, imageStore),
                Poof_ACTION_PERIOD);
        scheduler.scheduleEvent(this,
                new AnimationAction(this, Poof_ANIMATION_REPEAT_COUNT), getAnimationPeriod());
    }
}
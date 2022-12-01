import processing.core.PImage;

import java.util.List;

public class Food extends AnimationEntity {
    public static final int Food_ANIMATION_REPEAT_COUNT = 7;

    public Food(String id, Point position,
                    List<PImage> images)
    {
        super(id, position, images, 0, 0);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        this.removeEntity(world, this);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new AnimationAction(this, Food_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }
}

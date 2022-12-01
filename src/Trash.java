import processing.core.PImage;

import java.util.List;

public class Trash extends AnimationEntity{
    public static final int Trash_ANIMATION_REPEAT_COUNT = 7;

    public Trash(String id, Point position,
                    List<PImage> images)
    {
        super(id, position, images, 0, 0);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new AnimationAction(this, Trash_ANIMATION_REPEAT_COUNT),
                getAnimationPeriod());
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        scheduler.unscheduleAllEvents(this);
        this.removeEntity(world, this);
    }


}
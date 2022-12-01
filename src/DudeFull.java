
import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public class DudeFull extends Dude {
    public DudeFull(String id, Point position, List<PImage> images, int actionPeriod, PathingStrategy p) {
        super(id, position, images, actionPeriod, 0, p);
    }

    public Dude transformHelper() {
        DudeNotFull Dude = new DudeNotFull(this.getId(),
                this.getPosition(),
                this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getImages(),
                new AStarPathingStrategy());
        return Dude;
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(this.getPosition(), Trash.class);
        if (fullTarget.isPresent() && this.moveTo(world, (Entity)fullTarget.get(), scheduler)) {
            ((ActivityEntity)fullTarget.get()).scheduleActions(scheduler, world, imageStore);
            this.transform(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore), (long)this.getActionPeriod());
        }

    }


}

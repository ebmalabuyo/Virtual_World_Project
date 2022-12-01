//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.List;
import java.util.Optional;
import processing.core.PImage;

public class DudeNotFull extends Dude {
    public DudeNotFull(String id, Point position, int actionPeriod, int animationPeriod, List<PImage> images, PathingStrategy p) {
        super(id, position, images, actionPeriod, animationPeriod, p);
    }

    public Dude transformHelper() {
        if (this.getResourceCount() >= 2) {
            DudeFull Dude = new DudeFull(this.getId(), this.getPosition(), this.getImages(), this.getActionPeriod(), this.p);
            return Dude;
        } else {
            return null;
        }
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> notFullTarget = world.findNearest(this.getPosition(), Spider.class);

        if (!notFullTarget.isPresent() ||
                !this.moveTo(world, notFullTarget.get(), scheduler) || !
                this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this,
                    new ActivityAction(this, world, imageStore),
                    this.getActionPeriod());
            this.setResourceCount(this.getResourceCount()+1);
        }
//        if (this.moveTo(world, (Entity)notFullTarget.get(), scheduler)) {
//            Entity Poof = new Poof(notFullTarget.get().getPosition(), imageStore.getImageList("Poof"));
//
//            world.addEntity(Poof);
//            ((ActivityEntity)Poof).scheduleActions(scheduler, world, imageStore);
//        }

    }


}

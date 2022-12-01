import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Player extends Move {

    public Player(String id, Point position,
                List<PImage> images)
    {
        super(id, position, images, 0, 0, new SingleStepPathingStrategy());
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        this.setPosition(destPos);
        return destPos;
    }

    public Food createFood(Point pos, ImageStore imageStore) {
        return new Food("food", pos, imageStore.getImageList("Food"));
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
//        Optional<Entity> SpiderTarget = world.findNearest(this.getPosition(), Spider.class);
//        long nextPeriod = this.getActionPeriod();
//
//        if (SpiderTarget.isPresent())
//        {
//            Point tgtPos = SpiderTarget.get().getPosition();
//
//            if (moveTo(world, SpiderTarget.get(), scheduler))
//            {
////                Entity Poof = new Poof(tgtPos, imageStore.getImageList("Poof"));
////
////                world.addEntity(Poof);
//                nextPeriod += this.getActionPeriod();
////                ((ActivityEntity)Poof).scheduleActions(scheduler, world, imageStore);
//            }
//        }
//        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
//                nextPeriod);
//    }
}

}

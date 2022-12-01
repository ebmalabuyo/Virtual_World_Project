import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Spider extends Move{

    private static final String Poof_KEY = "Poof";

    public Spider(String id, Point position,
                int actionPeriod, int animationPeriod, List<PImage> images, PathingStrategy p)
    {
        super(id, position, images, actionPeriod, animationPeriod, p);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> SpiderTarget = world.findNearest(this.getPosition(), Food.class);
        long nextPeriod = this.getActionPeriod();

        if (SpiderTarget.isPresent())
        {
            Point tgtPos = SpiderTarget.get().getPosition();

            if (moveTo(world, SpiderTarget.get(), scheduler))
            {
                Entity Poof = new Poof(tgtPos, imageStore.getImageList(Poof_KEY));

                world.addEntity(Poof);
                nextPeriod += this.getActionPeriod();
                ((ActivityEntity)Poof).scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this, new ActivityAction(this, world, imageStore),
                nextPeriod);
    }

    public Point nextPosition(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get() instanceof Food)) )
        {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x, this.getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get() instanceof Food)))
            {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }


}
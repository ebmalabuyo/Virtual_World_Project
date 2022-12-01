import processing.core.PImage;

import java.util.List;

public abstract class Dude extends Move {
    private static int resourceLimit = 2;
    private static int resourceCount = 0;

    public Dude(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, PathingStrategy strategy) {
        super(id, position, images, actionPeriod, animationPeriod, strategy);
    }

    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPosition().x);
        Point newPos = new Point(this.getPosition().x + horiz,
                this.getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPosition().y);
            newPos = new Point(this.getPosition().x,
                    this.getPosition().y + vert);
            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPosition();
            }
        }
        return newPos;
    }

    public boolean transform(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Dude dude = transformHelper();

        if (dude != null){
            this.removeEntity(world, this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }
        return false;
    }

    protected abstract Dude transformHelper();

    public int getResourceLimit() { return resourceLimit; }
    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }
    public int getResourceCount() {
        return resourceCount;
    }
}
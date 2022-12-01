import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import processing.core.*;
import java.util.Optional;
import java.util.Set;
public final class VirtualWorld
        extends PApplet
{
    private static final int TIMER_ACTION_PERIOD = 100;
    private static final String FAST_FLAG = "-fast";
    private static final String FASTER_FLAG = "-faster";
    private static final String FASTEST_FLAG = "-fastest";
    private static final double FAST_SCALE = 0.5;
    private static final double FASTER_SCALE = 0.25;
    private static final double FASTEST_SCALE = 0.10;
    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 480;
    private static final int TILE_WIDTH = 32;
    private static final int TILE_HEIGHT = 32;
    private static final int WORLD_WIDTH_SCALE = 2;
    private static final int WORLD_HEIGHT_SCALE = 2;
    private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;
    private static final String IMAGE_LIST_FILE_NAME = "./projects/projectFinal/imagelist";
    private static final int DEFAULT_IMAGE_COLOR = 0x808080;
    private static final String LOAD_FILE_NAME = "./projects/projectFinal/world.sav";
    private static double timeScale = 1.0;
    private static final int RADIUS = 2;
    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;

    private long next_time;

    public void settings()
    {
        size(VIEW_WIDTH, VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup()
    {
        this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(WORLD_ROWS, WORLD_COLS, Background.createDefaultBackground(imageStore));
        this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
        this.scheduler = new EventScheduler(timeScale);

        imageStore.loadImages(IMAGE_LIST_FILE_NAME, this);
        loadWorld(world, LOAD_FILE_NAME, imageStore);

        scheduleActions(scheduler, imageStore);

        next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
    }

    public void draw()
    {
        long time = System.currentTimeMillis();
        if (time >= next_time)
        {
            scheduler.updateOnTime(time);
            next_time = time + TIMER_ACTION_PERIOD;
        }
        view.drawViewport();
    }

    public void mousePressed() {
        boolean check = false;

        Point pressed = mouseToPoint(mouseX, mouseY);
        System.out.println("CLICK! " + pressed.x + ", " + pressed.y);

        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (entityOptional.isPresent())
        {
            Entity entity = entityOptional.get();
            System.out.println(entity.getId() + ": " + entity.getClass() + " : ");
        }
        else {
            eventNewEntity(pressed);
            check = true;
        }
        if (check) {
            newBackgroundEvent(pressed);
        }

    }

    // create web that releases eggs to surrounding tiles
    // effects more than two tiles
    private void eventNewEntity(Point pressed) {
        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if(!(entityOptional.isPresent())){
        ActivityEntity web = new Web("web", pressed, imageStore.getImageList("Web"), 300);
        world.addEntity(web);
        web.scheduleActions(scheduler, world, imageStore);
    }}
    private void newBackgroundEvent(Point pressed) {
        Background webback = new Background("redbrick", imageStore.getImageList("redbrick"));
        for(int y = -1; y < RADIUS; y++)
        {
            for(int x = -1; x < RADIUS; x++)
            {
                Point areaP = new Point(pressed.x + x, pressed.y + y);
                Optional<Entity> entityOptional = world.getOccupant(areaP);
                if(!(entityOptional.isPresent())) {
                    if (x == 0 && y == 0) {
                        world.setBackground(pressed, webback);
                    } else {
                        world.setBackground(areaP, webback);
                    }
                }
            }
        }

    }

    private Point mouseToPoint(int x, int y)
    {
        return this.view.getViewport().viewportToWorld(mouseX/TILE_WIDTH, mouseY/TILE_HEIGHT);
    }



/// CONTROLS
    public void keyPressed()
    {
        if (key == CODED)
        {
            Set<Entity> list = world.getEntities();
            int dx = 0;
            int dy = 0;
            int px = 0;
            switch (keyCode)
            {
                case UP:
                    dy = -1;
                    break;
                case DOWN:
                    dy = 1;
                    break;
                case LEFT:
                    dx = -1;
                    break;
                case RIGHT:
                    dx = 1;
                    break;
                case SHIFT:
                    px = 2;
                    break;
                case CONTROL:
                    px = 3;
                    break;

            }
            // ACCESS Player object
            for (Entity obj: list) {
                if (obj instanceof Player){

                    // ADDS FOOD OBJECT
                    if (px == 2) {
                        Point next = new Point(obj.getPosition().x + 1, obj.getPosition().y);
                        Optional<Entity> entityOptional = world.getOccupant(next);
                        if(!(entityOptional.isPresent())){
                            Food extraFood = new Food("Foodcreated", next, imageStore.getImageList("Food"));
                            world.addEntity(extraFood);}
                        return;
                    }
                    // REMOVES SPIDER
                    if (px == 3) {
                        Point next = new Point(obj.getPosition().x + 1, obj.getPosition().y);
                        Optional<Entity> entityOptional = world.getOccupant(next);
                        if(entityOptional.isPresent()){
                            Entity Item = entityOptional.get();
                            if (Item instanceof Spider) {
                                Item.removeEntity(world, Item);
                                Poof poof = new Poof(next, imageStore.getImageList("Poof"));
                                world.addEntity(poof);
                                ((ActivityEntity) poof).scheduleActions(scheduler, world, imageStore);
                                return;
                            }
                        }
                    }
                    Point next = new Point(obj.getPosition().x + dx, obj.getPosition().y + dy);
                    if (world.withinBounds(next) && !(world.isOccupied(next))) {
                    ((Player) obj).nextPosition(world, next);
                    view.shiftView(dx, dy);

                    world.moveEntity(obj, next);}
                }
            }
        }
    }

//    public void movePlayer() {
//
//        if (key == CODED) {
//            int dx = 0;
//            int dy = 0;
//            Set<Entity> list = world.getEntities();
//
//
//            switch (keyCode)
//            {
//                case 87:
//                    dy = -1;
//                    break;
//                case 83:
//                    dy = 1;
//                    break;
//                case 65:
//                    dx = -1;
//                    break;
//                case 68:
//                    dx = 1;
//                    break;
//            }
//            for (Entity obj: list) {
//                if (obj instanceof Player){
//                    Point next = new Point(obj.getPosition().x + dx, obj.getPosition().y + dy);
//                    ((Player) obj).nextPosition(world, next);
//                    world.moveEntity(obj, next);
//                }
//            }
//            System.out.print("working");
//
//
//    }}

    public static void loadWorld(WorldModel world, String filename,
                                 ImageStore imageStore)
    {
        try
        {
            Scanner in = new Scanner(new File(filename));
            Parse.load(in, world, imageStore);
        }
        catch (FileNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public PImage createImageColored(int width, int height, int color)
    {
        PImage img = new PImage(width, height, RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++)
        {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    public static void parseCommandLine(String [] args)
    {
        for (String arg : args)
        {
            switch (arg)
            {
                case FAST_FLAG:
                    timeScale = Math.min(FAST_SCALE, timeScale);
                    break;
                case FASTER_FLAG:
                    timeScale = Math.min(FASTER_SCALE, timeScale);
                    break;
                case FASTEST_FLAG:
                    timeScale = Math.min(FASTEST_SCALE, timeScale);
                    break;
            }
        }
    }

    public static void main(String [] args)
    {
        parseCommandLine(args);
        PApplet.main(VirtualWorld.class);
    }

    public void scheduleActions(EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities())
        {
            //Only start actions for entities that include action (not those with just animations)
            if (entity instanceof ActivityEntity )//.getActionPeriod() > 0)
                ((ActivityEntity)entity).scheduleActions(scheduler, world, imageStore);
        }
    }
}
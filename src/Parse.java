//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.util.Scanner;

public class Parse {


    public Parse() {
    }

    public static void load(Scanner in, WorldModel world, ImageStore imageStore) {
        for(int lineNumber = 0; in.hasNextLine(); ++lineNumber) {
            try {
                if (!processLine(in.nextLine(), world, imageStore)) {
                    System.err.println(String.format("invalid entry on line %d", lineNumber));
                }
            } catch (NumberFormatException var5) {
                System.err.println(String.format("invalid entry on line %d", lineNumber));
            } catch (IllegalArgumentException var6) {
                System.err.println(String.format("issue on line %d: %s", lineNumber, var6.getMessage()));
            }
        }

    }

    private static boolean processLine(String line, WorldModel world, ImageStore imageStore) {
        String[] properties = line.split("\\s");
        if (properties.length > 0) {
            switch (properties[0]) {
                case "background":
                    return parseBackground(properties, world, imageStore);
                case "Dude":
                    return parseDude(properties, world, imageStore);
                case "obstacle":
                    return parseObstacle(properties, world, imageStore);
                case "Egg":
                    return parseEgg(properties, world, imageStore);
                case "Trash":
                    return parseTrash(properties, world, imageStore);
                case "Web":
                    return parseWeb(properties, world, imageStore);
                case  "Food":
                    return parseFood(properties, world, imageStore);
                case "Player":
                    return parsePlayer(properties, world, imageStore);
//                case "EggDude":
//                    parseEggDude(properties, world, imageStore);
            }
        }

        return false;
    }
    private static boolean parseWeb(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 5) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new Web(properties[1], pt, imageStore.getImageList("Web"), Integer.parseInt(properties[4]));
            world.tryAddEntity(entity);
        }

        return properties.length == 5;
    }
    private static boolean parseBackground(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            String id = properties[1];
            world.setBackground(pt, new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == 4;
    }

    private static boolean parsePlayer(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new Player(properties[1], pt, imageStore.getImageList("Player"));
            world.tryAddEntity(entity);
        }

        return properties.length == 4;
    }
    private static boolean parseDude(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 7) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new DudeNotFull(properties[1], pt, Integer.parseInt(properties[5]), Integer.parseInt(properties[6]), imageStore.getImageList("Dude"), new AStarPathingStrategy());
            world.tryAddEntity(entity);
        }

        return properties.length == 7;
    }

    private static boolean parseEggDude(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 7) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new EggDude(properties[1], pt, Integer.parseInt(properties[5]), Integer.parseInt(properties[6]), imageStore.getImageList("EggDude"), new AStarPathingStrategy());
            world.tryAddEntity(entity);
        }

        return properties.length == 7;
    }


    private static boolean parseObstacle(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new Obstacle(properties[1], pt, imageStore.getImageList("obstacle"));
            world.tryAddEntity(entity);
        }

        return properties.length == 4;
    }

    private static boolean parseEgg(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 5) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new Egg(properties[1], pt, Integer.parseInt(properties[4]), imageStore.getImageList("Egg"));
            world.tryAddEntity(entity);
        }

        return properties.length == 5;
    }



    private static boolean parseTrash(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new Trash(properties[1], pt, imageStore.getImageList("Trash"));
            world.tryAddEntity(entity);
        }

        return properties.length == 4;
    }

    private static boolean parseFood(String[] properties, WorldModel world, ImageStore imageStore) {
        if (properties.length == 4) {
            Point pt = new Point(Integer.parseInt(properties[2]), Integer.parseInt(properties[3]));
            Entity entity = new Food(properties[1], pt, imageStore.getImageList("Food"));
            world.tryAddEntity(entity);
        }

        return properties.length == 4;
    }
    //    private static final String Dude_KEY = "Dude";
//    private static final String OBSTACLE_KEY = "obstacle";
//    private static final String Egg_KEY = "Egg";
//    private static final String Web_KEY = "Web";
//    private static final String BGND_KEY = "background";
//    private static final int PROPERTY_KEY = 0;
//    private static final String Trash_KEY = "Trash";
//    private static final int Dude_NUM_PROPERTIES = 7;
//    private static final int Dude_ID = 1;
//    private static final int Dude_COL = 2;
//    private static final int Dude_ROW = 3;
//    private static final int Dude_LIMIT = 4;
//    private static final int Dude_ACTION_PERIOD = 5;
//    private static final int Dude_ANIMATION_PERIOD = 6;
//    private static final int OBSTACLE_NUM_PROPERTIES = 4;
//    private static final int OBSTACLE_ID = 1;
//    private static final int OBSTACLE_COL = 2;
//    private static final int OBSTACLE_ROW = 3;
//    private static final int Trash_NUM_PROPERTIES = 4;
//    private static final int Trash_ID = 1;
//    private static final int Trash_COL = 2;
//    private static final int Trash_ROW = 3;
//    private static final int Egg_NUM_PROPERTIES = 5;
//    private static final int Egg_ID = 1;
//    private static final int Egg_COL = 2;
//    private static final int Egg_ROW = 3;
//    private static final int Egg_ACTION_PERIOD = 4;
//    private static final int Web_NUM_PROPERTIES = 5;
//    private static final int Web_ID = 1;
//    private static final int Web_COL = 2;
//    private static final int Web_ROW = 3;
//    private static final int Web_ACTION_PERIOD = 4;
//    private static final int BGND_NUM_PROPERTIES = 4;
//    private static final int BGND_ID = 1;
//    private static final int BGND_COL = 2;
//    private static final int BGND_ROW = 3;


}

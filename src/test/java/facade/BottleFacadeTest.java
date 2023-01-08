package facade;

import exception.BadArgumentsException;
import exception.NotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class BottleFacadeTest {

    private final FacadeInterface facade = Facade.getInstance();
    private Cellar cellar;

    /**
     * Create a cellar for each test.
     */
    @BeforeEach
    void init() {
        ArrayList<Wall> walls = new ArrayList<>();

        List<EmplacementBottle> emplacementBottleMap = new ArrayList<>();

        List<BottleQuantity> bottles = new ArrayList<>();

        ArrayList<String> grapeList = new ArrayList<>();
        grapeList.add("fef");
        grapeList.add("fefzfzq");

        bottles.add(new BottleQuantity(new Bottle("test",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",grapeList), 1));

        List<Point> pointList = new ArrayList<>();

        pointList.add(new Point(1,4));
        pointList.add(new Point(1,2));
        pointList.add(new Point(4,2));

        emplacementBottleMap.add(new EmplacementBottle(pointList,bottles));

        walls.add(new Wall("googlgree.com", emplacementBottleMap,"Mur Nord"));

        ArrayList<ObjectId> readers = new ArrayList<>();
        ArrayList<ObjectId> managers = new ArrayList<>();

        cellar = new Cellar("Cave1", true, readers, managers, ObjectId.get(),walls);
    }

    /**
     * Test the insertion of a bottle.
     */
    @Test
    void addBottle() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            Cellar cellarBefore = facade.getOneCellar(cellarId);

            int nbOfBottlesBefore = cellarBefore.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().size();

            facade.insertBottle(cellar.getWalls().get(0), cellar, new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0));

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            int nbOfBottlesAfter = cellarAfter.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().size();

            assertEquals(nbOfBottlesBefore + 1, nbOfBottlesAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the findById method.
     */
    @Test
    void findBottleById(){
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
//            cellar.setId(cellarId);

            Bottle bottle = facade.findOne(new ObjectId("63ba6fa8fd18ac796d447e3a"));

            System.out.println(bottle.getBottleName());
            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (NotFoundException e) {
            fail(e.getMessage());
        }

    }

    /**
     * Test the insertion of a bottle with a bad cellar.
     */
    @Test
    void addBottleWithBadCellar() {
        ObjectId cellarId = facade.insertOneCellar(cellar);

        cellar.setName("egrhrgbfefbefsgseghsegrosegkegksjegjsge");

        // with an empty cellar object
        assertThrows(BadArgumentsException.class, () -> facade.insertBottle(cellar.getWalls().get(0), new Cellar(), new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0)));

        Cellar badCellar = new Cellar("zfqzffz", true, new ArrayList<>(), new ArrayList<>(), ObjectId.get(), new ArrayList<>());
        badCellar.setId(ObjectId.get());

        // with a bad cellar, not in the database.
        assertThrows(NotFoundException.class, () -> facade.insertBottle(cellar.getWalls().get(0), badCellar, new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0)));

        // CLEAN UP

        facade.deleteOneCellar(cellarId);
    }

    /**
     * Test the insertion of a bottle with a bad wall.
     */
    @Test
    void addBottleWithBadWall() {
        ObjectId cellarId = facade.insertOneCellar(cellar);
        cellar.setId(cellarId);

        // with an empty wall object
        assertThrows(BadArgumentsException.class, () -> facade.insertBottle(null, cellar, new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0)));

        // with a bad wall, not in the database.
        assertThrows(NotFoundException.class, () -> facade.insertBottle(new Wall("zfqzffz", new ArrayList<>(), "zfqzffz"), cellar, new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0)));

        // CLEAN UP

        facade.deleteOneCellar(cellarId);
    }

    /**
     * Test the insertion of a bottle with a bad emplacement.
     */
    @Test
    void addBottleWithBadEmplacement() {
        ObjectId cellarId = facade.insertOneCellar(cellar);
        cellar.setId(cellarId);

        // with an empty emplacement object
        assertThrows(BadArgumentsException.class, () -> facade.insertBottle(cellar.getWalls().get(0), cellar, new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), null));

        // with a bad emplacement, not in the database.
        assertThrows(NotFoundException.class, () -> facade.insertBottle(cellar.getWalls().get(0), cellar, new Bottle("Lorenzo",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), new EmplacementBottle(new ArrayList<>(), new ArrayList<>())));

        // CLEAN UP

        facade.deleteOneCellar(cellarId);
    }

    /**
     * Test the insertion of a bottle with a bad bottle.
     */
    @Test
    void addBottleWithBadBottle() {
        ObjectId cellarId = facade.insertOneCellar(cellar);
        cellar.setId(cellarId);

        // with an empty bottle object
        assertThrows(BadArgumentsException.class, () -> facade.insertBottle(cellar.getWalls().get(0), cellar, null, cellar.getWalls().get(0).getEmplacementBottleMap().get(0)));

        // CLEAN UP

        facade.deleteOneCellar(cellarId);
    }

    /**
     * Test the getAllBottles method.
     */
    @Test
    void getAllBottles() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            List<Bottle> bottleList = facade.getBottlesFromCellar(cellarId);

            assertEquals(1, bottleList.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the updateBottle method.
     */
    @Test
    void updateBottle() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            Cellar cellarBefore = facade.getOneCellar(cellarId);

            Bottle bottleBefore = cellarBefore.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0).getBottle();

            Bottle bottleAfter = new Bottle("Lorenzo",2012,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>());

            facade.updateBottle(cellar.getWalls().get(0), cellar, bottleBefore, cellar.getWalls().get(0).getEmplacementBottleMap().get(0), bottleAfter);

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            Bottle bottleAfterUpdate = cellarAfter.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0).getBottle();

            assertEquals(2020, bottleBefore.getVintage());
            assertEquals(2012, bottleAfterUpdate.getVintage());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the deletion of a bottle.
     */
    @Test
    void removeBottle() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);
            Bottle bottle = new Bottle("Lorenzo", 2020, "fafazf", "googreggle.com", 10.5, "michel", 14.2, 1, "L", "bf", new ArrayList<>());

            // Add the bottles

            facade.insertBottle(cellar.getWalls().get(0), cellar, bottle, cellar.getWalls().get(0).getEmplacementBottleMap().get(0));
            facade.insertBottle(cellar.getWalls().get(0), cellar, new Bottle("Morgane",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0));
            facade.insertBottle(cellar.getWalls().get(0), cellar, new Bottle("Tom",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",new ArrayList<>()), cellar.getWalls().get(0).getEmplacementBottleMap().get(0));

            // Get the cellar after the add to have an updated version of the cellar to pass as argument to the removal.
            Cellar cellarBefore = facade.getOneCellar(cellarId);

            int nbOfBottlesBefore = cellarBefore.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().size();

            facade.deleteBottle(cellarBefore.getWalls().get(0), cellarBefore, bottle, cellarBefore.getWalls().get(0).getEmplacementBottleMap().get(0));

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            int nbOfBottlesAfter = cellarAfter.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().size();

            assertEquals(nbOfBottlesBefore - 1, nbOfBottlesAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }
}
package facade;

import exception.BadArgumentsException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.bottle.Bottle;
import persistence.entity.cellar.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CellarFacadeTest {

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
        grapeList.add("raisin blanc");
        grapeList.add("raisin noir");

        bottles.add(new BottleQuantity(new Bottle("test",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",grapeList), 1));
        bottles.add(new BottleQuantity(new Bottle("test",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",grapeList), 1));
        bottles.add(new BottleQuantity(new Bottle("test",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",grapeList), 1));
        bottles.add(new BottleQuantity(new Bottle("Chateau le bosq", 2015, "Bordeaux", "https://www.chateau-le-bosq.com/", 13.5, "Chateau le bosq", 14.2, 1, "L", "Rouge", grapeList), 1));

        List<Point> pointList = new ArrayList<>();

        pointList.add(new Point(1,4));
        pointList.add(new Point(1,2));
        pointList.add(new Point(4,2));

        emplacementBottleMap.add(new EmplacementBottle(pointList,bottles));

        walls.add(new Wall("googlgree.com", emplacementBottleMap,"Mur Nord"));
        walls.add(new Wall("googlgree.com", emplacementBottleMap,"Mur Est"));
        walls.add(new Wall("googlgree.com", emplacementBottleMap,"Mur Ouest"));

        ArrayList<ObjectId> readers = new ArrayList<>();
        ArrayList<ObjectId> managers = new ArrayList<>();

        cellar = new Cellar("Cave1", true, readers, managers, ObjectId.get(),walls);
    }

    /**
     * Test the insertion of a cellar.
     */
    @Test
    void insertOneCellar() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        cellar.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneCellar(cellar);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Cellar receivedCellar = facade.getOneCellar(idReceivedAfterCreation);
        assertEquals(cellar.getName(), receivedCellar.getName());
        assertEquals(receivedCellar.getManagers(), cellar.getManagers());
        assertEquals(receivedCellar.getOwnerRef(), cellar.getOwnerRef());
        assertEquals(receivedCellar.getReaders(), cellar.getReaders());
        assertEquals(receivedCellar.getWalls(), cellar.getWalls());
        assertEquals(receivedCellar.isPublic(), cellar.isPublic());

        // CLEAN UP

        facade.deleteOneCellar(idReceivedAfterCreation);
    }

    /**
     * test the getList of cellars.
     */
    @Test
    void getCellarList() {
        int initialNumberOfCellars = facade.getCellarList().size();

        ObjectId idOfInsertedCellar = facade.insertOneCellar(cellar);
        ObjectId idOfInsertedCellar2 = facade.insertOneCellar(cellar);
        ObjectId idOfInsertedCellar3 = facade.insertOneCellar(cellar);

        List<Cellar> receivedCellarList = facade.getCellarList();

        assertEquals(3 + initialNumberOfCellars, receivedCellarList.size());
        assertTrue(receivedCellarList.contains(facade.getOneCellar(idOfInsertedCellar)));

        // CLEAN UP

        facade.deleteOneCellar(idOfInsertedCellar);
        facade.deleteOneCellar(idOfInsertedCellar2);
        facade.deleteOneCellar(idOfInsertedCellar3);
    }

    /**
     * Test the getOneCellar method.
     */
    @Test
    void getOneCellar() {
        ObjectId objectId = facade.insertOneCellar(cellar);
        Cellar oneCellar = facade.getOneCellar(objectId);

        assertEquals(cellar, oneCellar);

        // CLEAN UP

        facade.deleteOneCellar(objectId);
    }

    /**
     * Test the update of a cellar.
     */
    @Test
    void updateOneCellar() {
        ObjectId isOfInsertedCellar = facade.insertOneCellar(cellar);

        Cellar receivedCellar = facade.getOneCellar(isOfInsertedCellar);
        receivedCellar.setName("Blabla");
        receivedCellar.setPublic(false);

        facade.updateOneCellar(receivedCellar.getId(), receivedCellar);

        Cellar updateCellar = facade.getOneCellar(isOfInsertedCellar);

        assertEquals(receivedCellar.getName(), updateCellar.getName());
        assertEquals(receivedCellar.getManagers(), updateCellar.getManagers());
        assertEquals(receivedCellar.getOwnerRef(), updateCellar.getOwnerRef());
        assertEquals(receivedCellar.getReaders(), updateCellar.getReaders());
        assertEquals(receivedCellar.getWalls(), updateCellar.getWalls());
        assertEquals(receivedCellar.isPublic(), updateCellar.isPublic());

        // CLEAN UP

        facade.deleteOneCellar(isOfInsertedCellar);
    }

    /**
     * Test the deletion of a cellar.
     */
    @Test
    void deleteOneCellar() {
        ObjectId objectId = facade.insertOneCellar(cellar);
        List<Cellar> cellarListBefore = facade.getCellarList();

        facade.deleteOneCellar(objectId);
        List<Cellar> cellarListAfter = facade.getCellarList();

        assertEquals(cellarListBefore.size(), cellarListAfter.size() + 1);
    }

    /**
     * Test the insertion of a reader.
     */
    @Test
    void addNewReader() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            ObjectId cellarId2 = facade.insertOneCellar(cellar);

            ObjectId readerId = ObjectId.get();

            facade.addCellarReader(readerId, cellarId);

            List<Cellar> readOnlyCellarsFromUserBefore = facade.getReadOnlyCellarsFromUser(readerId);

            facade.addCellarReader(readerId, cellarId2);

            List<Cellar> readOnlyCellarsFromUserAfter = facade.getReadOnlyCellarsFromUser(readerId);

            assertEquals(readOnlyCellarsFromUserBefore.size() + 1, readOnlyCellarsFromUserAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the removal of a reader.
     */
    @Test
    void removeCellarReader() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            ObjectId cellarId2 = facade.insertOneCellar(cellar);

            ObjectId readerId = ObjectId.get();
            facade.addCellarReader(readerId, cellarId);
            facade.addCellarReader(readerId, cellarId2);

            List<Cellar> readOnlyCellarsFromUserBefore = facade.getReadOnlyCellarsFromUser(readerId);

            facade.removeCellarReader(readerId, cellarId);

            List<Cellar> readOnlyCellarsFromUserAfter = facade.getReadOnlyCellarsFromUser(readerId);

            assertEquals(readOnlyCellarsFromUserBefore.size() - 1, readOnlyCellarsFromUserAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the insertion of a manager.
     */
    @Test
    void addNewManager() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            ObjectId cellarId2 = facade.insertOneCellar(cellar);

            ObjectId managerId = ObjectId.get();

            facade.addCellarManager(managerId, cellarId);

            List<Cellar> managerCellarsFromUserBefore = facade.getCellarsWhereUserIsManager(managerId);

            facade.addCellarManager(managerId, cellarId2);

            List<Cellar> managerCellarsFromUserAfter = facade.getCellarsWhereUserIsManager(managerId);

            assertEquals(managerCellarsFromUserBefore.size() + 1, managerCellarsFromUserAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the removal of a manager.
     */
    @Test
    void removeCellarManager() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            ObjectId cellarId2 = facade.insertOneCellar(cellar);

            ObjectId managerId = ObjectId.get();
            facade.addCellarManager(managerId, cellarId);
            facade.addCellarManager(managerId, cellarId2);

            List<Cellar> managerCellarsFromUserBefore = facade.getCellarsWhereUserIsManager(managerId);

            facade.removeCellarManager(managerId, cellarId);

            List<Cellar> managerCellarsFromUserAfter = facade.getCellarsWhereUserIsManager(managerId);

            assertEquals(managerCellarsFromUserBefore.size() - 1, managerCellarsFromUserAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the insertion of a wall.
     */
    @Test
    void addWall() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);

            Cellar cellarBefore = facade.getOneCellar(cellarId);

            int nbOfWallsBefore = cellarBefore.getWalls().size();

            List<EmplacementBottle> emplacementBottleMap = new ArrayList<>();

            List<BottleQuantity> bottles = new ArrayList<>();

            ArrayList<String> grapeList = new ArrayList<>();
            grapeList.add("ffffesseef");
            grapeList.add("fefzgsgsesefzq");

            bottles.add(new BottleQuantity(new Bottle("test",2020,"fafazf","googreggle.com", 10.5, "michel", 14.2,1,"L","bf",grapeList), 1));

            List<Point> pointList = new ArrayList<>();

            pointList.add(new Point(3,4));
            pointList.add(new Point(1,2));
            pointList.add(new Point(4,2));

            emplacementBottleMap.add(new EmplacementBottle(pointList,bottles));
            Wall wall = new Wall("googlgree.com", emplacementBottleMap,"Mur Nord");

            facade.addWall(wall, cellarId);

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            int nbOfWallsAfter = cellarAfter.getWalls().size();

            assertEquals(nbOfWallsBefore + 1, nbOfWallsAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the deletion of a wall.
     */
    @Test
    void removeWall(){
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);

            Cellar cellarBefore = facade.getOneCellar(cellarId);

            int nbOfWallsBefore = cellarBefore.getWalls().size();

            facade.removeWall(cellar.getWalls().get(0), cellarId);

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            int nbOfWallsAfter = cellarAfter.getWalls().size();

            assertEquals(nbOfWallsBefore - 1, nbOfWallsAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the insertion of an emplacement.
     */
    @Test
    void addEmplacement() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            List<Point> pointList = new ArrayList<>();

            pointList.add(new Point(2,2));
            pointList.add(new Point(4,4));
            pointList.add(new Point(5,4));
            pointList.add(new Point(3,6));

            Cellar cellarBefore = facade.getOneCellar(cellarId);

            int nbOfEmplacementsBefore = cellarBefore.getWalls().get(0).getEmplacementBottleMap().size();

            facade.addEmplacement(cellar, cellar.getWalls().get(0), new EmplacementBottle(pointList, new ArrayList<>()));

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            int nbOfEmplacementsAfter = cellarAfter.getWalls().get(0).getEmplacementBottleMap().size();

            assertEquals(nbOfEmplacementsBefore + 1, nbOfEmplacementsAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the deletion of an emplacement.
     */
    @Test
    void removeEmplacement() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            List<Point> pointList = new ArrayList<>();

            pointList.add(new Point(2,2));
            pointList.add(new Point(4,4));
            pointList.add(new Point(5,4));
            pointList.add(new Point(3, 6));

            EmplacementBottle emplacementBottle = new EmplacementBottle(pointList, new ArrayList<>());
            facade.addEmplacement(cellar, cellar.getWalls().get(0), emplacementBottle);

            facade.addEmplacement(cellar, cellar.getWalls().get(0), emplacementBottle);

            List<Point> pointList2 = new ArrayList<>();

            pointList2.add(new Point(2,2));
            pointList2.add(new Point(4,4));
            pointList2.add(new Point(5,4));

            facade.addEmplacement(cellar, cellar.getWalls().get(0), new EmplacementBottle(pointList2, new ArrayList<>()));

            Cellar cellarBefore = facade.getOneCellar(cellarId);

            int nbOfEmplacementsBefore = cellarBefore.getWalls().get(0).getEmplacementBottleMap().size();

            facade.removeEmplacement(cellar, cellar.getWalls().get(0), emplacementBottle);

            Cellar cellarAfter = facade.getOneCellar(cellarId);

            int nbOfEmplacementsAfter = cellarAfter.getWalls().get(0).getEmplacementBottleMap().size();

            assertEquals(nbOfEmplacementsBefore - 2, nbOfEmplacementsAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the increase of a bottle quantity.
     */
    @Test
    void increaseBottleQuantity() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            EmplacementBottle emplacementBottle = cellar.getWalls().get(0).getEmplacementBottleMap().get(0);
            BottleQuantity bottleQuantity = cellar.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0);

            int quantity = cellar.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0).getQuantity();

            facade.increaseBottleQuantity(cellar, cellar.getWalls().get(0), emplacementBottle, bottleQuantity);

            Cellar cellar1 = facade.getOneCellar(cellarId);

            int quantity1 = cellar1.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0).getQuantity();

            assertEquals(quantity + 1, quantity1);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the decrease of a bottle quantity.
     */
    @Test
    void decreaseBottleQuantity() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);

            EmplacementBottle emplacementBottle = cellar.getWalls().get(0).getEmplacementBottleMap().get(0);
            BottleQuantity bottleQuantity = cellar.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0);

            int quantity = cellar.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0).getQuantity();

            facade.decreaseBottleQuantity(cellar, cellar.getWalls().get(0), emplacementBottle, bottleQuantity);

            Cellar cellar1 = facade.getOneCellar(cellarId);

            int quantity1 = cellar1.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().get(0).getQuantity();

            assertEquals(quantity - 1, quantity1);

            facade.decreaseBottleQuantity(cellar, cellar.getWalls().get(0), emplacementBottle, bottleQuantity);

            // assert that the bottleQuantity has been deleted properly
            Cellar cellar2 = facade.getOneCellar(cellarId);
            assertEquals(0, cellar2.getWalls().get(0).getEmplacementBottleMap().get(0).getBottleList().size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the getPublicCellars method.
     */
    @Test
    void getPublicCellars() {
        try {
            ObjectId objectId = facade.insertOneCellar(cellar);

            List<Cellar> cellarListBefore = facade.getPublicCellars();

            cellar.setPublic(false);

            ObjectId objectId1 = facade.insertOneCellar(cellar);
            ObjectId objectId2 = facade.insertOneCellar(cellar);

            cellar.setPublic(true);
            ObjectId objectId3 = facade.insertOneCellar(cellar);
            ObjectId objectId4 = facade.insertOneCellar(cellar);

            List<Cellar> cellarListAfter = facade.getPublicCellars();

            assertEquals(cellarListBefore.size() + 2, cellarListAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(objectId);
            facade.deleteOneCellar(objectId1);
            facade.deleteOneCellar(objectId2);
            facade.deleteOneCellar(objectId3);
            facade.deleteOneCellar(objectId4);

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the getCellarFromUser method.
     */
    @Test
    void getCellarFromUser(){
        try {
            ObjectId userId = cellar.getOwnerRef();

            ObjectId cellarId = facade.insertOneCellar(cellar);

            List<Cellar> cellarsFromUserBefore = facade.getCellarsFromUser(userId);

            int sizeBefore = cellarsFromUserBefore.size();

            ObjectId cellarId1 = facade.insertOneCellar(cellar);

            List<Cellar> cellarsFromUserAfter = facade.getCellarsFromUser(userId);

            int sizeAfter = cellarsFromUserAfter.size();

            assertEquals(sizeBefore + 1, sizeAfter);

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the getReadOnlyCellarsFromUser method.
     */
    @Test
    void getReadOnlyCellarsFromUser() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);
            ObjectId cellarId2 = facade.insertOneCellar(cellar);

            facade.addCellarReader(cellar.getOwnerRef(), cellarId);

            List<Cellar> cellarsFromUserBefore = facade.getReadOnlyCellarsFromUser(cellar.getOwnerRef());

            facade.addCellarReader(cellar.getOwnerRef(), cellarId2);

            List<Cellar> cellarsFromUserAfter = facade.getReadOnlyCellarsFromUser(cellar.getOwnerRef());

            assertEquals(cellarsFromUserBefore.size() + 1, cellarsFromUserAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the getCellarsWhereUserIsManager method.
     */
    @Test
    void getCellarsWhereUserIsManager() {
        try {
            ObjectId cellarId = facade.insertOneCellar(cellar);
            cellar.setId(cellarId);
            ObjectId cellarId2 = facade.insertOneCellar(cellar);

            facade.addCellarManager(cellar.getOwnerRef(), cellarId);

            List<Cellar> cellarsFromUserBefore = facade.getCellarsWhereUserIsManager(cellar.getOwnerRef());

            facade.addCellarManager(cellar.getOwnerRef(), cellarId2);

            List<Cellar> cellarsFromUserAfter = facade.getCellarsWhereUserIsManager(cellar.getOwnerRef());

            assertEquals(cellarsFromUserBefore.size() + 1, cellarsFromUserAfter.size());

            // CLEAN UP

            facade.deleteOneCellar(cellarId);
            facade.deleteOneCellar(cellarId2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

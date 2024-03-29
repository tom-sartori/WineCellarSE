package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.guide.Guide;
import persistence.entity.guide.GuideCategory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GuideFacadeTest {

    private final FacadeInterface facade = Facade.getInstance();
    private Guide guide;

    @BeforeEach
    void init() {
        LinkedHashMap<String, String> sectionMap = new LinkedHashMap<>();
        sectionMap.put("Section1","le paragraphe");
        sectionMap.put("titre 2", "blablabla");
        guide = new Guide("test", "description", sectionMap, GuideCategory.TUTORIAL,new Date());
    }

    /**
     * Test the insertOneGuide method.
     */
    @Test
    void insertOneGuide() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        guide.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneGuide(guide);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Guide receivedGuide = facade.getOneGuide(idReceivedAfterCreation);
        assertEquals(guide.getTitle(), receivedGuide.getTitle());
        assertEquals(guide.getDescription(), receivedGuide.getDescription());
        assertEquals(guide.getSectionList(), receivedGuide.getSectionList());
    }

    /**
     * Test l'ajout d'une section.
     */
    @Test
    void ajoutSection() {

        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        LinkedHashMap<String, String> sectionMap2 = new LinkedHashMap<>();

        sectionMap2.put("Section1","blablablabkla");


        sectionMap2.put("Section2","encore un paragraphe");


        Guide receivedGuide= facade.getOneGuide(idOfInsertedGuide);
        receivedGuide.setSectionList(sectionMap2);


        facade.updateOneGuide(receivedGuide.getId(), receivedGuide);

        Guide updatedGuide = facade.getOneGuide(idOfInsertedGuide);

        assertEquals(receivedGuide.getTitle(), updatedGuide.getTitle());
        assertEquals(receivedGuide.getSectionList(), updatedGuide.getSectionList());
    }

    /**
     * Test l'ajout de section par index : si on veut ajouter une section dans un ordre précis.
     */
    /*
    @Test
    void ajoutSectionParIndex(){
        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        //creation de la map et ds deux valeurs

        LinkedHashMap<String, String> sectionMap2 = new LinkedHashMap<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();

        sectionMap2.put("Section1","blablablabkla");
        sectionMap2.put("Section2","encore un paragraphe");


        Guide receivedGuide= facade.getOneGuide(idOfInsertedGuide);
        receivedGuide.setSectionList(sectionMap2);


        facade.updateOneGuide(receivedGuide.getId(), receivedGuide);

        List<String> newList = new ArrayList<>();
        newList.add("bla bla bla 1");
        newList.add("bla bla bla 2");
        newList.add("bla bla bla 3");
        String nomParagraphe = "nouveau Paragraphe";

        int index = 1;

        LinkedHashMap<String, List<String>> newMap = new LinkedHashMap<>();

        int compteur = 0;

        for (Map.Entry<String, List<String>> entry : sectionMap2.entrySet()) {
            if (compteur == index) {
                newMap.put(nomParagraphe, newList);
            }
            newMap.put(entry.getKey(), entry.getValue());
            compteur++;
        }

        receivedGuide.setSectionList(newMap);
        facade.updateOneGuide(receivedGuide.getId(), receivedGuide);

        LinkedHashMap<String, List<String>> mapBonOrdre = new LinkedHashMap<>();
        mapBonOrdre.put("Section1",list2);
        mapBonOrdre.put("nouveau Paragraphe",newList);
        mapBonOrdre.put("Section2",list3);
        assertEquals(newMap, mapBonOrdre);
    }

     */

    /**
     * Test the getGuideList method.
     */
    @Test
    void test_findAll_OK() {
        int initialNumberOfGuides = facade.getGuideList().size();

        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);
        facade.insertOneGuide(guide);
        facade.insertOneGuide(guide);

        List<Guide> receivedGuideList = facade.getGuideList();

        assertEquals(3 + initialNumberOfGuides, receivedGuideList.size());
        assertTrue(receivedGuideList.contains(facade.getOneGuide(idOfInsertedGuide)));
    }

    /**
     * Test the getOneGuide method.
     */
    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        Guide receivedGuide = facade.getOneGuide(idOfInsertedGuide);

        assertEquals(guide.getTitle(), receivedGuide.getTitle());
        assertEquals(guide.getSectionList(), receivedGuide.getSectionList());
        assertEquals(guide.getCreationDate(), receivedGuide.getCreationDate());

    }

    /**
     * Test the updateOneGuide method.
     */
    @Test
    void test_update_OK() {
        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        LinkedHashMap<String, String> sectionMap2 = new LinkedHashMap<>();
        sectionMap2.put("Section1","ubvuibguihgeug");


        Guide receivedGuide= facade.getOneGuide(idOfInsertedGuide);
        receivedGuide.setTitle("le guide");
        receivedGuide.setSectionList(sectionMap2);


        facade.updateOneGuide(receivedGuide.getId(), receivedGuide);

        Guide updatedGuide = facade.getOneGuide(idOfInsertedGuide);

        assertEquals(receivedGuide.getTitle(), updatedGuide.getTitle());
        assertEquals(receivedGuide.getSectionList(), updatedGuide.getSectionList());

    }

    /**
     * Test the deleteOneGuide method.
     */
    @Test
    void test_delete_OK() {
        ObjectId guideIdInserted = facade.insertOneGuide(guide);

        List<Guide> guideList = facade.getGuideList();
        int initialNumberOfGuides = guideList.size();
        assertTrue(guideList.contains(facade.getOneGuide(guideIdInserted)));

        assertTrue(facade.deleteOneGuide(guideIdInserted));
        assertEquals(initialNumberOfGuides - 1, facade.getGuideList().size());
    }

}
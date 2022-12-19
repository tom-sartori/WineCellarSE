package facade;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.guide.Guide;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GuideFacadeTest {

    private final FacadeInterface facade = Facade.getInstance();
    private Guide guide;

    @BeforeEach
    void init() {
        LinkedHashMap<String, List<String>> sectionMap = new LinkedHashMap<>();
        List<String> list = new ArrayList<>();
        list.add("paragraph");
        sectionMap.put("Section1",list);
        guide = new Guide("test", sectionMap, new Date());
    }

    @Test
    void insertOneGuide() {
        ObjectId idShouldBeOverridden = new ObjectId("aaaaaaaaaaaaaaaaaaaaaaaa");
        guide.setId(idShouldBeOverridden);

        ObjectId idReceivedAfterCreation = facade.insertOneGuide(guide);

        assertNotNull(idReceivedAfterCreation);
        assertNotEquals(idShouldBeOverridden, idReceivedAfterCreation);	// The user should not be able to set the id.

        Guide receivedGuide = facade.getOneGuide(idReceivedAfterCreation);
        assertEquals(guide.getTitle(), receivedGuide.getTitle());
        assertEquals(guide.getSectionList(), receivedGuide.getSectionList());
    }

    @Test
    void ajoutSection() {

        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        LinkedHashMap<String, List<String>> sectionMap2 = new LinkedHashMap<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();

        list2.add("bla bla bla 1");
        list2.add("bla bla bla 2");
        list2.add("bla bla bla 3");
        sectionMap2.put("Section1",list2);

        list3.add("bla bla bla 1");
        list3.add("bla bla bla 2");
        list3.add("bla bla bla 3");
        sectionMap2.put("Section2",list3);


        Guide receivedGuide= facade.getOneGuide(idOfInsertedGuide);
        receivedGuide.setSectionList(sectionMap2);


        facade.updateOneGuide(receivedGuide.getId(), receivedGuide);

        Guide updatedGuide = facade.getOneGuide(idOfInsertedGuide);

        assertEquals(receivedGuide.getTitle(), updatedGuide.getTitle());
        assertEquals(receivedGuide.getSectionList(), updatedGuide.getSectionList());
    }

    @Test
    void ajoutSectionParIndex(){
        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        //creation de la map et ds deux valeurs

        LinkedHashMap<String, List<String>> sectionMap2 = new LinkedHashMap<>();
        List<String> list2 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();

        list2.add("bla bla bla 1");
        list2.add("bla bla bla 2");
        list2.add("bla bla bla 3");
        sectionMap2.put("Section1",list2);

        list3.add("bla bla bla 1");
        list3.add("bla bla bla 2");
        list3.add("bla bla bla 3");
        sectionMap2.put("Section2",list3);


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

    @Test
    void test_findOne_OK() {
        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        Guide receivedGuide = facade.getOneGuide(idOfInsertedGuide);

        assertEquals(guide.getTitle(), receivedGuide.getTitle());
        assertEquals(guide.getSectionList(), receivedGuide.getSectionList());
        assertEquals(guide.getCreationDate(), receivedGuide.getCreationDate());

    }

    @Test
    void test_update_OK() {
        ObjectId idOfInsertedGuide = facade.insertOneGuide(guide);

        LinkedHashMap<String, List<String>> sectionMap2 = new LinkedHashMap<>();
        List<String> list2 = new ArrayList<>();
        list2.add("paragraphTest");
        sectionMap2.put("Section1",list2);


        Guide receivedGuide= facade.getOneGuide(idOfInsertedGuide);
        receivedGuide.setTitle("le guide");
        receivedGuide.setSectionList(sectionMap2);


        facade.updateOneGuide(receivedGuide.getId(), receivedGuide);

        Guide updatedGuide = facade.getOneGuide(idOfInsertedGuide);

        assertEquals(receivedGuide.getTitle(), updatedGuide.getTitle());
        assertEquals(receivedGuide.getSectionList(), updatedGuide.getSectionList());

    }

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
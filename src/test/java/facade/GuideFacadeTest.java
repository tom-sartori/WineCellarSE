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
package facade;

import exception.BadArgumentsException;
import exception.NotFoundException;
import exception.user.MustBeAnAdminException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.entity.cellar.Cellar;
import persistence.entity.company.Company;
import persistence.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyFacadeTest {

    private final FacadeInterface facade = Facade.getInstance();
    private Company company;

    /**
     * Create a company for each test.
     */
    @BeforeEach
    void init() {
        company = new Company("Google", "type1", " 4 rue de Ganges, 34090, Montpellier", false, null, new ArrayList<>(), new Cellar(), "06 43 54 23 53", null, null, null);
    }

    /**
     * Test the insertion of a company.
     */
    @Test
    void Test_insertOneCompany(){
        company.setAccessible(true);
        ObjectId objectId = facade.insertOneCompany(company);

        assertNotNull(objectId);

        // test if the company is not accessible even if we set it to true because the user should not be able to set it to true.
        Company company = facade.getOneCompany(objectId);

        assertNotNull(company);

        assertFalse(company.isAccessible());

        // CLEAN UP

        facade.deleteOneCompany(objectId);
    }

    /**
     * Test the getCompanyList method.
     */
    @Test
    void Test_getCompanyList(){
        ObjectId objectId = facade.insertOneCompany(company);

        assertNotNull(objectId);

        List<Company> before = facade.getCompanyList();

        ObjectId objectId1 = facade.insertOneCompany(company);
        ObjectId objectId2 = facade.insertOneCompany(company);

        List<Company> after = facade.getCompanyList();

        assertEquals(before.size() + 2, after.size());

        // CLEAN UP

        facade.deleteOneCompany(objectId);
        facade.deleteOneCompany(objectId1);
        facade.deleteOneCompany(objectId2);
    }

    /**
     * Test the getOneCompany method.
     */
    @Test
    void Test_getOneCompany(){
        ObjectId objectId = facade.insertOneCompany(company);

        assertNotNull(objectId);

        Company companyAfter = facade.getOneCompany(objectId);

        assertNotNull(company);

        assertEquals(company, companyAfter);

        // CLEAN UP

        facade.deleteOneCompany(objectId);
    }

    /**
     * Test the updateOneCompany method.
     */
    @Test
    void Test_updateOneCompany(){
        ObjectId objectId = facade.insertOneCompany(company);

        assertNotNull(objectId);

        company.setName("Apple");

        facade.updateOneCompany(objectId, company);

        Company companyAfter = facade.getOneCompany(objectId);

        assertEquals(company, companyAfter);
        assertEquals("Apple", companyAfter.getName());

        // CLEAN UP

        facade.deleteOneCompany(objectId);
    }

    /**
     * Test the deleteOneCompany method.
     */
    @Test
    void Test_deleteOneCompany(){
        ObjectId objectId = facade.insertOneCompany(company);

        assertNotNull(objectId);

        facade.deleteOneCompany(objectId);

        assertThrows(NotFoundException.class, () -> {
            facade.getOneCompany(objectId);
        });
    }

    /**
     * Test the getCompanyListByManager method.
     */
    @Test
    void Test_promoteNewMasterManager() throws MustBeAnAdminException {
        try {
            ObjectId objectId = facade.insertOneCompany(company);

            User user = new User("mich", "mich");

            ObjectId userId = facade.register(user);

            assertNotNull(objectId);

            ObjectId objectId1 = facade.promoteNewMasterManager(objectId, userId);

            assertNotNull(objectId1);

            Company companyAfter = facade.getOneCompany(objectId);

            assertEquals(userId, companyAfter.getMasterManager());

            // CLEAN UP

            facade.deleteOneCompany(objectId);
            facade.deleteOneUser(user.getUsername());
        } catch (BadArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the insertNewManager method.
     */
    @Test
    void Test_insertNewManager() throws MustBeAnAdminException {
        try {
            ObjectId objectId = facade.insertOneCompany(company);

            User user = new User("mich", "mich");

            ObjectId userId = facade.register(user);

            assertNotNull(objectId);

            ObjectId objectId1 = facade.addManager(objectId, userId);

            assertNotNull(objectId1);

            Company companyAfter = facade.getOneCompany(objectId);

            assertEquals(userId, companyAfter.getManagerList().get(0));

            // CLEAN UP

            facade.deleteOneCompany(objectId);
            facade.deleteOneUser(user.getUsername());
        } catch (BadArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the deleteManager method.
     */
    @Test
    void Test_removeAManager() throws MustBeAnAdminException {
        try {
            ObjectId objectId = facade.insertOneCompany(company);

            User user = new User("mich", "mich");

            ObjectId userId = facade.register(user);

            assertNotNull(objectId);

            ObjectId objectId1 = facade.addManager(objectId, userId);

            assertNotNull(objectId1);

            Company companyAfter = facade.getOneCompany(objectId);

            assertEquals(userId, companyAfter.getManagerList().get(0));

            ObjectId objectId2 = facade.removeManager(objectId, userId);

            assertNotNull(objectId2);

            Company companyAfter2 = facade.getOneCompany(objectId);

            assertEquals(0, companyAfter2.getManagerList().size());

            // CLEAN UP

            facade.deleteOneCompany(objectId);
            facade.deleteOneUser(user.getUsername());
        } catch (BadArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the acceptRequest method.
     */
    @Test
    void Test_acceptRequest(){
        try {
            ObjectId objectId = facade.insertOneCompany(company);

            ObjectId objectId1 = facade.acceptRequest(objectId);

            assertNotNull(objectId1);

            Company companyAfter = facade.getOneCompany(objectId);

            assertEquals(objectId, companyAfter.getId());
            assertTrue(companyAfter.isAccessible());

            // CLEAN UP
            facade.deleteOneCompany(objectId);
        } catch (BadArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the refuseRequest method.
     */
    @Test
    void Test_refuseRequest(){
        try {
            ObjectId objectId = facade.insertOneCompany(company);

            ObjectId objectId1 = facade.refuseRequest(objectId);

            assertNotNull(objectId1);

            assertThrows(NotFoundException.class, () -> facade.getOneCompany(objectId));

        } catch (BadArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the findAllAccessibleCompanies method.
     */
    @Test
    void Test_findAllAccessibleCompanies(){

        try {
            ObjectId objectId = facade.insertOneCompany(company);

            facade.acceptRequest(objectId);

            assertNotNull(objectId);

            List<Company> before = facade.findAllAccessibleCompanies();

            ObjectId objectId1 = facade.insertOneCompany(company);
            facade.acceptRequest(objectId1);
            ObjectId objectId2 = facade.insertOneCompany(company);

            List<Company> after = facade.findAllAccessibleCompanies();

            assertEquals(before.size() + 1, after.size());

            // CLEAN UP

            facade.deleteOneCompany(objectId);
            facade.deleteOneCompany(objectId1);
            facade.deleteOneCompany(objectId2);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the findAllUnAccessibleCompanies method.
     */
    @Test
    void Test_findAllUnAccessibleCompanies(){

        try {
            ObjectId objectId = facade.insertOneCompany(company);

            assertNotNull(objectId);

            List<Company> before = facade.findAllUnaccessibleCompanies();

            ObjectId objectId1 = facade.insertOneCompany(company);
            facade.acceptRequest(objectId1);
            ObjectId objectId2 = facade.insertOneCompany(company);
            ObjectId objectId3 = facade.insertOneCompany(company);

            List<Company> after = facade.findAllUnaccessibleCompanies();

            assertEquals(before.size() + 2, after.size());

            // CLEAN UP

            facade.deleteOneCompany(objectId);
            facade.deleteOneCompany(objectId1);
            facade.deleteOneCompany(objectId2);
            facade.deleteOneCompany(objectId3);
        } catch (BadArgumentsException e) {
            fail(e.getMessage());
        }
    }

    /**
     * Test the findCompaniesByUser method.
     */
    @Test
    void Test_findCompaniesByUserId(){
        try {
            User user = new User("Lorenzo10", "Lorenzo5");

            ObjectId userId = facade.register(user);

            assert userId != null;

            company.setMasterManager(userId);

            ObjectId objectId = facade.insertOneCompany(company);

            assertNotNull(objectId);

            List<Company> before = facade.findAllCompaniesByUserId(userId);

            company.setMasterManager(null);

            ObjectId objectId1 = facade.insertOneCompany(company);

            assertNotNull(objectId1);

            ObjectId objectId2 = facade.addManager(objectId1, userId);

            assertNotNull(objectId2);

            List<Company> after = facade.findAllCompaniesByUserId(userId);

            assertEquals(before.size() + 1, after.size());

            // CLEAN UP

            facade.deleteOneCompany(objectId);
            facade.deleteOneUser(userId);
        } catch (BadArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

}

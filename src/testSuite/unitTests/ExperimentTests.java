package testSuite.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import testSuite.TestInitializer;

import database.DatabaseAccessor;
import database.Experiment;

public class ExperimentTests {

    private static DatabaseAccessor dbac;

    private static String testExpId = "testExpId1";

    private static String testLabelFT = "testLabelFT1";
    private static String testValueFT = "testValueFT1";
    private static String testLabelDD = "testLabelDD1";
    private static String testChoice = "testchoice";
	private static String newLabel = "Tis";					//for changeLabel
    private static List<String> testChoices;

    @BeforeClass
    public static void setupTestCase() throws Exception {
        dbac = new DatabaseAccessor(TestInitializer.username, TestInitializer.password, TestInitializer.host,
        		TestInitializer.database);
        testChoices = new ArrayList<String>();
        testChoices.add(testChoice);
        testChoices.add(testChoice + "2");
    }

    @AfterClass
    public static void undoAllChanges() throws SQLException {
        dbac.close();
    }

    @Before
    public void setup() throws SQLException, IOException {
       dbac.addExperiment(testExpId);
       dbac.addFreeTextAnnotation(testLabelFT, null, true);
       dbac.addDropDownAnnotation(testLabelDD, testChoices, 0, false);
    }

    @After
    public void teardown() throws SQLException {
        dbac.deleteExperiment(testExpId);
        dbac.deleteAnnotation(testLabelFT);
        dbac.deleteAnnotation(testLabelDD);
        dbac.deleteAnnotation(newLabel);
    }

    @Test
    public void shouldBeAbleToConnectToDB() throws Exception {
        assertTrue(dbac.isConnected());
    }

    @Test
    public void testGetDeleteGetAddExperiment() throws Exception {

        assertTrue(dbac.hasExperiment(testExpId));
        Experiment e = dbac.getExperiment(testExpId);
        assertEquals(testExpId, e.getID());

        dbac.deleteExperiment(testExpId);
        assertFalse(dbac.hasExperiment(testExpId));
        e = dbac.getExperiment(testExpId);
        assertNull(e);

        dbac.addExperiment(testExpId);
        assertTrue(dbac.hasExperiment(testExpId));
        e = dbac.getExperiment(testExpId);
        assertEquals(testExpId, e.getID());
    }

    @Test
    public void shouldBeAbleToAnnotateExperimentFreeText()
            throws Exception {

        int res = dbac.annotateExperiment(testExpId,
                testLabelFT, testValueFT);
        assertEquals(1, res);
        Experiment e = dbac.getExperiment(testExpId);
        assertEquals(testValueFT, e.getAnnotations().get(testLabelFT));

    }

    @Test
    public void shouldBeAbleToDeleteExperimentAnnotationFreeText()
            throws Exception {

        dbac.annotateExperiment(testExpId, testLabelFT,
                testValueFT);

        int res = dbac.removeExperimentAnnotation(testExpId,
                testLabelFT);
        assertEquals(1, res);
        Experiment e = dbac.getExperiment(testExpId);
        assertFalse(e.getAnnotations().containsKey(testLabelFT));
    }

    @Test
    public void shouldBeAbleToTagExperimentDropDown()
            throws Exception {

        int res = dbac.annotateExperiment(testExpId,
                testLabelDD, testChoice);
        assertEquals(1, res);
        Experiment e = dbac.getExperiment(testExpId);
        assertEquals(testChoice, e.getAnnotations().get(testLabelDD));
    }

    @Test(expected = IOException.class)
    public void shouldNotBeAbleToTagExperimentWithInvalidDropdownChoice()
            throws Exception {

        String invalidValue = testChoice + "_invalid";
        try {
            dbac.annotateExperiment(testExpId, testLabelDD, invalidValue);
        } catch (Exception e) {
            throw e;
        } finally {
            Experiment e = dbac.getExperiment(testExpId);
            assertFalse(e.getAnnotations().containsKey(testLabelDD));
        }
    }

    @Test
    public void shouldBeAbleToSearchUsingExperimentID()
            throws Exception {
        Experiment e = dbac.getExperiment(testExpId);
        assertEquals(testExpId, e.getID());
    }

    @Test
    public void shouldReturnExperimentObjectContainingAnnotationOnSearch()
            throws Exception {

        Experiment e = dbac.getExperiment(testExpId);
        assertFalse(e.getAnnotations().containsKey(testLabelFT));
        dbac.annotateExperiment(testExpId, testLabelFT, testValueFT);
        e = dbac.getExperiment(testExpId);
        assertTrue(e.getAnnotations().containsKey(testLabelFT));
    }

    @Test
    public void shouldReturnExperimentObjectContainingMultipleAnnotationsOnSearch()
            throws Exception {

        dbac.annotateExperiment(testExpId, testLabelDD, testChoice);

        dbac.annotateExperiment(testExpId, testLabelFT, testValueFT);

        Experiment e = dbac.getExperiment(testExpId);
        assertEquals(2, e.getAnnotations().size());
        assertTrue(e.getAnnotations().containsKey(testLabelFT));
        assertTrue(e.getAnnotations().containsKey(testLabelDD));

    }

    @Test
    public void getAllAnnotationLabelsTest(){

    	ArrayList<String> allAnnotationlabels = dbac.getAllAnnotationLabels();

    	/*should be equal to 6 iff 4 entries in database,
    	  and 2 adds in beginning every testrun.*/
        assertEquals(2, allAnnotationlabels.size());

    	//a bit hardcoded, works if database contains this values before.
    	assertTrue(allAnnotationlabels.contains(testLabelDD));
    	assertTrue(allAnnotationlabels.contains(testLabelFT));
    }

    @Test
    public void changeFromOldLabelToNewLabelTest()
    		throws Exception{

    	ArrayList<String> allLabelsBefore = dbac.getAllAnnotationLabels();

    	if(allLabelsBefore.contains(testLabelFT)){

    		boolean succeed = dbac.changeAnnotationLabel(testLabelFT, newLabel);

    		assertTrue(succeed);

    		ArrayList<String> allLabelsAfter = dbac.getAllAnnotationLabels();
    		assertFalse(allLabelsAfter.contains(testLabelFT));
    		assertTrue(allLabelsAfter.contains(newLabel));
    	}else{
    		System.out.println("The old label did not exist in database!");
    		fail();
    	}
    }

}

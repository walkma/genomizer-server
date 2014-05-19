package command.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import command.CommandFactory;
import command.ProcessCommand;

public class ValidateProcessCommandTest {



	String uuid;



	@Before
	public void setup(){


	}

	@Test
	public void shouldValidateToTrue(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertTrue(processCommand.validate());
	}
	@Test
	public void shouldValidateToTrueWhenEmptyAuthor(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertTrue(processCommand.validate());
	}

	@Test
	public void shouldValidateToTrueWhenEmptyMetadata(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertTrue(processCommand.validate());
	}
	@Test
	public void shouldValidateToTrueWhenEmptyGRversion(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "a string of metadata";
		String genomeRelease = "";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);
		System.out.println(processCommand);

		assertTrue(processCommand.validate());
	}
	@Test
	public void shouldValidateToFalseWhenEmptyUsername(){

		String username = "";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateToFalseWhenEmptyFilename(){

		String username = "splutt";
		String filename = "";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}

	//TODO Insert more falsewhenempty tests

	@Test
	public void shouldValidateFalseWithoutFilename(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
		//					"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateFalseWithoutFileid(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
		//					"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateFalseWithoutExpid(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
		//					"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}

	@Test
	public void shouldValidateFalseWithoutProcesstype(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
		//					"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateFalseWithoutParameters(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
		//					"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateFalseWithoutMetadata(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
		//					"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateFalseWithoutGenomeRelease(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
		//					"\"genomeRelease\": \"" + genomeRelease + "\"," +
							"\"author\": \"" + author + "\"}";

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}
	@Test
	public void shouldValidateFalseWithoutAuthor(){

		String username = "splutt";
		String filename = "filename1234";
		String fileid = "1";
		String expid = "Exp1";
		String processtype = "rawtoprofile";
		String parameters = "\"param1\"," +
							"\"param2\"," +
							"\"param3\"," +
							"\"param4\"";
		String metadata = "astringofmetadata";
		String genomeRelease = "hg38";
		String author = "yuri";

		CommandFactory cmdf = new CommandFactory();



		String json = "{" +
							"\"filename\": \"" + filename + "\"," +
							"\"fileid\": \"" + fileid + "\"," +
							"\"expid\": \"" + expid + "\"," +
							"\"processtype\": \"" + processtype + "\"," +
							"\"parameters\": [" + parameters + "]," +
							"\"metadata\": \"" + metadata + "\"," +
							"\"genomeRelease\": \"" + genomeRelease + "\"}";
		//					"\"author\": \"" + author + "\"}"

		ProcessCommand processCommand = (ProcessCommand)cmdf.createProcessCommand(json, username);


		assertFalse(processCommand.validate());
	}




}

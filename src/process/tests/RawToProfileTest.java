package process.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import process.classes.RawToProfileConverter;

public class RawToProfileTest {
	RawToProfileConverter rtp = null;
	String path = "test/male.sam";
	String bowTie = "bowtie -a -m 1 --best -p 10 -v 2 ";
	//"d_melanogaster_fb5_22 -q reads/MOF_male_wt_reads_sample.fastq -S " +path;
	String[] parameters = new String[]{bowTie};

	@Before
	public void setup() {
		rtp = new RawToProfileConverter();
	}

	@After
	public void tearDown() {
		rtp = null;
	}

	@Test
	public void ShouldNotCrash() throws InterruptedException, IOException {
		String inFolder = "HEJ";
		String expected = "Indata is not in the correct format";
		String outFilePath = null;
		String actual = rtp.procedure(parameters, inFolder, outFilePath);
		assertEquals(expected, actual);
	}
	@Test
	public void ShouldNotRunWhenParametersIsNull() throws InterruptedException, IOException {
		String[] parameters = null;
		String inFolder = "resources";
		String expected = "Indata is not in the correct format";
		String outFilePath = "resources";
		String actual = rtp.procedure(parameters, inFolder, outFilePath);
		assertEquals(expected, actual);
	}

	@Test
	public void ShouldNotRunWithFiveParameters() throws InterruptedException, IOException {
		String inFolder = "HEJ";
		String outFilePath = "";
		String expected = "Indata is not in the correct format";
		String[] param = new String[]{"one","two","three","four","five"};
		String actual = rtp.procedure(param,  inFolder, outFilePath);
		assertEquals(expected, actual);
	}


	@Test
	public void ShouldNotRunWhenNotFindingFiles() throws InterruptedException, IOException {
		String inFolder = "HEJ";
		String outFilePath = "";
		String expected = "Indata is not in the correct format";
		String[] param = new String[]{"one","two","three","four"};
		String actual = rtp.procedure(param, inFolder, outFilePath);
		assertEquals(expected, actual);
	}

//	@Test
//	public void ShouldNot

//	@Test
//	public void shouldParseAStringToAnArrayOfStrings() {
//		String[] input = new String[]{"hello this is a test"};
//		String[] output = rtp.getBowTieParameters();
//		assertArrayEquals(new String[]{"hello","this","is","a","test"}, output);
//	}

//	@Test(expected = IOException.class)
//	public void ExceptedIOException() {
//		rtp.execute(new String[]{"HEJ"});
//	}

//	@Test
//	public void StandardShouldGetString() {
//		rtp.standardParamProcedure(new String[]{""});
//	}
//
//	@Test
//	public void SpecificShouldGetString() {
//		rtp.specificParamProcedure(new String[]{""});
//	}
}
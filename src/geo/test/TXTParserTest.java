package geo.test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import geo.GEOFileTuple;
import geo.TXTParser;

import org.junit.Before;
import org.junit.Test;

public class TXTParserTest {

	private String path = "src/geo/test/GSE47236_series_matrix.txt";
	private ArrayList<GEOFileTuple> gl;

	@Before
	public void setUp() {
		try {
			gl = TXTParser.readFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReceiveList() {
		try {
			assertNotNull(TXTParser.readFile(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testFirstSeriesRelation() {
		assertTrue(gl.get(0).seriesRelation
				.equals("SRA: http://www.ncbi.nlm.nih.gov/sra?term=SRP023360"));
	}

	@Test
	public void testFirstSeriesStatus() {
		assertTrue(gl.get(0).seriesStatus
				.equals("Public on May 28 2013"));
	}

	@Test
	public void testFirstSeriesContributor1() {
		assertTrue(gl.get(0).seriesContributors[0]
				.equals("Gary Karpen"));
	}

	@Test
	public void testFirstSeriesContributor2() {
		assertTrue(gl.get(0).seriesContributors[1]
				.equals("Sarah Elgin"));
	}

	@Test
	public void testFirstSeriesContributorLast() {
		assertTrue(gl.get(0).seriesContributors[13]
				.equals("Cameron Kennedy"));
	}

}

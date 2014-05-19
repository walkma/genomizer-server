package conversion.tests;

import org.junit.Test;

import conversion.classes.GenomeReleaseConverter;

public class GenomeReleaseConverterTest {


	@Test
	public void test() {
		GenomeReleaseConverter handler = new GenomeReleaseConverter();
		handler.procedure("test.bed", "temp.bed", "dm2ToDm3.over.chain.gz", "unlifted.bed");
	}


}

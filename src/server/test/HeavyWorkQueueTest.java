package server.test;

import org.junit.Before;
import org.junit.Test;

import server.WorkHandler;

public class HeavyWorkQueueTest {



	private WorkHandler queue;

	@Before
	public void setUp() {

		queue = new WorkHandler();
	}


	@Test
	public void shouldHaveEmptyQueue(){

	}




}

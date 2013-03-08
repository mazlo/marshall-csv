package org.gesis.zl.marshalling.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import org.gesis.zl.marshalling.CsvUnmarshallerFactory;
import org.gesis.zl.marshalling.Unmarshaller;
import org.junit.Before;
import org.junit.Test;

public class UnmarshallerBooleanColumnTest {

	private Unmarshaller<RowBooleanColumn> unmarshaller;

	@Before
	public void testUnmarshaller() throws FileNotFoundException
	{
		unmarshaller = CsvUnmarshallerFactory.createDefaultCsvUnmarshaller( RowBooleanColumn.class, new FileReader( new File( "src/test/resources/test-input_boolean-column.csv" ) ) );
		assertNotNull( unmarshaller );
	}

	@Test
	public void getAll() throws FileNotFoundException
	{
		List<RowBooleanColumn> all = unmarshaller.getAll();
		assertNotNull( all );
		assertEquals( 3, all.size() );
	}

	@Test
	public void checked()
	{
		RowBooleanColumn row = unmarshaller.getNext();
		assertNotNull( row );

		assertTrue( row.isChecked() );
	}


}

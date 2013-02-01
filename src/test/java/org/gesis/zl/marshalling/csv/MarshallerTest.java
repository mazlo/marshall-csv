package org.gesis.zl.marshalling.csv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.StringWriter;

import org.gesis.zl.marshalling.Marshaller;
import org.junit.Before;
import org.junit.Test;

public class MarshallerTest {

	private Marshaller<Row> marshaller;
	private CsvAnnotationInterpreter<Row> reader;

	@Before
	public void testMarshaller() throws IOException
	{
		reader = new CsvAnnotationInterpreterImpl<Row>( Row.class );
		assertNotNull( reader );

		marshaller = new CsvMarshaller<Row>( Row.class, new StringWriter() );
		assertNotNull( marshaller );
	}

	@Test
	public void testWriteHeaders()
	{
		marshaller.writeHeader();

		String[] columns = marshaller.getWriter().toString().trim().split( "," );

		assertEquals( "\"name\"", columns[0] );
		assertEquals( "\"description\"", columns[1] );
	}

	@Test
	public void testWriteNext()
	{
		Row row = new Row();

		row.setTarget_name( "target_test_name" );
		row.setTarget_description( "target_test_value" );

		marshaller.writeNext( row );

		String[] columns = marshaller.getWriter().toString().trim().split( "," );
		assertEquals( 2, columns.length );

		assertEquals( "\"target_test_name\"", columns[0] );
		assertEquals( "\"target_test_value\"", columns[1] );
	}

	@Test
	public void testWriteNextWithMissingValue()
	{
		Row row = new Row();

		row.setTarget_description( "test_description" );

		marshaller.writeNext( row );

		String[] columns = marshaller.getWriter().toString().trim().split( "," );
		assertEquals( 2, columns.length );

		assertEquals( "\"\"", columns[0] );
		assertEquals( "\"test_description\"", columns[1] );
	}

}

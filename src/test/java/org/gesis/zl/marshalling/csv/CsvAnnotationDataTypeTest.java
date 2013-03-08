package org.gesis.zl.marshalling.csv;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.gesis.zl.marshalling.annotations.CsvAnnotationInterpreterFactory;
import org.gesis.zl.marshalling.annotations.DataType;
import org.gesis.zl.marshalling.annotations.DataType.Type;
import org.junit.Before;
import org.junit.Test;

public class CsvAnnotationDataTypeTest
{
	private CsvAnnotationInterpreter<RowBooleanColumn> interpreter;

	@Before
	public void init()
	{
		interpreter = CsvAnnotationInterpreterFactory.createDefaultCsvAnnotationInterpreter( RowBooleanColumn.class );
		assertNotNull( interpreter );
	}

	@Test
	public void getBooleanDataType()
	{
		DataType dataType = interpreter.getDataType( "checked" );
		assertNotNull( dataType );
		assertTrue( dataType.type() == Type.BOOLEAN );
	}

	@Test
	public void isBooleanDataType()
	{
		boolean booleanType = interpreter.isBooleanType( "checked" );
		assertTrue( booleanType );

		booleanType = interpreter.isBooleanType( "bitColumn1" );
		assertFalse( booleanType );
	}
}

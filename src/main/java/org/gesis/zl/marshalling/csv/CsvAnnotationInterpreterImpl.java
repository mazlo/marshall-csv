package org.gesis.zl.marshalling.csv;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.gesis.zl.marshalling.annotations.CsvConfiguration;
import org.gesis.zl.marshalling.annotations.DefaultAnnotationInterpreter;
import org.gesis.zl.marshalling.annotations.OutputField;

/**
 * Interprets the annotations <i>InputField</i> and <i>OutputField</i> in a
 * csv-like manner.
 * 
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class.
 */
public class CsvAnnotationInterpreterImpl<T> extends DefaultAnnotationInterpreter<T> implements CsvAnnotationInterpreter<T>
{

	private List<String> outputColumnNames;

	public CsvAnnotationInterpreterImpl( Class<T> annotatedClass )
	{
		super( annotatedClass );

		this.outputColumnNames = new ArrayList<String>();

		// temporary maps to store the positions of the fields
		Map<Integer, String> o_fieldNames = new TreeMap<Integer, String>();
		Map<Integer, String> o_columnNames = new TreeMap<Integer, String>();

		// have a look on the declared fields, i.e. attributes
		for ( Field field : annotatedClass.getDeclaredFields() )
		{
			// read the OutputField property
			if ( field.isAnnotationPresent( OutputField.class ) )
			{
				int position = field.getAnnotation( OutputField.class ).position();

				o_fieldNames.put( position, field.getName() );

				// use field name as column name if not specified by the "name"
				// property of OutputField
				String columnName = field.getAnnotation( OutputField.class ).name();
				if ( StringUtils.equals( columnName, OutputField.DEFAULT_COLUMN_NAME ) )
					columnName = field.getName();

				o_columnNames.put( position, columnName );
			}
		}

		this.outputColumnNames = new ArrayList<String>( o_columnNames.values() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.csv.CsvAnnotationReader#skipFirstLine()
	 */
	public boolean skipFirstLine() {
		CsvConfiguration annotation = getAnnotatedClass().getAnnotation( CsvConfiguration.class );

		// default is to skip the line
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_SKIP_FIRST_LINE;

		return annotation.skipFirstLine();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.csv.CsvAnnotationReader#getSeparator()
	 */
	public char getSeparator()
	{
		CsvConfiguration annotation = getAnnotatedClass().getAnnotation( CsvConfiguration.class );

		// default
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_SEPARATOR;

		return annotation.separator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.csv.CsvAnnotationReader#getOutputColumnNames()
	 */
	public List<String> getOutputColumnNames()
	{
		return this.outputColumnNames;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gesis.zl.marshalling.csv.CsvAnnotationReader#getQuotationCharacter()
	 */
	public char getQuotationCharacter()
	{
		CsvConfiguration annotation = getAnnotatedClass().getAnnotation( CsvConfiguration.class );

		// default
		if ( annotation == null )
			return CsvConfiguration.DEFAULT_QUOTATION_CHARACTER;

		return annotation.quoteChar();
	}

}

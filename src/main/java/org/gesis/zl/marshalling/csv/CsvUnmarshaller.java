package org.gesis.zl.marshalling.csv;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.gesis.zl.marshalling.Unmarshaller;

import au.com.bytecode.opencsv.CSVReader;

public class CsvUnmarshaller<T> implements Unmarshaller<T> {

	private CsvAnnotationInterpreter<T> annotationInterpreter;
	private Class<T> bean;

	private CSVReader csvReader;

	private Reader reader;

	boolean firstLineSkipped = false;

	public CsvUnmarshaller(CsvAnnotationInterpreter<T> annotationReader, Reader reader)
	{
		this.annotationInterpreter = annotationReader;
		this.bean = annotationReader.getAnnotatedClass();

		// check if first line should be skipped
		this.firstLineSkipped = !this.annotationInterpreter.skipFirstLine();

		this.reader = reader;
		this.csvReader = createCsvReader();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.spelling.paradata.Unmarshaller#getAll()
	 */
	public List<T> getAll()
	{
		this.csvReader = createCsvReader();

		// return set of beans
		List<T> beans = new ArrayList<T>();

		while ( true )
		{
			T instance = getNext();

			// continue unmarshalling until no more data can be read
			if ( instance == null )
				break;

			beans.add( instance );
		}

		try
		{
			this.csvReader.close();
		} catch ( IOException e )
		{
			System.err.println( "Could not close the reader. The data should be able to be accessed." );
		}

		return beans;
	}

	/**
	 * Creates a new csv reader instance.
	 * 
	 * @return
	 */
	private CSVReader createCsvReader()
	{
		return new CSVReader( reader, this.annotationInterpreter.getSeparator(), this.annotationInterpreter.getQuotationCharacter() );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.spelling.paradata.Unmarshaller#getNext()
	 */
	public T getNext()
	{
		String[] values = getValuesFromCsvReader();

		if ( values == null || values.length == 0 )
			return null;

		// get the field mappings
		List<String> fieldNames = this.annotationInterpreter.getInputFieldNames();

		// the bean instance, i.e. new row
		T instance;

		try
		{
			instance = bean.newInstance();

			// set the values for the fields of the bean
			for ( String fieldName : fieldNames )
			{
				int position = annotationInterpreter.getPositionOf( fieldName );

				if ( position < 0 )
					continue;

				if ( position >= values.length )
					continue;

				String value = values[position];

				if ( annotationInterpreter.getIgnoredValues( fieldName ).contains( value ) )
					// ignore this value
					continue;

				// checks if column is of boolean type
				if ( annotationInterpreter.isBooleanType( fieldName ) )
				{
					String defaultValue = annotationInterpreter.getDefaultValueForType( fieldName );

					// the default value of the dataType property must be equal
					// to the value in the cell
					if ( StringUtils.equals( defaultValue, value ) )
						BeanUtils.setProperty( instance, fieldName, Boolean.TRUE );
					else
						BeanUtils.setProperty( instance, fieldName, Boolean.FALSE );

					continue;
				}

				BeanUtils.setProperty( instance, fieldName, value );
			}

			return instance;

		} catch ( InstantiationException e1 )
		{
			System.err.println( "Could not instantiate bean for class:" + bean.getCanonicalName() );
		} catch ( IllegalAccessException e )
		{
			System.err.println( "Could not access setter method for a field." );
		} catch ( InvocationTargetException e )
		{
			System.err.println( "Could not invoke setter method for a field." );
		}

		return null;

	}

	/**
	 * Returns the values from the csv reader.
	 * 
	 * @return
	 */
	private String[] getValuesFromCsvReader()
	{
		String[] values = new String[0];

		try
		{
			// skip the first line if configurated so
			if ( !this.firstLineSkipped && this.annotationInterpreter.skipFirstLine() )
			{
				this.csvReader.readNext();
				this.firstLineSkipped = true;
			}

			// get values in a row
			values = this.csvReader.readNext();
		} catch ( IOException e )
		{
			System.err.println( "Could not retrieve values from csv reader." );
		}

		return values;
	}
}

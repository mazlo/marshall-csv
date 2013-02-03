package org.gesis.zl.marshalling.csv;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.gesis.zl.marshalling.Marshaller;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvMarshaller<T> implements Marshaller<T> {

	private CsvAnnotationInterpreterImpl<T> annotationReader;
	private CSVWriter csvWriter;

	private Writer writer;

	public CsvMarshaller( Class<T> annotatedBean, Writer writer ) throws IOException
	{
		this.annotationReader = new CsvAnnotationInterpreterImpl<T>( annotatedBean );

		this.writer = writer;
		this.csvWriter = new CSVWriter( writer, this.annotationReader.getSeparator(), this.annotationReader.getQuotationCharacter() );
	}

	/* (non-Javadoc)
	 * @see org.gesis.zl.spelling.paradata.Marshaller#writeAll(java.util.List)
	 */
	public void writeAll( List<T> beans )
	{
		this.csvWriter = createCsvWriter();

		writeHeader();

		// write all the beans to the csv file
		for ( T bean : beans )
		{
			writeNext( bean );
		}

		try
		{
			this.csvWriter.flush();
			this.csvWriter.close();
		} catch ( IOException e )
		{
			System.err.println( "Unable to flush the writers content." );
		}
	}

	/**
	 * Creates a new csv writer instance.
	 * 
	 * @return
	 */
	private CSVWriter createCsvWriter()
	{
		return new CSVWriter( this.writer, this.annotationReader.getSeparator() );
	}

	/* (non-Javadoc)
	 * @see org.gesis.zl.spelling.paradata.Marshaller#writeNext(T)
	 */
	public void writeNext( T instance )
	{
		if ( instance == null )
			return; // the instance cannot be null

		// list is sorted according to the positions of the output fields
		List<String> outputColumnNames = this.annotationReader.getOutputFieldNames();

		// resulting values
		String[] csvValues = new String[outputColumnNames.size()];

		// populates the array with values
		for ( int i = 0; i < outputColumnNames.size(); i++ )
		{
			// i is the position of the output fields
			String fieldName = outputColumnNames.get( i );

			String value = "";

			try
			{
				value = BeanUtils.getProperty( instance, fieldName );

				if ( value == null )
					value = "";

			} catch ( IllegalAccessException e )
			{
				System.err.println( "Could not access the setter method for the field of the instance" );
				continue;
			} catch ( InvocationTargetException e )
			{
				System.err.println( "Could not access the setter method for the field of the instance" );
				continue;
			} catch ( NoSuchMethodException e )
			{
				System.err.println( "Could not set the value for the field: " + fieldName + ". Setting empty value instead." );
				value = "";
			}

			// set the value for the position
			csvValues[i] = value;
		}

		this.csvWriter.writeNext( csvValues );
	}

	/* (non-Javadoc)
	 * @see org.gesis.zl.spelling.paradata.Marshaller#writeHeader()
	 */
	public boolean writeHeader()
	{
		if ( csvWriter == null )
			return false;

		List<String> headers = annotationReader.getOutputColumnNames();

		csvWriter.writeNext( headers.toArray( new String[0] ) );

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gesis.zl.marshalling.Marshaller#getWriter()
	 */
	public Writer getWriter()
	{
		return this.writer;
	}
}

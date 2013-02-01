package org.gesis.zl.marshalling.annotations;

import org.gesis.zl.marshalling.csv.CsvAnnotationInterpreter;
import org.gesis.zl.marshalling.csv.CsvAnnotationInterpreterImpl;

public class AnnotationInterpreterFactory
{

	/**
	 * Creates an annotation reader for the csv specific configurations.
	 * 
	 * @param annotatedClass
	 * @return
	 */
	public static <T> CsvAnnotationInterpreter<T> createCsvAnnotationReader(Class<T> annotatedClass)
	{
		return new CsvAnnotationInterpreterImpl<T>( annotatedClass );
	}
}

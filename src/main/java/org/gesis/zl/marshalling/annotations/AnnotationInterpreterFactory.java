package org.gesis.zl.marshalling.annotations;

import org.gesis.zl.marshalling.csv.CsvAnnotationInterpreter;
import org.gesis.zl.marshalling.csv.CsvAnnotationInterpreterImpl;

public class AnnotationInterpreterFactory
{

	/**
	 * Creates a default annotation interpreter for the csv specific
	 * configurations.
	 * 
	 * @param annotatedClass
	 * @return
	 */
	public static <T> CsvAnnotationInterpreter<T> createDefaultCsvAnnotationInterpreter(Class<T> annotatedClass)
	{
		return new CsvAnnotationInterpreterImpl<T>( annotatedClass );
	}
}

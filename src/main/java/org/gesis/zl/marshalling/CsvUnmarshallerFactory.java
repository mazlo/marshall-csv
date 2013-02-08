package org.gesis.zl.marshalling;

import java.io.Reader;

import org.gesis.zl.marshalling.annotations.CsvAnnotationInterpreterFactory;
import org.gesis.zl.marshalling.csv.CsvAnnotationInterpreter;
import org.gesis.zl.marshalling.csv.CsvUnmarshaller;

public class CsvUnmarshallerFactory
{

	/**
	 * Creates a default CsvUnmarshaller-instance with the default
	 * CsvAnnotationInterpreter-instance. The annotation interpreter is created
	 * by invoking the AnnotationInterpreterFactory method
	 * <i>createDefaultCsvAnnotationInterpreter</i>.
	 * 
	 * @param annotatedClass
	 * @param reader
	 * @return
	 */
	public static <T> Unmarshaller<T> createDefaultCsvUnmarshaller(Class<T> annotatedClass, Reader reader)
	{
		// the annotation interpreter
		CsvAnnotationInterpreter<T> interpreter = CsvAnnotationInterpreterFactory.createDefaultCsvAnnotationInterpreter( annotatedClass );

		// the csv unmarshaller
		Unmarshaller<T> unmarshaller = new CsvUnmarshaller<T>( interpreter, reader );

		return unmarshaller;
	}
}

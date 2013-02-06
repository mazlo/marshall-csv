package org.gesis.zl.marshalling.csv;

import java.util.List;

import org.gesis.zl.marshalling.annotations.AnnotationInterpreter;

/**
 * Interprets the annotation specific for data stored in csv-format. Provides
 * methods to adequately interpret csv annotations.
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class <i>T</i>.
 */
public interface CsvAnnotationInterpreter<T> extends AnnotationInterpreter<T> {

	/**
	 * Reads the annotations on the desired resulting bean class. In particular,
	 * this method analyses the <b>OutputField</b> annotations and returns the
	 * value for property <i>name</i>. The list of field names is sorted with
	 * respect to the <i>position</i>-property.
	 * 
	 * @return
	 */
	public abstract List<String> getOutputColumnNames();

	/**
	 * Returns the value of the <i>skipFirstLine</i>-property, configurated in
	 * the class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public abstract boolean skipFirstLine();

	/**
	 * Returns the value of the <i>separator</i>-property, configurated in the
	 * class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public abstract char getSeparator();

	/**
	 * Returns the value of the <i>quoteChar</i>-property, configured in the
	 * class for which the CsvConfiguration-Annotation was applied.
	 * 
	 * @return
	 */
	public abstract char getQuotationCharacter();

}

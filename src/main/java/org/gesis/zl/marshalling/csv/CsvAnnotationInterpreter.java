package org.gesis.zl.marshalling.csv;

import java.util.List;
import java.util.Set;

import org.gesis.zl.marshalling.annotations.AnnotationInterpreter;

/**
 * Generic interface for all Csv annotation readers. Provides methods to
 * adequately interpret csv annotations.
 * 
 * @author matthaeus
 * 
 * @param <T>
 *            The annotated class <i>T</i>.
 */
public interface CsvAnnotationInterpreter<T> extends AnnotationInterpreter<T> {

	/**
	 * Reads the annotations on the annotated bean class. In particular, this
	 * method analyses the <b>InputColum</b> annotations on fields and returns
	 * its name. The list of field names is sorted with respect to the
	 * <i>position</i>-property.
	 * 
	 * @return
	 */
	public abstract List<String> getInputFieldNames();

	/**
	 * Reads the annotations on the annotated bean class. In particular, this
	 * method analyses the <b>OutputColum</b> annotations on fields and returns
	 * its name. The list of field names is sorted with respect to the
	 * <i>position</i>-property.
	 * 
	 * @return
	 */
	public abstract List<String> getOutputFieldNames();

	/**
	 * Reads the annotations on the desired resulting bean class. In particular,
	 * this method analyses the <b>OutputColum</b> annotations and returns the
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

	/**
	 * Returns the value of the <i>position</i>-property for the given field.
	 * 
	 * @param fieldName
	 * @return
	 */
	public abstract int getPositionOf( String fieldName );

	/**
	 * Returns the set of ignored values, defined in the
	 * <i>ignoredValues</i>-property for the given field.
	 * 
	 * @return
	 */
	public abstract Set<String> getIgnoredValues(String fieldName);
}

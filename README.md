Data stored in CSV-format (Comma Separated Values) can easily be mapped to fields of a plain old java object (POJO) using Java-annotations. 

This package provides an implementation for the [marshall-api](https://github.com/mazlo/marshall-api), a generic api for marshalling and unmarshalling data using Java-annotations.

## Usage

### The Annotated POJO Class

In order to instantiate an Unmarshaller/Marshaller, you will firstly need a Java-class that contains the fields, to which the external data should be mapped to. The [marshall-api](https://github.com/mazlo/marshall-api) provides some ready to be used annotations, like 

* `InputField`, used for unmarshalling
* `OutputField`, used for marshalling

Place this annotations on your fields of the POJO and

**provide getter- and setter-methods**. 

For instance,

    public class BeanClass {
    
        @InputField( position=0 )
        private String description;
        
        @OutputField ( name="comment" )
        private String target_description;
        
        // getter-/setter-methods
    }

For the annotations to be recognized, a so called `AnnotationInterpreter` is needed. The `DefaultAnnotationInterpreter` provides a default implementation, which can be found in the [marshall-api](https://github.com/mazlo/marshall-api).

Factory-classes are provided to create the `CsvUnmarshaller` and `CsvMarshaller` objects, as well as `CsvAnnotationInterpreter`.

### Using the Default Implementation

The easiest way to instantiate the Unmarshaller/Marshaller is through methods provided by the Factory-classes.

For instance,

    Unmarshaller<BeanClass> unmarshaller = 
        CsvUnmarshallerFactory
        .createDefaultCsvUnmarshaller( BeanClass.class, new FileReader(...) );

returns an object of `CsvUnmarshaller`, which was instantiated with the `CsvAnnotationInterpreter`. Without using the Factory-class you must instantiate an `AnnotationInterpreter` by yourself, e.g.

    // the annotation interpreter
    CsvAnnotationInterpreter<T> interpreter = 
        CsvAnnotationInterpreterFactory
        .createDefaultCsvAnnotationInterpreter( annotatedClass );

    // the csv unmarshaller
    Unmarshaller<T> unmarshaller = new CsvUnmarshaller<T>( interpreter, reader );
    
### Marshalling/Unmarshalling

The `Unmarshaller/Marshaller`-instance provides methods to read/write the instantiated objects. For instance

* `unmarshaller.getAll();`, returns a list of T
* `marshaller.writeNext(t);`, writes an instance t of type T

If you have large csv-files a step by step unmarshalling process would be more appropriate:

* `unmarshaller.getNext();`

returns the next unmarshalled instance of type `T`.



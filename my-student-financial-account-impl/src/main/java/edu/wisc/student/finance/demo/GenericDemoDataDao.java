/**
 *
 */
package edu.wisc.student.finance.demo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;

/**
 * Abstract class providing methods for loading, and accessing demo data from a json file.
 * 
 * 
 * Subclasses must provide two vital bits of information:
 * 1) Subclasses must pass in a type via {@link #GenericDemoDataDao(Class)}. Jackson needs to know which type
 * to unserialize. The Type paramter of the subclass will not suffice due to type erasure.
 * 
 * 2) Subclasses must ensure that {@link #getDemoDataPath()} returns a path to a json formatted file on the
 * classpath. The file must contain properly formatted json objects. The json objects must be of the
 * same type you specified as the classes type paramater and the same type passed to the constructor.
 * 
 * @author Collin Cudd
 */
public abstract class GenericDemoDataDao<T> {
  /**
   * The {@link ObjectMapper} used to serialize/unserialize the data
   */
  protected ObjectMapper mapper = new ObjectMapper();
  protected Map<String, List<T>> data = Collections.emptyMap();
  private final Class<T> typeParameterClass;
  
  /**
   * Jackson needs type information in order to deserialize, we can't rely on the paramater T due to type erasure, so subclasses must pass in type information here.
   * @see http://stackoverflow.com/questions/3437897/how-to-get-class-instance-of-generics-type-t
   * @param typeParameterClass
   */
  public GenericDemoDataDao(Class<T> typeParameterClass) {
    this.typeParameterClass = typeParameterClass;
}

  /**
   * Initialization method called {@link PostConstruct}.
   *
   * @see #readDemoData()
   * @throws IOException
   */
  @PostConstruct
  protected void init() throws IOException {
    data = readDemoData();
  }

  /**
   * @param key
   * @return a {@link List} of values for the given key.
   */
  protected List<T> getDemoData(String key) {
    List<T> list = data.get(key);
    if (list == null) {
      return Collections.<T>emptyList();
    } else {
      // clone the list so mutations don't go back to persistence
      return new ArrayList<T>(list);
    }
  }

  /**
   * Reads the demo data and returns it as a {@link Map}.
   *
   * @return
   * @throws IOException
   */
  protected Map<String, List<T>> readDemoData() throws IOException {
    CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, typeParameterClass);
    JavaType stringType = mapper.getTypeFactory().constructType(String.class);
    MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, stringType, collectionType);
    try (InputStream is = new ClassPathResource(getDemoDataPath()).getInputStream()) {
      return mapper.readValue(is, mapType);
    }
  }
  /**
   * @return the path to the file containing the demo data
   */
  public abstract String getDemoDataPath();
}

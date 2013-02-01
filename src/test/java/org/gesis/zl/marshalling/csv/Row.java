package org.gesis.zl.marshalling.csv;

import org.gesis.zl.marshalling.annotations.CsvConfiguration;
import org.gesis.zl.marshalling.annotations.InputField;
import org.gesis.zl.marshalling.annotations.OutputField;

@CsvConfiguration
public class Row {

	private int id1;

	private int id2;

	@InputField( position = 2 )
	private String name;

	@InputField( position = 3, ignoreValues = { "test4", "test5" } )
	private String description;

	@OutputField( position = 1, name = "description" )
	private String target_description;

	@OutputField( position = 0, name = "name" )
	private String target_name;

	public int getId1()
	{
		return id1;
	}

	public void setId1( int id1 )
	{
		this.id1 = id1;
	}

	public int getId2()
	{
		return id2;
	}

	public void setId2( int id2 )
	{
		this.id2 = id2;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getTarget_name()
	{
		return target_name;
	}

	public void setTarget_name( String target_name )
	{
		this.target_name = target_name;
	}

	public String getTarget_description()
	{
		return target_description;
	}

	public void setTarget_description( String target_description )
	{
		this.target_description = target_description;
	}

}

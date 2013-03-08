package org.gesis.zl.marshalling.csv;

import org.gesis.zl.marshalling.annotations.CsvConfiguration;
import org.gesis.zl.marshalling.annotations.DataType;
import org.gesis.zl.marshalling.annotations.DataType.Type;
import org.gesis.zl.marshalling.annotations.InputField;

@CsvConfiguration
public class RowBooleanColumn {

	@InputField( position = 0 )
	private int bitColumn1;

	@InputField( position = 4, dataType = @DataType( type = Type.BOOLEAN, defaultValue = "x" ) )
	private boolean checked;

	public int getBitColumn1()
	{
		return bitColumn1;
	}

	public void setBitColumn1(int bitColumn1)
	{
		this.bitColumn1 = bitColumn1;
	}

	public boolean isChecked()
	{
		return checked;
	}

	public void setChecked(boolean checked)
	{
		this.checked = checked;
	}

}

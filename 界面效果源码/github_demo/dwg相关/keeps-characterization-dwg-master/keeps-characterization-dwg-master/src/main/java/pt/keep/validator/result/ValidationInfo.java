package pt.keep.validator.result;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "validation")
@XmlType(propOrder = { "valid", "validationError" })
public class ValidationInfo {
	private boolean valid;
	private String validationError;

	@XmlElement
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@XmlElement(required=false)
	public String getValidationError() {
		return validationError;
	}

	public void setValidationError(String validationError) {
		this.validationError = validationError;
	}

	
	

}

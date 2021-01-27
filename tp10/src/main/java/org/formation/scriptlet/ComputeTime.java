package org.formation.scriptlet;

import net.sf.jasperreports.engine.JRDefaultScriptlet;
import net.sf.jasperreports.engine.JRScriptletException;

public class ComputeTime extends JRDefaultScriptlet {

	long start;
	
	@Override
	public void beforeReportInit() throws JRScriptletException {
		super.beforeReportInit();
		
		start = System.currentTimeMillis();
	}

	public Long getComputeTime() {
		return System.currentTimeMillis() - start;
	}

	@Override
	public void beforePageInit() throws JRScriptletException {
		
		super.beforePageInit();
		String fl = (String)getVariableValue("FIRST_LETTER");
		if ( fl != null ) {
			setVariableValue("LAST_FIRST_LETTER", fl);
		}
		System.out.println(getVariableValue("LAST_FIRST_LETTER"));
	}


	
}

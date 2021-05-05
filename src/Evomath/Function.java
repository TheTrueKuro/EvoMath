package Evomath;

import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class Function {

	protected String function;
	protected final ScriptEngineManager mgr;
	protected final ScriptEngine engine;

	public Function(String function) {
		this.function = function.toLowerCase();
		mgr = new ScriptEngineManager(null);
		engine = mgr.getEngineByName("JavaScript");
	}

	public Function(Function f) {
		this.function = f.function;
		mgr = new ScriptEngineManager(null);
		engine = mgr.getEngineByName("JavaScript");
	}

	public double get(double x) throws ScriptException {
		Double val = (Double) engine.eval(function);
		return val;	
	}
}

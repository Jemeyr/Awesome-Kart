package Controller;

public enum ControllerType {
	XBOX("x-box"), KEYBOARD("keyboard");
	
	private String name;
	
	ControllerType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}

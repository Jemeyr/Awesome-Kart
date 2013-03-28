package World;

public enum EntityType {
	ROCKET("rocket"), MINE("mine");
	
	private String name;
	
	EntityType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}

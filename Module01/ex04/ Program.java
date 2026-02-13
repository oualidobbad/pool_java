package Module01.ex04;

class Person {

	private int a;
	public Person(int a){
		this.a = a;
	}
	void role() {
		System.out.println("I am a person.");
	}
}

class Father extends Person {
  
	public Father(){
		System.out.println("hello");
		super(3);
	}
	@Override
	void role() {
		System.out.println("I am a father.");
		super.role();
	}
	void print(){
		System.out.println("print methode.");
	}
}

class  Program {
    public static void main(String[] args) {
      
        Person p = new Father();
        
        p.role();
      	// ((Father) p).print();
    }
}


public class Deneme {
	int a = 3;
	static int s = 3;
	
	public static void sta()
	{
		//a = 4;
		s = 34;
	}
	
	public void n()
	{
		s = 4;
		a = 4;
		this.a = 3;
		this.s= 3;
		sta();
		this.sta();
	}
	
	public static void main(String[] args) {
		Object o = new String("3344");
		String s = new String();	
		System.out.println(o.hashCode());
		System.out.println(((String) o).length());
	}
}

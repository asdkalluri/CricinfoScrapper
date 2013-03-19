
public class TestforReg {


	public static void main(String[] args) {
		String input = "out (Botha/†de Villiers/sachin)";
		
			input=input.replaceAll("\\(", "");
			input=input.replaceAll("\\)", "");
			input=input.replaceAll("out ", "");
			String[] runout = input.split("/");
			for(int i=0;i<runout.length;i++)
			{
				System.out.println(runout[i].replaceFirst("†", ""));
			}
		
		
	}
	
}

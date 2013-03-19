
public class TestforReg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "out (Botha/†de Villiers/sachin)";
		String str = "MS Dhoni";
		
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

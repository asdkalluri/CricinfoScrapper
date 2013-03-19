
public class TestforReg {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "(1nb";
		String str = "MS Dhoni";
		if(input.equals(""))
		{
			
		}
		else
		{
			input=input.replaceAll("\\(", "");
			input=input.replaceAll("\\)", "");
			String[] extra = input.split(", ");
			if(extra.length>1)
			{
				extra[0]=extra[0].replace("nb", "");
				extra[1]=extra[1].replace("w", "");
			}
			else
			{
				if(extra[0].contains("w"))
					extra[0]=extra[0].replace("w", "");
				else
					extra[0]=extra[0].replace("nb", "");
			}
			System.out.println(extra[0]);
			System.out.println(extra[1]);
		}
		
		
		
	}
	
}

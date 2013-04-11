package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Utility {

	public static boolean isDebugging = true;
	public static final int optionLines = 1;
	public static final int optionString = 2;
	public static final String space =" ";
	public static final String newLine ="\n";
	public static final String emptyLine ="";

	
	public static ArrayList<String> readFile(String name)
	{

		ArrayList<String> sentences = new ArrayList<String>();;
		StringBuffer sentence = new StringBuffer();

		try{
			FileInputStream fileStream = new FileInputStream(name);
			DataInputStream inputStream = new DataInputStream(fileStream);			
			BufferedReader bufferdReader = new BufferedReader(new InputStreamReader(inputStream));
			String line;
			sentences.clear();

			while ((line = bufferdReader.readLine()) != null)
			{
				sentences.add(line);
			}
			inputStream.close();
		}catch (Exception e){
			System.err.print("Could not read file specified:" +name);
			System.exit(1);
		}
		return sentences;
	}


	public static String getCurrentExecutionPath()
	{
		return System.getProperty("user.dir");
	}
	
	public static String returnFirstNWords(String t, int n)
	{
		if(t == null)
			return null;

		String words [] = t.split(space);

		ArrayList<String> list = new ArrayList<String>();
		for(int i=0;i<words.length;i++)
		{	
			if(!words[i].equalsIgnoreCase(""))
				list.add(words[i]);
		}

		int size = list.size();

		if(size < n)
			return null;
		else
		{
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<n;i++)
				sb.append(list.get(i)).append(space);
			return sb.toString().trim();
		}
	}



	public static void writeFile(String name, ArrayList<String> data)
	{
		try{
			// Create file 
			name = getCurrentExecutionPath() +"/"+ name;
			System.out.println("Name to print:"+name);
			FileWriter fstream = new FileWriter(name);
			BufferedWriter out = new BufferedWriter(fstream);
			int size = data.size();
			for(int i=0;i<size;i++)
				out.write(data.get(i)+"\n");

			//Close the output stream
			out.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static String[] splitLine(String line)
	{
		String[] tokens = line.split(":");
		return tokens;
	}
	
}

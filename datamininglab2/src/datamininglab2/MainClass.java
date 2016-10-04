package datamininglab2;

import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
	private static String printLabelList(List<String> topic)
	{
		String result="";
		for(int i=0;i<topic.size();i++)
		{
			result+=topic.get(i)+",";
		}
		return result;
	}
	private static void printResult(List<ReuterDoc> reuterList) throws IOException
	{
		FileWriter predictWriter=new FileWriter("./result/TopicPredict.html");
		String begin="<html><head></head><body><table border=\"1\">\n";
		String end="</table></body></html>\n";
		String firstColumnFormat="<tr><td>%s</td>\n";
		String format="<td>%s</td>\n";
		String lastColumnFormat="<td>%s</td></tr>\n";
		predictWriter.write(begin);
		
		
				predictWriter.write(String.format(firstColumnFormat,"tite/words"));
				predictWriter.write(String.format(format, "actual topic"));
				predictWriter.write(String.format(lastColumnFormat,"predict topic"));
	
		
		for(int i=0;i<reuterList.size();i++)
		{
			predictWriter.write(String.format(firstColumnFormat,reuterList.get(i).title));
			predictWriter.write(String.format(format, printLabelList(reuterList.get(i).topics)));
			predictWriter.write("</tr>\n");
		}
		predictWriter.write(end);
		predictWriter.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		for(int i=0;i<22;i++){
			String fileName="./data/reut2-"+UtilClass.GetThreeDigitsNumber(i)+".sgm";
			List<ReuterDoc> reuters=FileParser.parse(fileName);
			reuterList.addAll(reuters);
		}
		WordCounter word=new WordCounter(reuterList);
		
		printResult(reuterList);	
	}
}

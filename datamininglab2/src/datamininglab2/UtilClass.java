package datamininglab2;

import java.util.ArrayList;
import java.util.List;

public class UtilClass {
	public static List<String> predictClassLabel(double[] distances,List<String> topicSet)
	{
			List<String> topic=new ArrayList<String>();
			for(int j=0;j<distances.length;j++){
				if(distances[j]>0.3)
				{
					topic.add(topicSet.get(j));
				}
			}
			return topic;
		
	}
	public static String GetThreeDigitsNumber(int number)
	{
		String result="000";
		if(number<10&&number>=0)
		{
			result="00"+String.valueOf(number);
		}
		else if(number>=10&&number<100)
		{
			result="0"+String.valueOf(number);
		}
		else
		{
			result=String.valueOf(number);
		}
		return result;
	}

}

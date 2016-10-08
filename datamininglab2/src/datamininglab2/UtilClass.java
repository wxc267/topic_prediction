package datamininglab2;

import java.util.ArrayList;
import java.util.List;

public class UtilClass {
	public static List<String> predictClassLabel(double[] distances,List<String> topicSet)
	{
			List<String> topic=new ArrayList<String>();
			double max=0;
			int index=0;
			for(int i=0;i<distances.length;i++){
				if(distances[i]>max)
				{
					max=distances[i];
					index=i;
				}
			}
			topic.add(topicSet.get(index));
			for(int i=0;i<distances.length;i++)
			{
				if(max/distances[i]<=1.5&&i!=index)
				{
					topic.add(topicSet.get(i));
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

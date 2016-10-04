package datamininglab2;


import java.util.List;

public class TopicCollection {
	
	public static void GetTopicLists(List<ReuterDoc> reuterList,List<String> actualTopics,List<String> topicSet)
	{
		for(int i=0;i<reuterList.size();i++)
		{
			String topic=reuterList.get(i).topics.toString();
			actualTopics.add(topic);
			if(!topicSet.contains(topic))
			{
				topicSet.add(topic);
			}
		}
	}
}

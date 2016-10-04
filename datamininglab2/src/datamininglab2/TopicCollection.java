package datamininglab2;


import java.util.List;

public class TopicCollection {
	
	public static void GetTopicLists(List<ReuterDoc> reuterList,List<List<String>> actualTopics,List<String> topicSet)
	{
		for(int i=0;i<reuterList.size();i++)
		{
			List<String> topics= reuterList.get(i).topics;
			actualTopics.add(topics);
			for(int j=0;j<topics.size();j++)
			{
				String topic=topics.get(j);
				if(!topicSet.contains(topic))
				{
					topicSet.add(topic);
				}
			}
		}
	}
}

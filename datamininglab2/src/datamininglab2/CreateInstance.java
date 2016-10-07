package datamininglab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class CreateInstance {
	/**
	 * transfer the feature vectors to the data set
	 * 
	 * @param tfidf
	 * the tfidf values for each article and words
	 * @param wordList
	 * the word with tfidf value
	 * @param actualTopic
	 * the topic of this document
	 * @param topicSet
	 * the total topic set for this article
	 * @return
	 */
	public static Instances GetDataset(List<Map<String,Double>> tfidf, List<String> wordList,List<List<String>> actualTopic,List<String> topicSet,int split)
	{
		//add the attributes to the weka
		Attribute topic_attr=new Attribute("topic_attr",topicSet);
		ArrayList<Attribute> attrs=new ArrayList<Attribute>();
		for(int i=0;i<wordList.size();i++)
		{
			attrs.add(new Attribute(wordList.get(i)));
		}
		attrs.add(topic_attr);//add class label
		//establish a new data set.
		Instances dataSet=new Instances("dataSet",attrs,tfidf.size());
		
		dataSet.setClassIndex(dataSet.numAttributes()-1);
		//add data instances into the dataset
		for(int i=0;i<split;i++){
			Instance instance=new DenseInstance(wordList.size()+1);
			List<String> topics=actualTopic.get(i);
			Map<String,Double> values=tfidf.get(i);
			
			for(int j=0;j<wordList.size();j++)
			{
				//set the attribute value
				instance.setDataset(dataSet);
				String word=wordList.get(j);
				Attribute word_attr=instance.attribute(j);
				if(values.containsKey(word))
				{
					
					instance.setValue(word_attr, values.get(word));
				}
				else
				{
					instance.setValue(word_attr,0);
				}
			}
			
				for(int k=0;k<topics.size();k++)
				{
					Instance copyInstance=new DenseInstance(instance);
					copyInstance.setValue(topic_attr,topics.get(k));
					dataSet.add(copyInstance);
				}
			
			
		}
		return dataSet;
	}


	public static Instance CreateTestData(Map<String,Double> tfidfMap, List<String> wordList,Instances dataSet)
	{
		Instance instance=new DenseInstance(wordList.size()+1);
		instance.setDataset(dataSet);
		for(int i=0;i<wordList.size();i++)
		{
			String word=wordList.get(i);
			Attribute word_attr=instance.attribute(i);
			if(tfidfMap.containsKey(word))
			{
				instance.setValue(word_attr, tfidfMap.get(word));
			}
			else
			{
				instance.setValue(word_attr,0);
			}
		}
		return instance;
	}
}

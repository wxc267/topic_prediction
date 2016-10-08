package datamininglab2;

import java.io.FileWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import weka.core.Instance;
import weka.core.Instances;

public class MainClass {
	private static int splitPercent=80;//the split percent for test set and train set
	private static String printLabelList(List<String> topic)
	{
		String result="";
		for(int i=0;i<topic.size();i++)
		{
			result+=topic.get(i)+",";
		}
		return result;
	}
	private static void printResult(FileWriter writer,List<ReuterDoc> reuterList,List<List<String>> result,int splitPoint) throws IOException
	{
		
		String begin="<html><head></head><body><h1>Test Data</h1><table border=\"1\">\n";
		String end="</table></body></html>\n";
		String firstColumnFormat="<tr><td>%s</td>\n";
		String format="<td>%s</td>\n";
		String lastColumnFormat="<td>%s</td></tr>\n";
		writer.write(begin);
		
		
				writer.write(String.format(firstColumnFormat,"tite/words"));
				writer.write(String.format(format, "actual topic"));
				writer.write(String.format(lastColumnFormat,"predict topic"));
	
		
		for(int i=splitPoint+1;i<reuterList.size();i++)
		{
			writer.write(String.format(firstColumnFormat,reuterList.get(i).title));
			writer.write(String.format(format, printLabelList(reuterList.get(i).topics)));
			writer.write(String.format(lastColumnFormat, printLabelList(result.get(i-splitPoint-1))));
			writer.write("</tr>\n");
		}
		writer.write(end);
		writer.close();
	}
	private static List<List<String>> ClassifyKNN(Instances train,List<Map<String,Double>> tfidf,List<String> wordList,List<String> topicSet,int split) throws Exception
	{
		long start=System.currentTimeMillis();
		List<List<String>> result=new ArrayList<List<String>> ();
		KNN knn=new KNN();
		knn.BuildClassifier(train);
		for(int i=split+1;i<tfidf.size();i++)
		{
			Instance test=CreateInstance.CreateTestData(tfidf.get(i), wordList, train);
			double[] distance= knn.ClassifyInstance(test);
			List<String> topic=UtilClass.predictClassLabel(distance, topicSet);
			result.add(topic);
		}
		long end=System.currentTimeMillis();
		double timeSpan=(end-start)/1000.0;
		System.out.println("Time for classify the label for test data for KNN(Online Cost):"
				+timeSpan+"s.");
		return result;
	}
	private static List<List<String>> ClassifyTree(Instances train,List<Map<String,Double>> tfidf,List<String> wordList,List<String> topicSet,int split) throws Exception
	{
		long start=System.currentTimeMillis();
		List<List<String>> result=new ArrayList<List<String>> ();
		DecisionTree tree=new DecisionTree();
		tree.BuildClassifier(train);
		for(int i=split+1;i<tfidf.size();i++)
		{
			Instance test=CreateInstance.CreateTestData(tfidf.get(i), wordList, train);
			double[] distance= tree.ClassifyInstance(test);
			List<String> topic=UtilClass.predictClassLabel(distance, topicSet);
			result.add(topic);
		}
		long end=System.currentTimeMillis();
		double timeSpan=(end-start)/1000.0;
		System.out.println("Time for classify the label for test data for decision tree(Online Cost):"
		+timeSpan+"s.");
		return result;
	}
	
	private static double getAccuracy(List<List<String>> actualTopic,List<List<String>> predictTopic,int split)
	{
		double result=0.0;
		for(int i=split+1;i<actualTopic.size();i++)
		{
			double accuracy=0.0;
			Set<String> actualSet=new HashSet<String>(actualTopic.get(i));
			Set<String> predictSet=new HashSet<String>(predictTopic.get(i-split-1));
			int total,hit;
			if(actualSet.size()>=predictSet.size())
			{
				 total=actualSet.size();
				actualSet.removeAll(predictSet);
				hit=total-actualSet.size();				
			}
			else
			{
				total=predictSet.size();
				predictSet.removeAll(actualSet);
				 hit=total-predictSet.size();	
			}
			accuracy=hit*1.0/total;
			result+=accuracy;
		}
		return result/predictTopic.size();
	}
	public static void main(String[] args) throws Exception
	{
		System.out.println("Split percent:"+splitPercent+"/"+(100-splitPercent));
		//parse the data file
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		for(int i=0;i<22;i++){
			String fileName="./data/reut2-"+UtilClass.GetThreeDigitsNumber(i)+".sgm";
			List<ReuterDoc> reuters=FileParser.parse(fileName);
			reuterList.addAll(reuters);
		}
		//analyze the parsed data file, receive the tfidf value and construct the instances
		List<List<String>> actualTopics=new ArrayList<List<String>>();
		List<String> topicSet=new ArrayList<String>();
		TopicCollection.GetTopicLists(reuterList, actualTopics, topicSet);
		WordCounter word=new WordCounter(reuterList);
		List<Map<String,Double>> tfidf=word.getTFIDFValue();
		List<String> wordList=word.getTopWordsList();
		int size=tfidf.size();
		int splitPoint=size*splitPercent/100;
		Instances dataset=CreateInstance.GetDataset(tfidf, wordList, actualTopics, topicSet,splitPoint);
		//split the dataset to train set and test set
		List<List<String>> knnResult=ClassifyKNN(dataset,tfidf,wordList,topicSet,splitPoint);
		FileWriter knnWriter=new FileWriter("./result/Result(KNN).html");
		printResult(knnWriter,reuterList,knnResult,splitPoint);
		double knnAccuracy=getAccuracy(actualTopics,knnResult,splitPoint);
		System.out.println("Accuracy of KNN method:"+knnAccuracy*100.0+"%");
		List<List<String>> treeResult=ClassifyTree(dataset,tfidf,wordList,topicSet,splitPoint);
		FileWriter treeWriter=new FileWriter("./result/Result(DecisionTree).html");
		printResult(treeWriter,reuterList,treeResult,splitPoint);
		double	treeAccuracy=getAccuracy(actualTopics,treeResult,splitPoint);
		System.out.println("Accuracy of Decision Tree method:"+treeAccuracy*100.0+"%");
	}
}

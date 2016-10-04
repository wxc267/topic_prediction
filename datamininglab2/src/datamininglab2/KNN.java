package datamininglab2;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

public class KNN {
		Classifier ibk;
		public KNN()
		{
			ibk=new IBk();
		}
		
		public void BuildClassifier(Instances trainData) throws Exception
		{
			ibk.buildClassifier(trainData);
		}
		public List<double[]> getClassDistances(Instances testData) throws Exception
		{
			List<double[]> result=new ArrayList<double[]>();
			for(int i=0;i<testData.size();i++)
			{
				Instance test=testData.get(i);
				double[] value=ibk.distributionForInstance(test);
				result.add(value);
			}
			return result;
		}
		public String predictClassLabel(double[] possibility,List<String> topicSet)
		{
			//TODO
			return null;
		}
}

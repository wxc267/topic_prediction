package datamininglab2;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.lazy.IBk;
import weka.core.Instance;
import weka.core.Instances;

public class KNN {
		Classifier ibk;
		Evaluation eval;
		public KNN()
		{
			ibk=new IBk();
		}
		
		public void BuildClassifier(Instances trainData) throws Exception
		{
			ibk.buildClassifier(trainData);
			eval=new Evaluation(trainData);
		}
		public List<double[]> getClassDistances(Instances testData) throws Exception
		{
			List<double[]> result=new ArrayList<double[]>();
			eval.evaluateModel(ibk, testData);
			for(int i=0;i<testData.size();i++)
			{
				Instance test=testData.get(i);
				double[] value=ibk.distributionForInstance(test);
				result.add(value);
			}
			return result;
		}
		public List<String> predictClassLabel(double[] possibility,List<String> topicSet)
		{
			List<String> result=new ArrayList<String>();
			for(int i=0;i<possibility.length;i++)
			{
				if(possibility[i]>0.9)
				{
					result.add(topicSet.get(i));
				}
			}
			return result;
			
		}
		public double[][] getConfusionMatrix()
		{
			return eval.confusionMatrix();
		}
}

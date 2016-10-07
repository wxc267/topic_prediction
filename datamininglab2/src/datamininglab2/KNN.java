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
		public KNN()
		{
			ibk=new IBk(5);//k is 5.
		}
		
		public void BuildClassifier(Instances trainData) throws Exception
		{
			long start=System.currentTimeMillis();
			ibk.buildClassifier(trainData);
			long end=System.currentTimeMillis();
			double timeSpan=(end-start)/1000.0;
			System.out.println("Time for construct classifier for KNN(Offline cost):" +timeSpan+" s.");
		}
		public double[] ClassifyInstance(Instance testData) throws Exception
		{
				return ibk.distributionForInstance(testData);
		}


}

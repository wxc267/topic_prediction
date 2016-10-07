package datamininglab2;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

public class DecisionTree {
	J48 decision_tree;
	public DecisionTree() 
	{
		decision_tree=new J48();
		
	}
	public void BuildClassifier(Instances trainData) throws Exception
	{
		//set the options for decision trees as unpruned.
		long start=System.currentTimeMillis();
		String[] options=new String[1];
		options[0]="-U";
		decision_tree.setOptions(options);
		decision_tree.buildClassifier(trainData);
		long end=System.currentTimeMillis();
		double timeSpan=(end-start)/1000.0;
		System.out.println("Time for construct classifier for decision tree(Offline cost):" +timeSpan+" s.");
	}
	public double[] ClassifyInstance(Instance testData) throws Exception
	{
			return decision_tree.distributionForInstance(testData);
	}

}

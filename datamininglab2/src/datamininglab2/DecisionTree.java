package datamininglab2;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

public class DecisionTree {
	J48 decision_tree;
	public DecisionTree() 
	{
		decision_tree=new J48();
		
	}
	public void buildClassifier(Instances trainData) throws Exception
	{
		//set the options for decision trees as unpruned.
				String[] options=new String[1];
				options[0]="-U";
				decision_tree.setOptions(options);
				decision_tree.buildClassifier(trainData);
	}
	public List<double[]> getClassDistances(Instances testData) throws Exception
	{
		List<double[]> result=new ArrayList<double[]>();
		for(int i=0;i<testData.size();i++)
		{
			Instance test=testData.get(i);
			double[] value=decision_tree.distributionForInstance(test);
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

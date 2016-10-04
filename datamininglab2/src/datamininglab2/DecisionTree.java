package datamininglab2;

import java.util.ArrayList;
import java.util.List;

import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instance;
import weka.core.Instances;

public class DecisionTree {
	J48 decision_tree;
	Evaluation eval;
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
				eval=new Evaluation(trainData);
	}
	public List<double[]> getClassDistances(Instances testData) throws Exception
	{
		List<double[]> result=new ArrayList<double[]>();
		eval.evaluateModel(decision_tree, testData);
		for(int i=0;i<testData.size();i++)
		{
			Instance test=testData.get(i);
			double[] value=decision_tree.distributionForInstance(test);
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

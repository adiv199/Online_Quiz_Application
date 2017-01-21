package model;

import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;


public class ChartBean {
	
	public JFreeChart createPieChart(ArrayList<Integer> numStudents) {
		// create a dataset...
		int i;
		DefaultPieDataset data = new DefaultPieDataset();
		for( i = 0; i < numStudents.size(); i++) {
			if(i==0)
				data.setValue("A", numStudents.get(i));
			if(i==1)
				data.setValue("B", numStudents.get(i));
			if(i==2)
				data.setValue("C", numStudents.get(i));
			if(i==3)
				data.setValue("D", numStudents.get(i));
			if(i==4)
				data.setValue("E", numStudents.get(i));
		}
		
		
		JFreeChart chart = ChartFactory.createPieChart(
		"Pie Chart", data, true, true, false
		);
		return chart;
		}
	
		public JFreeChart createHistChart(double[] numStudents) {
		// create a dataset...
		
		HistogramDataset data = new HistogramDataset();
		data.setType(HistogramType.FREQUENCY);
		//data.addSeries("Histogram", numStudents,1);
		data.addSeries("Histogram", numStudents, 10, 0, 20);	
		JFreeChart chart = ChartFactory.createHistogram(
		"Histogram", "Marks range","Students no.",data, PlotOrientation.VERTICAL, false, true, false );
		
		
		return chart;
		}
	
	    public JFreeChart createBarChart(ArrayList<Integer> scores, ArrayList<String> assessNames) {
		
	    // create a dataset...
		int i;
		DefaultCategoryDataset data = new DefaultCategoryDataset();
		
		for( i = 0; i < scores.size(); i++) {
				
				data.addValue(scores.get(i),assessNames.get(i), "Student");
		}
		
		JFreeChart chart = ChartFactory.createBarChart(
				"Bar Chart",
				"Assessments",
				"Scores",
				data,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);
				return chart;
	    }

}

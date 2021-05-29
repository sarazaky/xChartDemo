package workOnTitanic.workOnTitanic;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



public class xChartOp {
	List<TitanicPassenger> passengerList;
	
	public xChartOp() {
		passengerList = new ArrayList<TitanicPassenger> ();
	}
	
	public List<TitanicPassenger> getPassengersFromJsonFile(String filePath) {
		
    	ObjectMapper objectMapper = new ObjectMapper ();
    	objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	try {
    		InputStream input = new FileInputStream (filePath);
    		//Read JSON file
			passengerList = objectMapper.readValue (input, new TypeReference<List<TitanicPassenger>> () { });
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return passengerList;
	}
	
	public void graphPassengerAges() {
		//filter to get an array of passenger ages
		List<Float> pAges = passengerList.stream ().map (TitanicPassenger::getAge).limit (8).collect (Collectors.toList ());
		List<String> pNames = passengerList.stream ().map (TitanicPassenger::getName).limit (8).collect (Collectors.toList ());
		
		//Using XCart to graph the Ages 1.Create Chart
		CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Age Histogram").xAxisTitle ("Names").yAxisTitle
		("Age").build ();
		// 2.Customize Chart
		chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
		chart.getStyler ().setHasAnnotations (true);
		chart.getStyler ().setStacked (true);
		// 3.Series
		chart.addSeries ("Passenger's Ages", pNames, pAges);
		// 4.Show it
		new SwingWrapper (chart).displayChart ();
	}
	
	public void graphPassengerClass() {
		//filter to get a map of passenger class and total number of passengers in each class
		Map<String, Long> result =
		passengerList.stream ().collect (
		Collectors.groupingBy (TitanicPassenger::getPclass, Collectors.counting () ) );
		
		// Create Chart
		PieChart chart = new PieChartBuilder ().width (800).height (600).title (getClass ().getSimpleName ()).build ();
		// Customize Chart
		Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
		chart.getStyler ().setSeriesColors (sliceColors);
		// Series
		chart.addSeries ("First Class", result.get ("1"));
		chart.addSeries ("Second Class", result.get ("2"));
		chart.addSeries ("Third Class", result.get ("3"));
		// Show it
		new SwingWrapper (chart).displayChart ();
	}
	
	public void graphPassengersurvived() {
		//filter to get a map of survived and not survived passengers 
		Map<String, Long> result =
		passengerList.stream ().collect (
		Collectors.groupingBy (TitanicPassenger::getSurvived, Collectors.counting () ) );
		
		// Create Chart
		PieChart chart = new PieChartBuilder ().width (800).height (600).title ("Passenger survived").build ();
		// Customize Chart
		Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120)};
		chart.getStyler ().setSeriesColors (sliceColors);
		// Series
		chart.addSeries ("not Survived", result.get ("0"));
		chart.addSeries ("Survived", result.get ("1"));
		// Show it
		new SwingWrapper (chart).displayChart ();
	}
	
	public void graphPassengersurvivedGender() {
		//filter to get a map of survived passengers based on gender
		Map<String, Long> result =
		passengerList.stream ().filter(p -> p.getSurvived().equals("1") ).collect (
		Collectors.groupingBy (TitanicPassenger::getSex, Collectors.counting () ) );
		
		// Create Chart
		PieChart chart = new PieChartBuilder ().width (800).height (600).title ("Passenger survived Gender").build ();
		// Customize Chart
		Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120)};
		chart.getStyler ().setSeriesColors (sliceColors);
		// Series
		chart.addSeries ("Female Survived", result.get ("female"));
		chart.addSeries ("Male Survived", result.get ("male"));
		// Show it
		new SwingWrapper (chart).displayChart ();
	}

}

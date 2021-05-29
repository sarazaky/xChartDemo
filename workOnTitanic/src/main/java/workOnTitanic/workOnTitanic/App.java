package workOnTitanic.workOnTitanic;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	  	
        xChartOp ch = new xChartOp();
        List<TitanicPassenger> pasLst = ch.getPassengersFromJsonFile("src/sources/titanic_csv.json");
//        pasLst.stream().forEach(p -> System.out.println( p ));
        ch.graphPassengersurvived();
        ch.graphPassengersurvivedGender();
             
    }
    
}


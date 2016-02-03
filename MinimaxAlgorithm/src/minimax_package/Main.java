package minimax_package;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
	
	static final int KNOWN_ATTRIBUTES = 100; /* 100
	 * % of attributes known for all
	 * producers
	 */
	static final double SPECIAL_ATTRIBUTES = 33; /* 33
		 * % of special attributes known
		 * for some producers
		 */
	static final int MUT_PROB_CUSTOMER_PROFILE = 33; /*  * % of mutated
				 * attributes in a
				 * customer profile
				 */

	static final int RESP_PER_GROUP = 20; /* * We divide the respondents of each
	 * profile in groups of
	 * RESP_PER_GROUP respondents
	 */
	static final int NEAR_CUST_PROFS = 4; /*Number of near customer profiles to generate a product*/
	static final int NUM_EXECUTIONS = 10; /* number of executions */
	static final int NUM_TURNS = 20;
	
	// static final String SOURCE = "D:\Pablo\EncuestasCIS.xlsx";
	static final int SHEET_AGE_STUDIES = 1;
	static final int SHEET_POLITICAL_PARTIES = 2;
	static final String EOF = "EOF";
	
	private static int MAX_DEPTH_0 = 4; /*Maximum depth of the minimax*/
	private static int MAX_DEPTH_1 = 2; /*Maximum depth of the minimax*/
	private static int NUM_BRANCHES = 50; /*Number of branches deployed in each step of the minimax*/

	/*INPUT VARIABLES*/
	private int mNAttrMod; /*Number of attributes the producer can modify (D)*/
	private int mPrevTurns; /*Number of previous turns to compute (tp)*/
	private int mNTurns; /*Number of turns to play (tf)*/
	
	private static int Number_Attributes; /* Number of attributes */
	private static int Number_Producers; /* Number of producers */
	private static int Number_Customer; /* Number of customers*/
	private static int Number_CustomerProfile; /* Number of customer profiles */

	private static ArrayList<Attribute> TotalAttributes = new ArrayList<>();
	private static ArrayList<Double> StdDevProd;
	private static double SumOfDev;
	private static ArrayList<Producer> Producers;


    private static LinkedList<CustomerProfile> CustomerProfileList = new LinkedList<>();
    private static LinkedList<CustomerProfile> CustomerProfileListAux = new LinkedList<>();
    private static LinkedList<Integer> NumberCustomerProfile = new LinkedList<>(); /*Number of customers of each customer profile*/
    
    private static LinkedList<CustomerProfile> CustGathered = new LinkedList<>(); /*Customers gathered by each producer during the previous mPrevTurns turns*/
    private static LinkedList<Integer> NumberCustGathered = new LinkedList<>(); /*Number of customers gathered by each producer during the previous mPrevTurns turns*/

    /* STATISTICAL VARIABLES */
	private LinkedList<Integer> Results;
	
	public static void main(String[] args) throws IOException {
		// An excel file name. You can create a file name with a full path
				// information.
				String filename = "EncuestasCIS.xlsx";
				// Create an ArrayList to store the data read from excel sheet.
				List sheetData = new ArrayList();
				FileInputStream fis = null;
				try {
					// Create a FileInputStream that will be use to read the excel file.
					fis = new FileInputStream(filename);

					// Create an excel workbook from the file system.
					XSSFWorkbook workbook = new XSSFWorkbook(fis);

					// Get the first sheet on the workbook.
					XSSFSheet sheet = workbook.getSheetAt(0);

					/*
					 * When we have a sheet object in hand we can iterator on each
					 * sheet's rows and on each row's cells. We store the data read on
					 * an ArrayList so that we can printed the content of the excel to
					 * the console.
					 */
					Iterator rows = sheet.rowIterator();
					while (rows.hasNext()) {
						XSSFRow row = (XSSFRow) rows.next();
						Iterator cells = row.cellIterator();
						List data = new ArrayList();
						while (cells.hasNext()) {
							XSSFCell cell = (XSSFCell) cells.next();
							// System.out.println("A�adiendo Celda: " +
							// cell.toString());
							data.add(cell);
						}
						sheetData.add(data);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fis != null) {
						fis.close();
					}
				}

	}
	
	/**Generating the input data
	 * @throws Exception */
	private void generateInput() throws Exception{
		mNAttrMod = 1;
		mNTurns = NUM_TURNS;
		mPrevTurns = mNTurns; //They can be different
		
		/*' In this case study the number of attributes mNAttr 
        ' of the product is the number of questions of the poll
        ' The number of producers mNProd is the number of political parties:
        ' MyPP, PP, PSOE, IU, UPyD, CiU*/
		Number_Attributes = 0;
		Number_Producers = 2; //In this moment the game only works with 2 players/producers. Adding new players would need to change alphabeta() function.
	
		//genAttrVal();
		//genCustomerProfiles();
       // genCustomerProfilesNum();
        divideCustomerProfile();
       // genProducers();
	}
	
	/**Generating statistics about the PD problem*/
	private void statisticsPD() throws Exception{
		double mean;
		double sum = 0; /*sum of customers achieved*/
		double initSum = 0; /*sum of initial customers*/
		int sumCust = 0; /*sum of the total number of customers*/
		double custMean;
		double variance;
		double stdDev;
		double percCust; /*% of customers achieved*/
		double initPercCust; /*% of initial customers achieved*/
		String msg;
		
		Results = new LinkedList<Integer>();
		
		//generateInput();
		initialVoters();
		
		for(int i = 0; i < NUM_EXECUTIONS - 1; i++)
		{
			playGame();
			sum += Results.get(i);
			sumCust += (Number_Customer * mPrevTurns * Number_Producers);
			
		}
		
		mean = sum / NUM_EXECUTIONS;
		variance = computeVariance(mean);
		stdDev = Math.sqrt(variance);
		custMean = sumCust / NUM_EXECUTIONS;
		percCust = 100 * mean / custMean;
		
		/*MOSTRARLO*/
	}
	
	/**Computing the voters of each political party at the beginning
	 * @throws Exception */
	private void initialVoters() throws Exception{
		double perCust; // % of customers achieved
		String msg;
		int wsc0 = 0;
		int wsc;
		for(int prodInd = 0; prodInd < Number_Producers - 1; prodInd++)
		{
			wsc = computeWSC(listOfProducts(), prodInd); 
			if(prodInd == 0) wsc0 = wsc;
		}
		perCust = 100 * wsc0 / Number_Customer;
	}
	
	private Product listOfProducts() {
		// TODO Auto-generated method stub
		return null;
	}

	/**Playing the PDG*/
	private void playGame(){
		Math.random();
		for(int i = 1; i < mNTurns; i++)
		{
			for(int prodInd = 0; prodInd < Number_Producers - 1; prodInd++)
			{
				//changeProduct(prodInd);
				//updateCustGathered(i);
			}
		}
		
		Results.add(NumberCustGathered.get(0)); //We store the number of customers gathered by producer 0
	}


	/*************************************** " AUXILIARY METHODS GENERATEINPUT()" ***************************************/
	
	/**Dividing the customer profiles into sub-profiles
	 * @throws Exception */
	private static void divideCustomerProfile() throws Exception{
		int numOfSubProfile;
		CustomerProfileListAux = new LinkedList<CustomerProfile>();
		for(int i = 0; i < CustomerProfileList.size(); i++)
		{
			CustomerProfileListAux.add(new CustomerProfile(new ArrayList<Attribute>()));
			numOfSubProfile = CustomerProfileList.get(i).getScoreAttributes().size() / RESP_PER_GROUP;
			if((CustomerProfileList.get(i).getScoreAttributes().size() % RESP_PER_GROUP) != 0)
			{
				numOfSubProfile++;
			}	
			for(int j = 0; j < numOfSubProfile - 1; j++) //We divide into sub-profiles
			{
				CustomerProfileListAux.get(i).getScoreAttributes().add(TotalAttributes.get(j));
				for(int k = 0; k < Number_Attributes - 1; k++) //Each of the sub-profiles choose a value for each of the attributes
				{
					CustomerProfileListAux.get(i).getScoreAttributes().get(j).getScoreValues().add(chooseValueForAttribute(i, k));
					
				}
			}
		}
	}
	
	/**Given an index of a customer profile and the index of an attribute we choose a value
    for that attribute of the sub-profile having into account the values of the poll*/
	private static Integer chooseValueForAttribute(int custProfInd, int attrInd) throws Exception {
		int value = 0;
		double total = 0;
		double rndVal;
		boolean found = false;
		double accumulated = 0;
		
		for (int i = 0; i < CustomerProfileList.get(custProfInd).getScoreAttributes().get(attrInd).getScoreValues().size() - 1; i++)
		{
			total += CustomerProfileList.get(custProfInd).getScoreAttributes().get(attrInd).getScoreValues().get(i);
		}
		rndVal = total * Math.random();
		while(!found)
		{
			accumulated += CustomerProfileList.get(custProfInd).getScoreAttributes().get(attrInd).getScoreValues().get(value);
			if(rndVal <= accumulated) found = true;
			else value++;
		
		
			if (value >=  CustomerProfileList.get(custProfInd).getScoreAttributes().size())
				throw new Exception("Error 1 in chooseValueForAttribute() method: Value not found");
		}
		
		if(!found) throw new Exception("Error 2 in chooseValueForAttribute() method: Value not found");
		return value; //The attribute value chosen is equal to its index
	}
	
	/**Creating available attributes for the producer*/
	private static ArrayList<Attribute> createAvailableAttributes()
	{
		ArrayList<Attribute> availableAttributes = new ArrayList<>();
		int limit = Number_Attributes * KNOWN_ATTRIBUTES / 100;
		
		/*All producers know the first ATTRIBUTES_KNOWN % of the attributes*/
		for(int i = 0; i < limit - 1; i++){
			Attribute attr = new Attribute(TotalAttributes.get(i).getName(), TotalAttributes.get(i).getMIN(), TotalAttributes.get(i).getMAX());
			ArrayList<Boolean> values = new ArrayList<>();
			for(int j = 0; j < attr.getMAX(); j++){
				values.add(true);
			}

			attr.setAvailableValues(values);
			availableAttributes.add(attr);
		}
		
		/*The remaining attributes are only known by SPECIAL_ATTRIBUTES % producers*/
		for(int k = limit; k < TotalAttributes.size() - 1; k++){
			Attribute attr = new Attribute(TotalAttributes.get(k).getName(), TotalAttributes.get(k).getMIN(), TotalAttributes.get(k).getMAX());
			ArrayList<Boolean> values = new ArrayList<>();
			
			for(int j = 0; j < attr.getMAX(); j++){
				double rnd = Math.random();
				double rndVal = Math.random();
				/*Furthermore, with a 50% of probabilities it can know this attribute*/
				if(rndVal < (SPECIAL_ATTRIBUTES / 100) && rnd < 0.5)
					values.add(true);
				else
					values.add(false);
			}
			attr.setAvailableValues(values);
			availableAttributes.add(attr);
		}
		
		return availableAttributes;
	}
	
	/**Computing the standard deviation for each attribute given the products of the producers*/
	private void computeStdDevProd(){
		int total;
		double mean;
		double sqrSum;
		double variance;
		double stdDev;
		
		StdDevProd = new ArrayList<>();
		SumOfDev = 0;
		
		for(int i = 0; i < Number_Attributes - 1; i++)
		{
			total = 0;
			for(int j = 0; j < Number_Producers - 1; j++)
			{
				total += Producers.get(j).product.getValuesPopuProduct().get(i);
			}
			mean = total / Number_Producers;
			
			sqrSum = 0;
			for(int k = 0; k < Number_Producers - 1; k++)
			{
				sqrSum += Math.pow(Producers.get(k).product.getValuesPopuProduct().get(i) - mean, 2);
			}
			variance = sqrSum / Number_Producers;
			stdDev = Math.sqrt(variance);
			//We increase the deviation in order to choose those with 
            // higher deviations with greater probability
			if(stdDev > 0) stdDev = Math.pow(stdDev + 1, 2);
			
			StdDevProd.add(stdDev);
			SumOfDev += stdDev;

		}
		
	}

	/*************************************** " AUXILIARY METHODS PLAYGAME()" ***************************************/
	
	/**Computing the score of a product given the customer profile index
    custProfInd and the product*/
	private int scoreProduct(int custProfInd, int custSubProfInd, Product product) throws Exception
	{
		int score = 0;
		for(int i = 0; i < Number_Attributes - 1; i++)
		{
			score += scoreAttribute(TotalAttributes.get(custProfInd).getScoreValues().get(i), CustomerProfileListAux.get(custProfInd).getScoreAttributes().get(custSubProfInd).getScoreValues().get(i), product.getAttributeValue().get(i));//////////
			 // score += scoreAttribute(mAttributes(i), mCustProfAux(custProfInd)(custSubProfInd)(i), product(i))
		}
		return score;
	}
	
	/**Computing the score of an attribute for a product given the
    ' number of values */
	private int scoreAttribute(int numOfValsOfAttr, int valOfAttrCust, int valOfAttrProd) throws Exception
	{
		int score = 0;
		switch(numOfValsOfAttr){
			case 2: {
				if(valOfAttrCust == valOfAttrProd) score = 10;
				else score = 0;
			}
			break;
			case 3: {
				if(valOfAttrCust == valOfAttrProd) score = 10;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 1) score = 5;
				else score = 0;
			} break;
			case 4: {
				if(valOfAttrCust == valOfAttrProd) score = 10;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 1) score = 6;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 2) score = 2;
				else score = 0;
			} break;
			case 5: {
				if(valOfAttrCust == valOfAttrProd) score = 10;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 1) score = 6;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 2) score = 2;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 3) score = 1;
				else score = 0;
			} break;
			case 11: {
				if(valOfAttrCust == valOfAttrProd) score = 10;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 1) score = 8;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 2) score = 6;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 3) score = 4;
				else if(Math.abs(valOfAttrCust - valOfAttrProd) == 4) score = 2;
				else score = 0;
			} break;
			default: throw new Exception("Error in scoreAttribute() function: " +
                    "Number of values of the attribute unexpected");
		}
		return score;
	}
	
	/***Computing the weighted score of the producer
    prodInd is the index of the producer
	 * @throws Exception **/
	private int computeWSC(Product product, int prodInd) throws Exception {
		int wsc = 0;
		boolean isTheFavourite;
		int meScore;
    	int score;
		int k;
		int numTies;
		for(int i = 0; i < Number_CustomerProfile - 1; i++)
		{
			for(int j = 0; j < CustomerProfileListAux.get(i).getScoreAttributes().size() - 1; j++)
			{
				isTheFavourite = true;
				numTies = 1;
				meScore = scoreProduct(i,j,product);
				k = 0;
				while(isTheFavourite && k < Number_Producers)
				{
					if(k != prodInd)
					{
						score = scoreProduct(i,j, Producers.get(k).product);
						if(score > meScore) isTheFavourite = false;
						else if(score == meScore) numTies += 1;
					}
					k++;
				}
				/*TODO: When there exists ties we loose some voters because of decimals (undecided voters)*/
				if(isTheFavourite)
				{
					if((j == (CustomerProfileListAux.get(i).getScoreAttributes().size() - 1)) && ((NumberCustomerProfile.get(i) % RESP_PER_GROUP) != 0))
					{
						wsc += (NumberCustomerProfile.get(i) % RESP_PER_GROUP) / numTies;
					}
					else{
						wsc += RESP_PER_GROUP / numTies;
					}
				}
					
			}
		}

		return wsc;
	}
	
	/*************************************** " AUXILIARY METHODS STATISTICSPD()" ***************************************/

	/**Computing the variance */
	private double computeVariance(double mean){
		double sqrSum = 0;
		for(int i = 0; i < NUM_EXECUTIONS; i++){
			sqrSum += Math.pow( Results.get(i) - mean, 2); 
		}
		return (sqrSum/NUM_EXECUTIONS);
	}

}
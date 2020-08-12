/**
 * Program:			D_M_ScoreboardMethods.java
 * Date:			Nov 14, 2019
 * Author:			D. Miike
 * Description:		This class consists in various helper methods to minimize code in the main() method of D_M_Scoreboard 
 */

public class D_M_ScoreboardMethods 
{
	/**
	* Method Name:		isValid
	* Purpose:			Validate data for data submission file
	* Accepts:			integer values
	* Returns:			boolean
	*/
	


	public static boolean isValid(int idTeams, int idProblem, int teamsNbr, int problemNbr) {
		if((idTeams > 0 && idTeams <=teamsNbr) &&
		   (idProblem > 0 && idProblem <= problemNbr))
		{ 
			 return true;
		 }
		 else
		 {
			 return false;
		 }
		
		
	}//end isValid
	
	/**
	* Method Name:		getNumSolved
	* Purpose:			Get the total number of problems solved per team
	* Accepts:			an integer Array and a integer value
	* Returns:			void
	*/
	

	public static void getNumSolved (int [] a, int i )
	{
		a[i-1] += 1;
		
	}//end getNumSolved

	
	/**
	* Method Name:		getTotTime
	* Purpose:			Get total time per team and problem solved
	* Accepts:			An integer array and two integer variables
	* Returns:			void
	*/
	

	public static void getTotTime (int [] a, int i, int j)
	{
		a[i-1] += j;
	}//end getTotTime
	
	/**
	* Method Name:		truncate
	* Purpose:			Print the first fourteen characters
	* Accepts:			An String value and an int variable length
	* Returns:			An String value
	*/
	
	
	public static String truncate(String value, int length)
	{
	  if (value != null && value.length() > length)
	  {
	    value = value.substring(0, length);
	  	  }
	  return value;
	}
	
	/**
	* Method Name:	getPenalty
	* Purpose:		Get the total number of minutes used to solve all question by team including the penalties of 20 minutes
	* 				for each repeated submission; if the team submit twice the same questions the total of minutes should be 
	* 				the total time used to solve the question at the first and the second time plus 20 min because of the second submission 
	* Accepts:		An string 2d array, an integer 2d array and a integer regular array
	* Returns:		void
	*/
	public static void getPenalty(String [][] a, int [][] b, int [] p)
	{
		for(int r = 0; r < a.length;r++)
		{
			for(int c = 0; c < a[r].length;c++)
			{
				if (a[r][c].equals("Y"))
				{
					int penaltyQ = b[r][c]-1;
					int penaltyT = (penaltyQ*20);
					p[r] += penaltyT;
				}
			}
		}
	}//end getPenalty
	
	/**
	* Method Name:		sortArray
	* Purpose:			Sort the array by the number of problems solved. Where there is a tie the time's total time will be
	* 					used as secondary sort key
	* Accepts:			Three regular int arrays and one 2D Array
	* Returns:			void
	*/
	//sort[rB][0] - SLV
	//sort[rB][1] - Time
	//sort[rB][2] - Slv array's index
	//iTS - line count
	
	public static void sortArray(int [] slv, int [] time, int [] pen, int [][] sort)
	{
		int iTS = 0;
		boolean reorg;
		
		for(int rB=0; rB < slv.length; rB++)
		{
			int timeSpend = time[rB] + pen[rB];
			if(rB == 0)
			{
				sort[rB][0] = slv[rB];
				sort[rB][1] = timeSpend;	
				sort[rB][2] = rB;
				iTS++;
			}
			else
			{
				reorg = false;
		
				for(int rS=0; rS < iTS; rS++)
				{
					if(slv[rB] > sort[rS][0])
					{
						reorgSortArray(iTS, rS, sort);
						sort[rS][0] = slv[rB];
						sort[rS][1] = timeSpend;	
						sort[rS][2] = rB;
						rS = iTS;
						reorg =  true;
					}
					else
					{
						if(slv[rB] == sort[rS][0] && timeSpend < sort[rS][1])
						{
							reorgSortArray(iTS, rS, sort);
							sort[rS][0] = slv[rB];
							sort[rS][1] = timeSpend;	
							sort[rS][2] = rB;
							rS = iTS;
							reorg =  true;
						}
					}
				}
				if(reorg == false)
				{
					sort[iTS][0] = slv[rB];
					sort[iTS][1] = timeSpend;	
					sort[iTS][2] = rB;
				}
				iTS++;
			}
		}
		
	}//end sortArray
	
	/**
	* Method Name:		reorgSortArray
	* Purpose:			Swap the array
	* Accepts:			Two int variables and one 2D array
	* Returns:			void
	*/
	//sort[rB][0] - SLV
	//sort[rB][1] - Time
	//sort[rB][2] - Slv array's index
	//iTSR - number of total line in the sort array
	//iSR - Line's index of the new value
	
	
	public static void reorgSortArray(int iTSR, int iSR, int [][] rsort)
	{
		for(int iReorg=iTSR-1; iReorg >= iSR; iReorg--)
		{
			rsort[iReorg+1][0] = rsort[iReorg][0];
			rsort[iReorg+1][1] = rsort[iReorg][1];
			rsort[iReorg+1][2] = rsort[iReorg][2];
		}
	}//end reorgSortArray
	
	/**
	* Method Name:		printReportSort
	* Purpose:			Print the final report
	* Accepts:			1 String regular array, 1 int regular array, 1 2D String array, 2 2D int array, 2 int variables
	* Returns:			void
	*/
	public static void printReportSort (String [] team,  int [] slv, String [][] solved,int [][] sub,
										int [][] sort, int valid, int invalid,String head )
	{
		System.out.println(head + "\n");
		System.out.printf("%-8s", "Rank");
		System.out.printf("%-20s","Team");
		String [] title = {"Slv/Time", "P1","P2","P3","P4","P5","P6","P7","P8","P9","P10","P11"};
		for(int i = 0; i < title.length; i++)
		{
			System.out.printf("%8s", title[i]);
		}
	
		System.out.println("\n");
	
		for(int r = 0; r < sort.length;r++)
		{
			System.out.printf("%-8d", r+1);
			System.out.printf("%-20s", team[sort[r][2]]);
			
			int totalTime = sort[r][1];
		
			String slvTime = slv[sort[r][2]]+"/"+totalTime;
			System.out.printf("%-8s", slvTime);
			
			for(int c = 0; c < sub[sort[r][2]].length;c++)
			{
				String qSubmission = solved[sort[r][2]][c] + "/" + sub[sort[r][2]][c];
				System.out.printf("%8s",qSubmission);
			}
			//20.3 Print a empty line to divide the report and the next message
			System.out.println();
		}
		System.out.println("\n" + valid + " valid submission(s) were processed.");
		System.out.println(invalid + " submission(s) were invalid and ignored.");

	}//end printReportSort
	
	/**
	* Method Name:		printReport
	* Purpose:			Print the final report unsorted
	* Accepts:			1 String regular array, 3 int regular array, 1 2D String array, 1 2D int array, 2 int variables and 1 String variable
	* Returns:			void
	*/
	public static void printReport (String [] team,  int [] slv, int[] time, int [] penalty, String [][] solved,
									int [][] sub, int valid, int invalid, String head)
	{
		System.out.println(head + "\n");
		System.out.printf("%-20s","Team");
		String [] title = {"Slv/Time", "P1","P2","P3","P4","P5","P6","P7","P8","P9","P10","P11"};
		for(int i = 0; i < title.length; i++)
		{
			System.out.printf("%8s", title[i]);
		}
	
		System.out.println("\n");
	
		
		for(int r = 0; r < sub.length;r++)
		{
			
			System.out.printf("%-20s", team[r]);
			
			int totalTime = time[r] + penalty[r];
			String slvTime = slv[r]+"/"+totalTime;
			
			System.out.printf("%8s", slvTime);
		
			for(int c = 0; c < sub[r].length;c++)
			{
				String qSubmission = solved[r][c] + "/" + sub[r][c];
				System.out.printf("%8s",qSubmission);
			}
			//20.3 Print a empty line to divide the report and the next message
			System.out.println();
		}
		//20.4 Print the message with the total of valid and invalid submissions
		System.out.println("\n" + valid + " valid submission(s) were processed.");
		System.out.println(invalid + " submission(s) were invalid and ignored.");

	}//end printReport
}


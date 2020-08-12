/**
 * Program:			D_M_Scoreboard.java
 * Date:			Nov 13, 2019
 * Author:			D. Miike
 * Description:		Read text files from log files them generates and displays a contest scoreboard including a title and a column
 * 					name. The output will also report the number of submissions.
 */


import java.io.*;
import java.util.*;


public class D_M_Scoreboard 
{

	//1. Declare variables to use in the program
	public static int			teamsNbr		= 0;
	public static int 			problemNbr		= 0;
	public static int 			totalMnt		= 0; 
	public static int 			addMnt			= 0;
	public static int 			lineCount		= 1;
	public static int 			totValidSub		= 0;
	public static int 			totInvalidSub	= 0;
	public static String 		head;
	public static int[] 		slvArray; 
	public static int[] 		timesArray; 
	public static int[][] 		submissionsArray;
	public static String[][] 	solvedArray;
	public static int[] 		penaltyArray;
	public static String[] 		teamsArray; 
	public static int[][]		sortArray;
	
	

	public static void main(String[] args) 
	{
		 
		// 2. Create the file object and a Scanner object that will read from the file submissions and teams
		File submissionsFile 	= new File("submissions.txt"); 
		File teamsFile 			= new File("teams.txt"); 
		
		try
		{
			// 3. Create a scanner file object to read the Submission file line by line
			
			Scanner fileSubmissionsInput	= new Scanner(submissionsFile);
		
			while(fileSubmissionsInput.hasNextLine())
			{
				//4. If statement to Know which line the code are reading
				
				if(lineCount == 1)
				{
					head 	= fileSubmissionsInput.nextLine();
				}
				else if(lineCount == 2)
				{
					// 5. Declare variables and storage informations to use in the code
					 teamsNbr	= fileSubmissionsInput.nextInt();				 
					 problemNbr	= fileSubmissionsInput.nextInt();
					 totalMnt 	= fileSubmissionsInput.nextInt();
					 addMnt		= fileSubmissionsInput.nextInt();
					 

					 // 6. Define the dimensions of each array based in the second line information from submission file
					 submissionsArray	= new int [teamsNbr][problemNbr];
					 timesArray			= new int [teamsNbr];
					 solvedArray		= new String [teamsNbr][problemNbr];
					 slvArray			= new int [teamsNbr];
					 penaltyArray		= new int [teamsNbr];
					 teamsArray			= new String[teamsNbr];
					 sortArray			= new int [teamsNbr][3];

					 //7. Filed the part of solved array that should receive an "N" for not solved questions
					for(int r = 0; r < solvedArray.length;r++)
					{
						for(int c = 0; c < solvedArray[r].length;c++)
						{
							solvedArray[r][c] = "N";
						}
					}
				}
	
				//8. Read the submission and declare the variables to store the informations
				else
				{
					int idTeams 	= fileSubmissionsInput.nextInt();
					int time 		= fileSubmissionsInput.nextInt();
					int idProblem	= fileSubmissionsInput.nextInt();
					String solved 	= fileSubmissionsInput.next();
				 
					// 9. Fill the arrays and validate if the submission file informations are right
					
					if(D_M_ScoreboardMethods.isValid(idTeams,idProblem, teamsNbr, problemNbr)==true && time <= totalMnt)
					{
						submissionsArray[idTeams-1][idProblem-1] += 1 ;
						// 10. Increment the variable with the total numbers of valid submissions
						totValidSub +=1;
						
						//11. Fill the timesArray with total time of each team, solvedArray with a "Y" only for the solved questions
						// and the slvArray with the total number of solved questions per team
						if(solved.equals("Y"))
						{
							D_M_ScoreboardMethods.getTotTime(timesArray, idTeams, time);;
							solvedArray[idTeams-1][idProblem-1] = solved ;
							D_M_ScoreboardMethods.getNumSolved(slvArray, idTeams);
						}
					}
					else
					{
						//12. Increment the variable with the total numbers of invalid submissions
						totInvalidSub +=1;
					}
				//13. Increment the variable to count the lines of submission file
				}lineCount++; 
				
			//14. Close the submission file
			}fileSubmissionsInput.close();

		}
		catch (FileNotFoundException ex)
		{
			// 15. Print a message in case the file was not found
			System.out.println("Your file was not found.");
			System.out.println("Exception message: " + ex.getMessage());
		}
		
		//16. Create a scanner file object to read the Teams file line by line
		try
		{
			Scanner fileTeamsInput 	= new Scanner(teamsFile);
			while(fileTeamsInput.hasNextLine())
			{
				//17. Read each token
				int idTeam 		= fileTeamsInput.nextInt();
				String teamName = D_M_ScoreboardMethods.truncate(fileTeamsInput.nextLine().trim(), 15);
				
				//18. Fill the array with the name of the team, using the idtem as index
				teamsArray[idTeam-1] = teamName;
				 
			}//19. Close the Teams file
			fileTeamsInput.close();
		}
		catch (FileNotFoundException ex)
		{
			//20.  Print a message in case the file was not found
			System.out.println("Your file was not found.");
			System.out.println("Exception message: " + ex.getMessage());
		}
		

		//21. Method to get the total of minutes per team with penalties
		D_M_ScoreboardMethods.getPenalty(solvedArray, submissionsArray, penaltyArray);
		

		//22. Final Print Report UNSORTED
		
		//D_M_ScoreboardMethods.printReport(teamsArray, slvArray, timesArray, penaltyArray, solvedArray, submissionsArray,
		//totValidSub, totInvalidSub, head);
	
		
		//23. Sort by ranking SLV + Time
		D_M_ScoreboardMethods.sortArray(slvArray, timesArray, penaltyArray, sortArray);
				
		//24. Final Print Report SORTED
		D_M_ScoreboardMethods.printReportSort(teamsArray, slvArray, solvedArray,submissionsArray, sortArray,totValidSub,totInvalidSub,head);	
		
	}
}

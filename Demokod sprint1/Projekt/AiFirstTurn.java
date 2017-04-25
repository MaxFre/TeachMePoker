//package Projekt;
//
//import java.util.Random;
//


/**
 * ANVÄNDS EJ!! Början till en uppdelning för AI
 */


//public class AiFirstTurn {
//	
//	private String card1, card2;
//	
//	private String card1Clr,card2Clr;
//	
//	private int card1nbr, card2nbr;
//	int likelyhood = 10;
//	private boolean bet;
//	private double pot;
//	private double playerPot;
//	
//	
//	public AiFirstTurn(String card1, String card2, int playerPot){
//
//			this.card1 = card1;
//			this.card2 = card2;		
//			this.playerPot = playerPot;
//			
//			getCards();
//			Calclikelyhood();	
//			pot();
//			decision();
//	}
//	
//	
//	
//	
//	public void getCards(){
//		
////		Random rand = new Random();
////		Random rand2 = new Random();
////		
////		card1nbr = cards[rand.nextInt(13)+1];
////		card2nbr = cards[rand2.nextInt(13)+1];
////		
////		card1Clr = Color[rand.nextInt(4)];
////		card2Clr = Color[rand2.nextInt(4)];
////		
//		card1 = card1nbr + "-"+ card1Clr;
//		
//		card2 = card2nbr + "-"+ card2Clr;
////		
////		if(card1.equals(card2)){														//om korten e lika, blir 2 nytt.
////		   Random rand3 = new Random();
////		   card2 = cards[rand3.nextInt(12)] + "-" + Color[rand3.nextInt(4)];
//////			System.out.println("ReDraw");
//////			System.out.println(card1 + "    " + card2);
////		}
////		
////		
////		System.out.println(card1 + "    " + card2);
//	}
//	
//	public void pot(){
//			
//		pot = 500;
////		Random amount = new Random();
////	    pot = amount.nextInt(1000);
////		System.out.println("pot " + pot);
////		System.out.println("PlayerChips " + playerPot);
////				
//	}
//	
//	public boolean decision(){  //vad Ai gör.
//		int whatTodo = 0;
//		
//		Random decide = new Random();
//		
//		double test2 = playerPot/30;
//		
//		double test = pot/25;
//		whatTodo = decide.nextInt(100);
////		System.out.println("med pot " + (whatTodo+test));
////		System.out.println("med spelarchips " + (likelyhood+test2));
////		whatTodo+=test;
////		likelyhood+=test2;
////		
//	
//		
////		System.out.println("likelyhood " + likelyhood +"  gräns " + whatTodo );
//		if(whatTodo<=likelyhood){
////			System.out.println("bid");
//			bet=true;
//		}
//		
//		else{
////			System.out.println("fold");
//			bet=false;
//		}
//		return bet;
//	}
//	
//	
//	
//	public int Calclikelyhood(){  //räknar ut vad Ai ska göra.
//		
//
//		if(card1Clr.equals("H") && card2Clr.equals("D") || card1Clr.equals("D") && card2Clr.equals("H")){
//			likelyhood+=20;
//			System.out.println("Same Color");
//		}
//		
//		if(card1Clr.equals("S") && card2Clr.equals("C") || card1Clr.equals("C") && card2Clr.equals("S")){
//			likelyhood+=20;
//			System.out.println("Same Color");
//		}
//		
//		if(card1.equals("H") && card2.equals("H") || card1.equals("D") && card2.equals("D") || card1.equals("S") && card2.equals("S") || card1.equals("C") && card2.equals("C")){
//			likelyhood+=30;
//			System.out.println("Same Suit");			
//		}
//		
//		if(card1nbr==2 && card2nbr==14 || card2nbr==2&&card1nbr==14){
//			System.out.println("stegchans");
//			likelyhood+=20;
//		}
//		
//		if(card1nbr+card2nbr<17){   //låga kort.
//			System.out.println("Low");	
//			
//			if(card1nbr+-1 == card2nbr){  //om stegchans.
//				System.out.println("stegchans");
//				likelyhood+=20;
//			}
//			
//			if(card1nbr == card2nbr){   //par
//				likelyhood += 90;
//				System.out.println("add2");
//			}
//		}
//		
//		
//		
//		if(card1nbr+card2nbr>17){   //höga kort.
//		  likelyhood+=20;
//		  System.out.println("High");
//		  
//		  
//		  if(card1nbr==card2nbr){ //par
//		    likelyhood+= 100;
//			System.out.println("even");
//		  }
//		
//		
//		
//		if(card1nbr+-1 == card2nbr ){  //om stegchans.
//			System.out.println("stegchans");
//			likelyhood+=30;
//		  }
//	  }
//		
//		
//		if(card1nbr+card2nbr>20){ 
//			likelyhood+=40;
//			System.out.println("rly high");
//		}
//		System.out.println("likelyhood " + likelyhood);
//		
//		
//		return likelyhood;
//	}
//	
//
//}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentfinal;

/**
 *
 * @author Student
 */
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Messages {
     
    
        private String messageID;
        private int messageNumber;
        private String recipient;
        private String message;
        private String messageHash;
        private String messageStatus;
        
        private static ArrayList<Messages> sentMessages=new ArrayList<>();
        
        private static ArrayList<Messages> disregardedMessages = new ArrayList<>();
        
        private static ArrayList<Messages> storedMessages = new ArrayList<>();
        
        private static int totalMessages = 0;
        
        
        
        public Messages(
                String messageID,
                int messageNumber,
                String recipient,
                String message){
            
             this.messageID = messageID;
             this.messageNumber =messageNumber;
             this.recipient = recipient;
             this.message = message;
             this.messageStatus = "";
             
             this.messageHash = createMessageHash();
        }
        
        //=============================
        //START QUICKCHAT
        //=============================
        
        public static void startQuickChat(Scanner input){
            System.out.println("\n=============================");
            System.out.println("        Welcome to QuickChat  ");
            System.out.println("===============================");
            
            System.out.println(
                "How many messages would you like to enter?"
            );
        
            int numberOfMessages;

while (true) {
    System.out.print("How many messages would you like to enter? ");

    String numberInput = input.nextLine();

    try {
        numberOfMessages = Integer.parseInt(numberInput);

        if (numberOfMessages >= 0) {
            break;
        } else {
            System.out.println("Please enter zero or a positive number.");
        }

    } catch (NumberFormatException e) {
        System.out.println("Invalid input. Please enter a whole number.");
    }
}

        boolean running = true; 
        while (running) {
            
            System.out.println("\n=============================");
            System.out.println("       QuickChat  ");
            System.out.println("===============================");
            System.out.println("\n1. Send Messages");
            System.out.println("2. Show recently sent messages");
            System.out.println("3. Quit");
            System.out.print("Enter your option: ");

            String option = input.nextLine();

            switch (option) {

                 case "1":
                    System.out.println("Send Messages selected.");
                    sendMessages(input, numberOfMessages);
                    break;

                case "2":
    System.out.println("\nRecently sent messages:");

    if (sentMessages.isEmpty()) {
        System.out.println("No messages have been sent.");
    } else {
        for (Messages msg : sentMessages) {
            System.out.println("Message ID: " + msg.messageID);
            System.out.println("Recipient: " + msg.recipient);
            System.out.println("Message: " + msg.message);
            System.out.println("Hash: " + msg.messageHash);
            System.out.println("Status: " + msg.messageStatus);
            System.out.println();
        }
    }
    break;

                case "3":
                    running = false;
                    System.out.println("Thank you for using QuickChat.");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
        
        //==========================
        //SEND MESSAGES
        //==========================
        
       public static void sendMessages(
               Scanner input,
               int numberofMessages){
           
           /*
           *The loop allows the user to enter only the number
           *of messages they selected
           */
           
        for (int i = 1; i <= numberofMessages; i++) {
             
           System.out.println("\n========================");
           System.out.println("MESSAGE " + i );
           System.out.println("=========================");
           
           //==========================
           //MESSAGE ID
           //==========================
           String messageID = generateMessageID();

           
           System.out.println(
               "Message ID generated: " + messageID
           );
           
           //=========================
           //RECIPIENT
           //=========================
           
           String recipient;
                   
            while (true){
                
                System.out.println(
                     "Enter recipient cellphone number: "
                );
                
                recipient = input.nextLine();
                
                Messages temporaryMessage = new Messages(
                     messageID,
                     i,
                     recipient,
                     ""
                );
                
                if (temporaryMessage.checkRecipientCell()){
                    
                    System.out.println(
                         "Cell phone number successfully captured"                    
                    );
                    
                    break; 
                } else {
                    
                    System.out.println(
                        "Cell phone number is incorrectly formatted"
                       +"or does not contain international code."
                       +"Please correct the number and try again."
                    );
                }
            }
            
            //=========================
            //MESSAGE
            //=========================
            
            String messageText;
            
            while (true){
                
                System.out.println("Enter your message: ");
                
                messageText = input.nextLine();
                
                if (messageText.length() <=250){
                    
                    break;
                    
                } else{
                    
                    System.out.println(
                         "Please enter a message of less than 250"
                         +"charaters"
                    );
                }
            }
            
            //========================
            //CREATE MESSAGE
            //========================
            
            Messages newMessage = new Messages(
                messageID,
                i,
                recipient,
                messageText );
            
            //========================
            //DISPLAY HASH
            //========================
            
            System.out.println(
                 "Message Hash: "
                 +newMessage.createMessageHash()
              );
            
            //=========================
            //SEND MESSAGE 
            //=========================
            
            String result = newMessage.Messages(input);
            
            System.out.println("\n" + result);
            
         }
       }
       
            //=========================
            //GENERATE MESSAGE ID
            //=========================
       
            public static String generateMessageID() {
        Random random = new Random();
        long id = 1_000_000_000L
                + (long) (random.nextDouble() * 9_000_000_000L);
        return String.valueOf(id);
    }
       //===============================
       //CHECK RECIPIENT CELL NUMBER
       //===============================
       public boolean checkRecipientCell(){
           
           return recipient != null
                   &&recipient.matches("^\\+27[6-8][0-9]{8}$");
           }
       
       //CREATE MESSAGE HASH
       
      public String createMessageHash(){
        
          String firstTwoNumbers;
          
          if (messageID.length() >=2){
              
             firstTwoNumbers=
                     messageID.substring(0, 2);
          }else{
              
              firstTwoNumbers = messageID;
           }
          
          String firstWord = "";
          String lastWord = "";
          
          if (message != null && !message.trim().isEmpty()){
              
              String cleanMessage = message.trim();
              
              String[] words = cleanMessage.split("\\s+");
              
              firstWord = words[0];
              
              lastWord = words[words.length - 1];
              
              //Remove punctuation
              firstWord = firstWord.replaceAll(
                       "[^a-zA-Z0-9]",""
              );
              
              lastWord = lastWord.replaceAll(
                      "[^a-zA-Z0-9]",""
              );
          }
          
          return (
               firstTwoNumbers
               +":"
               +messageNumber
               +":"
               +firstWord
               +lastWord
          ).toUpperCase();
          
      }
      //SEND,DISREGARD OR STORE MESSAGE 
      
      public String Messages(Scanner input){
           System.out.println("\n========================");
           System.out.println("   SEND MESSAGE " );
           System.out.println("=========================");
           
           System.out.println("1. Send Message");
           System.out.println("2.  Disregard Message" );
           System.out.println("3.  Store Message");
           
           System.out.print("Choose an option: ");
           
           String option = input.nextLine();
           
           switch (option){
            case "1":
             messageStatus = "Sent";
             sentMessages.add(this);
             totalMessages++;

             return "Message successfully sent.";
            case "2":
                messageStatus = "Disregarded";
                disregardedMessages.add(this);

                return "Message disregarded.";

            case "3":
                messageStatus = "Stored";
                storedMessages.add(this);

                try (FileWriter writer =
                             new FileWriter("messages.json", true)) {

                    writer.write(
                            "{\"messageID\":\"" + messageID
                            + "\",\"messageNumber\":" + messageNumber
                            + ",\"recipient\":\"" + recipient
                            + "\",\"message\":\""
                            + message.replace("\\", "\\\\")
                                    .replace("\"", "\\\"")
                                    .replace("\n", "\\n")
                            + "\",\"messageHash\":\"" + messageHash
                            + "\",\"messageStatus\":\"" + messageStatus
                            + "\"}\n"
                    );

                } catch (IOException e) {
                    return "Error storing message: " + e.getMessage();
                }

                return "Message successfully stored.";

            default:
                return "Invalid option. Message was not sent.";
        }
    }
     
}

          

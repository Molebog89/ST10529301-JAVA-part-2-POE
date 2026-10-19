package assignmentfinal;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Student
 */
public class MessagesIT {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    public MessagesIT() {
    }

    @Before
    public void setUp() {
        // Capture console output so tests can check what was printed
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
    }

    // ------------------------------------------------------------
    // generateMessageID
    // ------------------------------------------------------------
    @Test
    public void testGenerateMessageID() {
        System.out.println("generateMessageID");
        for (int i = 0; i < 100; i++) {
            String result = Messages.generateMessageID();
            assertNotNull(result);
            assertEquals("ID must be 10 characters long", 10, result.length());
            assertTrue("ID must be numeric", result.matches("\\d{10}"));
        }
    }

    // ------------------------------------------------------------
    // checkRecipientCell
    // ------------------------------------------------------------
    @Test
    public void testCheckRecipientCell_valid() {
        System.out.println("checkRecipientCell - valid");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "Hi");
        assertTrue(instance.checkRecipientCell());

        assertTrue(new Messages("1234567890", 1, "+27712345678", "Hi").checkRecipientCell());
        assertTrue(new Messages("1234567890", 1, "+27612345678", "Hi").checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_invalid() {
        System.out.println("checkRecipientCell - invalid");
        String[] badNumbers = {
            "0831234567",       // no international code
            "27831234567",      // missing +
            "+2783123456",      // too short
            "+278312345678",    // too long
            "+27591234567",     // invalid first digit after 27
            "+27 83 123 4567",  // contains spaces
            "+27abc1234567",    // letters
            ""                  // empty
        };
        for (String number : badNumbers) {
            Messages instance = new Messages("1234567890", 1, number, "Hi");
            assertFalse("Should be invalid: " + number, instance.checkRecipientCell());
        }
    }

    @Test
    public void testCheckRecipientCell_null() {
        System.out.println("checkRecipientCell - null");
        Messages instance = new Messages("1234567890", 1, null, "Hi");
        assertFalse(instance.checkRecipientCell());
    }

    // ------------------------------------------------------------
    // createMessageHash
    // ------------------------------------------------------------
    @Test
    public void testCreateMessageHash() {
        System.out.println("createMessageHash");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "Hi tonight");
        assertEquals("12:1:HITONIGHT", instance.createMessageHash());
    }

    @Test
    public void testCreateMessageHash_removesPunctuation() {
        System.out.println("createMessageHash - punctuation");
        Messages instance = new Messages("9876543210", 3, "+27831234567", "Hello, world!");
        assertEquals("98:3:HELLOWORLD", instance.createMessageHash());
    }

    @Test
    public void testCreateMessageHash_singleWord() {
        System.out.println("createMessageHash - single word");
        Messages instance = new Messages("1234567890", 2, "+27831234567", "Hello");
        assertEquals("12:2:HELLOHELLO", instance.createMessageHash());
    }

    @Test
    public void testCreateMessageHash_extraSpaces() {
        System.out.println("createMessageHash - extra spaces");
        Messages instance = new Messages("1234567890", 4, "+27831234567", "   Good   morning   friend   ");
        assertEquals("12:4:GOODFRIEND", instance.createMessageHash());
    }

    @Test
    public void testCreateMessageHash_emptyMessage() {
        System.out.println("createMessageHash - empty message");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "");
        assertEquals("12:1:", instance.createMessageHash());
    }

    @Test
    public void testCreateMessageHash_shortID() {
        System.out.println("createMessageHash - short ID");
        Messages instance = new Messages("7", 1, "+27831234567", "Hi there");
        assertEquals("7:1:HITHERE", instance.createMessageHash());
    }

    // ------------------------------------------------------------
    // Messages(Scanner) - send / disregard / store
    // ------------------------------------------------------------
    @Test
    public void testMessages_send() {
        System.out.println("Messages - send");
        Scanner input = new Scanner("1\n");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "Hello world");
        assertEquals("Message successfully sent.", instance.Messages(input));
    }

    @Test
    public void testMessages_disregard() {
        System.out.println("Messages - disregard");
        Scanner input = new Scanner("2\n");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "Hello world");
        assertEquals("Message disregarded.", instance.Messages(input));
    }

    @Test
    public void testMessages_store() {
        System.out.println("Messages - store");
        File file = new File("messages.json");
        boolean existedBefore = file.exists();

        Scanner input = new Scanner("3\n");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "Say \"hi\" to Sam");
        assertEquals("Message successfully stored.", instance.Messages(input));
        assertTrue("messages.json should exist after storing", file.exists());

        // Clean up only if the test created the file
        if (!existedBefore) {
            file.delete();
        }
    }

    @Test
    public void testMessages_invalidOption() {
        System.out.println("Messages - invalid option");
        Scanner input = new Scanner("9\n");
        Messages instance = new Messages("1234567890", 1, "+27831234567", "Hello world");
        assertEquals("Invalid option. Message was not sent.", instance.Messages(input));
    }

    // ------------------------------------------------------------
    // sendMessages
    // ------------------------------------------------------------
    @Test
    public void testSendMessages() {
        System.out.println("sendMessages");
        // bad number -> good number -> message -> option 1 (send)
        String data = "0831234567\n"
                + "+27831234567\n"
                + "Hello world\n"
                + "1\n";
        Messages.sendMessages(new Scanner(data), 1);

        String output = outContent.toString();
        assertTrue(output.contains("Cell phone number is incorrectly formatted"));
        assertTrue(output.contains("Cell phone number successfully captured"));
        assertTrue(output.contains(":1:HELLOWORLD"));
        assertTrue(output.contains("Message successfully sent."));
    }

    @Test
    public void testSendMessages_messageTooLong() {
        System.out.println("sendMessages - message too long");
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 251; i++) {
            longMessage.append("a");
        }
        String data = "+27831234567\n"
                + longMessage + "\n"      // rejected (251 chars)
                + "Short message\n"        // accepted
                + "2\n";                   // disregard
        Messages.sendMessages(new Scanner(data), 1);

        String output = outContent.toString();
        assertTrue(output.contains("Please enter a message of less than 250"));
        assertTrue(output.contains("Message disregarded."));
    }

    @Test
    public void testSendMessages_zeroMessages() {
        System.out.println("sendMessages - zero messages");
        Messages.sendMessages(new Scanner(""), 0);
        assertFalse(outContent.toString().contains("MESSAGE 1"));
    }

    // ------------------------------------------------------------
    // startQuickChat
    // ------------------------------------------------------------
    @Test
    public void testStartQuickChat() {
        System.out.println("startQuickChat");
        String data = "abc\n"             // invalid count
                + "-1\n"                  // negative count
                + "1\n"                   // valid count
                + "1\n"                   // menu: send messages
                + "+27831234567\n"        // recipient
                + "Hello world\n"         // message
                + "1\n"                   // send
                + "2\n"                   // menu: show sent messages
                + "3\n";                  // menu: quit
        Messages.startQuickChat(new Scanner(data));

        String output = outContent.toString();
        assertTrue(output.contains("Invalid input. Please enter a whole number."));
        assertTrue(output.contains("Please enter zero or a positive number."));
        assertTrue(output.contains("Message successfully sent."));
        assertTrue(output.contains("Recipient: +27831234567"));
        assertTrue(output.contains("Status: Sent"));
        assertTrue(output.contains("Thank you for using QuickChat."));
    }

    @Test
    public void testStartQuickChat_invalidMenuOption() {
        System.out.println("startQuickChat - invalid menu option");
        String data = "1\n"    // count
                + "7\n"        // invalid option
                + "3\n";       // quit
        Messages.startQuickChat(new Scanner(data));

        String output = outContent.toString();
        assertTrue(output.contains("Invalid option. Please try again."));
        assertTrue(output.contains("Thank you for using QuickChat."));
    }
}
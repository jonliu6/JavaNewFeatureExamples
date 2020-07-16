import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.util.Scanner;

/**
 * A simple console application sending an email
 * Library dependencies: mail-1.5.0-b01.jar, activation-1.1.1.jar
 * Compile: javac -cp mail-1.5.0-b01.jar;activation-1.1.1.jar EmailSender.java
 * Run: java -cp .;mail-1.5.0-b01.jar;activation-1.1.1.jar EmailSender mailhost <smtp port>
 */
public class EmailSender {
    private static String smtpServer = null;
    private static int smtpPort = 25;
    public static void main(String[] args) {
        if (args == null || args.length < 1) {
            System.out.println("Usage: EmailSender emailServer <emailPort>");
            System.exit(0);
        }
        else {
            smtpServer = args[0];
            if (args.length > 1) {
                smtpPort = Integer.valueOf(args[1]);
            }

            System.out.println("E-mail Server: " + smtpServer + ", Port: " + smtpPort);
            Scanner scan = new Scanner(System.in);
            System.out.print("Sender Address: ");
            String sender = scan.nextLine();
            System.out.print("Receiver Address: ");
            String receiver = scan.nextLine();
            System.out.print("Subject: ");
            String subject = scan.nextLine();
            System.out.print("Content (enter a \".\" in a new line to finish): ");
            StringBuilder body = new StringBuilder();
            String line = "";
            while ((line = scan.nextLine()).startsWith(".") == false) {
                body.append(line).append("\n");
            }

            sendEmail(sender, receiver, subject, body.toString());

        }
    }

    private static void sendEmail(String mailFrom, String rcptTo, String subject, String body) {
        if (mailFrom != null && mailFrom.contains("@") && rcptTo != null && rcptTo.contains("@")) {

            Properties props = new Properties();
            // props.put("mail.smtp.auth", true); // authentication
            props.put("mail.smtp.host", smtpServer);
            props.put("mail.smtp.port", String.valueOf(smtpPort));
            // props.setProperty("mail.user", "myuser"); // credential for authentication
            // props.setProperty("mail.password", "mypwd");

            Session session = Session.getDefaultInstance(props);
            try {
                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(mailFrom));
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(rcptTo));
                msg.setSubject(subject);
                MimeBodyPart msgBody = new MimeBodyPart();
                msgBody.setContent(body, "text/html");

                // MimeBodyPart attachment = new MimeBodyPart(); // attachment
                // attachment.attachFile(new File("..."));

                Multipart parts = new MimeMultipart();
                parts.addBodyPart(msgBody);

                msg.setContent(parts);

                Transport.send(msg);

                System.out.println("E-mail sent to " + rcptTo);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        else {
            System.err.println("Error: invalid email sender/receiver address(es)!");
            System.exit((-1));
        }
    }
}

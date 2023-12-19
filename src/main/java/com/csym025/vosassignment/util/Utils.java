package com.csym025.vosassignment.util;

import com.csym025.vosassignment.entity.Bill;
import com.csym025.vosassignment.entity.Customer;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static ArrayList<Customer> loadCustomers() {
        System.out.println("load customer method is called");
        ArrayList<Customer> list = new ArrayList<>();
        try {
            File customerFile = new File("target/customers.dat");
            FileInputStream inputStream;
            ObjectInputStream objectInputFile;
            if (customerFile.exists()) {
                if (customerFile.length() > 0) {
                    inputStream = new FileInputStream("target/customers.dat");
                    objectInputFile = new ObjectInputStream(inputStream);
                    list = (ArrayList<Customer>) objectInputFile.readObject();
                    objectInputFile.close();
                } else System.out.println("customers file is empty");
            } else System.out.println("customers file does not exist");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void createCustomers(ArrayList<Customer> customers) {
        try {
            FileOutputStream outStream = new FileOutputStream("target/customers.dat");
            ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
            objectOutputFile.writeObject(customers);

            System.out.println("customers list is written to file");
            outStream.flush();
            objectOutputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Bill> loadBills(String file) {
        System.out.println("load bills method is called");
        ArrayList<Bill> list = new ArrayList<>();
        try {
            File billFile = new File(file);
            FileInputStream inputStream;
            ObjectInputStream objectInputFile;
            if (billFile.exists()) {
                if (billFile.length() > 0) {
                    inputStream = new FileInputStream(file);
                    objectInputFile = new ObjectInputStream(inputStream);
                    list = (ArrayList<Bill>) objectInputFile.readObject();
                    objectInputFile.close();
                } else System.out.println("bill file is empty");
            } else System.out.println("bill file does not exist");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void createBills(ArrayList<Bill> bills, String file) {
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);
            objectOutputFile.writeObject(bills);

            System.out.println("bills list is written to file");
            outStream.flush();
            objectOutputFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Bill> loadBillByCustId(int id, String file) {
        System.out.println("load customer bill history");
        ArrayList<Bill> list = new ArrayList<>();
        try {
            File billFile = new File(file);
            FileInputStream inputStream;
            ObjectInputStream objectInputFile;
            if (billFile.exists()) {
                if (billFile.length() > 0) {
                    inputStream = new FileInputStream(file);
                    objectInputFile = new ObjectInputStream(inputStream);
                    list = (ArrayList<Bill>) objectInputFile.readObject();
                    objectInputFile.close();
                } else System.out.println("bill file is empty");
            } else System.out.println("bill file does not exist");
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<Bill> newList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            int cID = list.get(i).getCustId();
            if (cID == id) newList.add(list.get(i));
        }

        return newList;
    }

    public static boolean authenticateEmailAddress(String email) throws CustomException {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher matcher = pattern.matcher(email);

        if (!matcher.find() && !matcher.group().equals(email))
            throw new CustomException("kindly enter valid email address");

        return true;
    }

    public static boolean authenticateMobileNo(String mobile) throws CustomException {
        Pattern p = Pattern.compile("(0|91)?[7-9][0-9]{9}");
        Matcher matcher = p.matcher(mobile);

        if (!matcher.find() && !matcher.group().equals(mobile))
            throw new CustomException("kindly enter valid mobile number");

        return true;
    }

    private String SMTP_HOST = "smtp.gmail.com";
    private String FROM_ADDRESS = "richardadeyemi42@gmail.com";
    private String PASSWORD = "City_051";
    private String FROM_NAME = "Richard";


    public boolean sendMail(String[] recipients, String[] bccRecipients, String subject, String message) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", SMTP_HOST);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug", "false");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.port", 587);

            Session session = Session.getInstance(props, new SocialAuth());
            Message msg = new MimeMessage(session);

            InternetAddress from = new InternetAddress(FROM_ADDRESS, FROM_NAME);
            msg.setFrom(from);

            InternetAddress[] toAddresses = new InternetAddress[recipients.length];
            for (int i = 0; i < recipients.length; i++) {
                toAddresses[i] = new InternetAddress(recipients[i]);
            }
            msg.setRecipients(Message.RecipientType.TO, toAddresses);

            InternetAddress[] bccAddresses = new InternetAddress[bccRecipients.length];
            for (int j = 0; j < bccRecipients.length; j++) {
                bccAddresses[j] = new InternetAddress(bccRecipients[j]);
            }
            msg.setRecipients(Message.RecipientType.BCC, bccAddresses);

            msg.setSubject(subject);

            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();

            // Fill the message
            messageBodyPart.setText("This is message body");

            // Create a multipar message
            Multipart multipart = new MimeMultipart();

            // Set text message part
            multipart.addBodyPart(messageBodyPart);

            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "target/bills.dat";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            msg.setContent(multipart);
            Transport.send(msg);
            System.out.println("Sent message successfully....");
            return true;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return false;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    class SocialAuth extends Authenticator {

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(FROM_ADDRESS, PASSWORD);
        }
    }
}

package de.cmlab.ubicomp.client;

import java.io.IOException;
import java.util.*;


import org.apache.xmlrpc.*;


public class JavaClient {

    public static void main (String [] args) {

        try {
            XmlRpcClient server = new XmlRpcClient("http://localhost/sample");
            Vector params = new Vector();


            getUserInputToParams(params);

            getSumAndPrint(server, params);


        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception);
        }
    }

    /**
     * Gets the calculation of the input from server and prints is as result.
     *
     * @param server
     * @param params
     * @throws XmlRpcException
     * @throws IOException
     */
    private static void getSumAndPrint(XmlRpcClient server, Vector params) throws XmlRpcException, IOException {

        Object result = server.execute("sample.sum", params);

        int sum = ((Integer) result).intValue();
        System.out.println("The sum is: "+ sum);
    }


    /**
     * Method invoks a scanner that takes the user input and tries to parse it to int, if this is not possible
     * user is asked to input an int again, if possible the integer is added to the argument Vector and when two
     * integers were added to the Vector, the method returns.
     *
     * @param params
     */
    private static void getUserInputToParams(Vector params) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please type in to integers to get there sum: ");

        while (params.size()<=1) {
            String input = scanner.nextLine();
            try {
                int in = Integer.parseInt(input);
                params.addElement(in);
                continue;
            } catch (Exception e) {
                System.out.println("Sry only integer allowed as input, you typed in: " + input);
            }
        }
        return;
    }
}



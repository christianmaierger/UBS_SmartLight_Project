package de.cmlab.ubicomp.client;

import java.io.IOException;
import java.util.*;


import org.apache.xmlrpc.*;


public class JavaClient {
    private static int type=0;


    public static void main (String [] args) {

        try {
            XmlRpcClient server = new XmlRpcClient("http://localhost/sample");
            Vector params = new Vector();

            chooseSumOrMultiply();


            getUserInputToParams(params);

            if(type == 1) {
                getSumAndPrint(server, params);
            }
            if(type == 2) {
                getMultiplicationAndPrint(server, params);
            }



        } catch (Exception exception) {
            System.err.println("JavaClient: " + exception);
        }
    }



    private static void chooseSumOrMultiply() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please type in 1 to get the sum or 2 to get the multiplication result of two integers: ");

        while(type!=1 || type!=2) {
            String input = scanner.nextLine();
            try {

                type = Integer.parseInt(input);
                if (type!=1 && type!=2) {
                    System.out.println("Please just type 1 or 2!");
                }
                    if (type == 1) {
                        System.out.println("You choose sum");
                        break;
                    } else if (type == 2) {
                        System.out.println("You choose multiplication");
                        break;
                    }
            } catch (Exception e) {
                System.out.println("Sry only integer allowed as input, you typed in: " + input);
            }
        }
        return;
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

        Object result  = server.execute("sample.sum", params);

        int sum = ((Integer) result).intValue();
        System.out.println("The sum is: "+ sum);
    }

    private static void getMultiplicationAndPrint(XmlRpcClient server, Vector params) throws XmlRpcException, IOException {

        Object result  = server.execute("sample.multiply", params);

        int mult = ((Integer) result).intValue();
        System.out.println("The multiplication rsult is: "+ mult);

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
        scanner.close();
        return;
    }
}



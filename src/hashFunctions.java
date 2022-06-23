// Name: Amir Davis
// Class: CS 3305/Section# 04
// Term: Spring 2022
// Instructor: Dr. Haddad
// Assignment: 6

import java.util.Scanner;

public class hashFunctions {
    public static void main(String[] args) {
        int userChoice = 0;
        int[] keys = {1234, 8234, 7867, 1009, 5438, 4312, 3420, 9487, 5418, 5299,
                5078, 8239, 1208, 5098, 5195, 5329, 4543, 3344, 7698, 5412,
                5567, 5672, 7934, 1254, 6091, 8732, 3095, 1975, 3843, 5589,
                5439, 8907, 4097, 3096, 4310, 5298, 9156, 3895, 6673, 7871,
                5787, 9289, 4553, 7822, 8755, 3398, 6774, 8289, 7665, 5523};

        while(userChoice != 5){
            Scanner myScanner = new Scanner(System.in);
            printMenu();
            userChoice = myScanner.nextInt();
            System.out.println();

            /*Switch statement that performs a method depending on the user's input. Statement ends if
            the user inputs 5
             */
            switch (userChoice){
                case 1:
                    H1(keys);
                    break;
                case 2:
                    H2(keys);
                    break;
                case 3:
                    H3(keys);
                    break;
                case 4:
                    H4(keys);
                    break;
                case 5:
                    break;
            }
        }
    }

    /*
    Method to print out the options the user can choose from in the main method
     */
    public static void printMenu(){
        System.out.print("\n-----MAIN MENU--------------------------------------\n" +
                "1. Run HF1 (Division method with Linear Probing)\n" +
                "2. Run HF2 (Division method with Quadratic Probing)\n" +
                "3. Run HF3 (Division method with Double Hashing)\n" +
                "4. Run HF4 (Student Designed HF)\n" +
                "5. Exit program\n\n" +
                "Enter option number: ");
    }

    /*
    Adds up all the attempted probes within a hash function and then returns that sum
     */
    public static int sumProbes(int[][] array){
        int totalNumberOfProbes = 0;
        for(int j = 0; j < array.length; j++){
            totalNumberOfProbes += array[j][1];
        }
        return totalNumberOfProbes;
    }

    /*
    Hash function that implements the division method with linear probing for collision handling
     */
    public static void H1(int[] array) {
        int Hkey;
        int numOfProbes = 0;
        int[][] array2 = new int[50][2];

        for (int i = 0; i < array.length; i++) {
            //Executes the division method to determine the location the key will be placed in the hash table
            Hkey = array[i] % array.length;
            if (Hkey > 49) {
                Hkey = Hkey % array.length;
            }

            /*Checks to see if a key is already present in the 2D array's position. If so, executes linear
            probing for collision handling.
             */
            while (array2[Hkey][0] != 0) {
                numOfProbes++;
                Hkey++;
                if (Hkey > 49) {
                    Hkey = Hkey % array.length;
                }
            }

            //Inputs the key and number of probes it took in the Hash table and resets numOfProbes back to 0.
            array2[Hkey][0] = array[i];
            array2[Hkey][1] = numOfProbes;
            numOfProbes = 0;
        }
        printHashTable(array2, 1);
    }

    /*
    Hash function that implements the division method and quadratic probing for collision handling
     */
    public static void H2(int[] array){
        int Hkey;
        int numOfProbes = 0;
        int[][] array2 = new int[50][2];

        for(int i = 0; i < array.length; i++){
            Hkey = array[i] % array.length;

            //If the hash key goes over 49, the 2D's array maximum length since it's 0 - 49,
            // the Hkey is modded by 50
            if(Hkey > 49){
                Hkey = Hkey % array.length;
            }

            int placeHolder = Hkey;

            /*Checks to see if a key is already present in the 2D array's position. If so, executes
            quadratic probing for collision handling.
             */
            while (array2[Hkey][0] != 0){
                numOfProbes++;
                Hkey = placeHolder;

                int numToAdd = (int) Math.pow(numOfProbes, 2);
                Hkey = Hkey + numToAdd;

                if (Hkey >= 50){
                    Hkey = Hkey % array.length;
                }

                if(numOfProbes == 50){
                    break;
                }
            }
            array2[Hkey][0] = array[i];
            array2[Hkey][1] = numOfProbes;
            numOfProbes = 0;
        }
        printHashTable(array2, 2);
    }

    /*
    Hash function that implements the division method with double hashing for collision handling
     */
    public static void H3(int[] array){
        int Hkey;
        int numOfProbes = 0;
        int[][] array2 = new int[50][2];

        for (int i = 0; i < array.length; i++) {
            Hkey = array[i] % array.length;

            /*If the hash key (Hkey) is greater than 49, then Hkey will me moded by 50 to stay in bounds
            of the array
             */
            if (Hkey > 49) {
                Hkey = Hkey % array.length;
            }

            int placeHolder = Hkey;
            //2nd hash function
            int Hkey2 = 30 - (array[i] % 25);

            /*Checks to see if a key is already present in the 2D array's position. If so, executes
            double hashing for collision handling.
             */
            while (array2[Hkey][0] != 0){
                numOfProbes++;
                Hkey = placeHolder % 50;
                Hkey = Hkey + (numOfProbes * Hkey2);

                /*If the hash key (Hkey) is greater than 49, then Hkey will me moded by 50 to stay in bounds
                of the array
                */
                if(Hkey > 49){
                    Hkey = Hkey % array.length;
                }
                /*If numOfProbes reaches 50, then the if statement will output "Unable to hash: " that key
                and will break out of the while loop and continue to the next key.
                 */
                if(numOfProbes == 50){
                    System.out.println("Unable to hash " + array[i]);
                    break;
                }
            }
            //Adds the key and numOfProbes to the hash key only if the numOfProbes is less than 50
            if(numOfProbes < 50){
                array2[Hkey][0] = array[i];
                array2[Hkey][1] = numOfProbes;
            }
            numOfProbes = 0;
        }
        printHashTable(array2, 3);
    }

    /*
    This hash function implements a reverse function and uses a modified version of the double hashing
    in H3 for the error handling in this method. I chose to add a third hash function, which makes for
    triple hashing, and made the third has function equal to close to half of the second hash function.
     */
    public static void H4(int[] array){
        int Hkey;
        int Hkey1;
        int numOfProbes = 0;
        int[][] array2 = new int[50][2];

        for (int i = 0; i < array.length; i++) {
            int reversedNumber = 0;
            Hkey = array[i];
            Hkey1 = Hkey;

            /*Here the number is reversed. After each time a digit is extracted and added to
            reversedNumber, reversedNumber is then multiplied by 11 instead of 10. Multiplying by 10
            would yield a true reversedNumber, but multiplying by 11 yielded a lower probe count.
             */
            while(Hkey1 > 0){
                int digit = Hkey1 % 10;
                reversedNumber += digit;
                if((Hkey1 / 10) != 0){
                    reversedNumber *= 11;
                }
                Hkey1 /= 10;
            }

            /*If the hash key (Hkey) is greater than 49, then Hkey will me moded by 50 to stay in bounds
            of the array
             */
            if (reversedNumber > 49) {
                reversedNumber = reversedNumber % array.length;
            }

            /*Both placeHolder and originalNumber are needed to keep reversing the same number within
            the while loop for the double hash.
             */
            int placeHolder = reversedNumber;
            int originalNumber = placeHolder;
            //2nd hash function
            int Hkey2 = 30 - (array[i] % 25);
            //3rd Hash function
            int Hkey3 = 15 - (array[i] % 11);

            /* Implements a modified version of double hashing from H3 for collision handling. Instead
            of having only two hash functions, this modified version has three.
             */
            while (array2[reversedNumber][0] != 0){
                numOfProbes++;
                placeHolder = originalNumber;
                //1st Hash function
                while(placeHolder > 0){
                    int digit = placeHolder % 10;
                    reversedNumber += digit;
                    if((placeHolder / 10) != 0){
                        reversedNumber *= 11;
                    }
                    placeHolder /= 10;
                }
                reversedNumber = reversedNumber + (numOfProbes * Hkey2) + Hkey3;

                /*If the hash key (Hkey) is greater than 49, then Hkey will me moded by 50 to stay in bounds
                of the array
                */
                if(reversedNumber > 49){
                    reversedNumber = reversedNumber % array.length;
                }
                if(numOfProbes == 50){
                    System.out.println("Unable to hash " + array[i]);
                    break;
                }
            }
            //Only adds the key to the hash function if the numOfProbes is less than 50
            if(numOfProbes < 50){
                array2[reversedNumber][0] = array[i];
                array2[reversedNumber][1] = numOfProbes;
            }
            numOfProbes = 0;
        }
        printHashTable(array2, 4);
    }

    //Prints out the hash table for a hash function
    public static void printHashTable(int[][] array, int num){
        String s;
        System.out.println("Hash table resulted from H" + num + ": ");
        System.out.println("\nIndex\tKey\t\tProbes" +
                "\n------------------------");
        for(int j = 0; j < array.length; j++){
            System.out.print(j + "\t\t");
            for(int k = 0; k < 2; k++){
                //System.out.print(array[j][k] + "\t");
                System.out.printf("%-8d", array[j][k]);
            }
            System.out.println();
        }
        System.out.println("------------------------");
        System.out.println("\nSum of probe values = " + sumProbes(array) + " probes");
    }
}

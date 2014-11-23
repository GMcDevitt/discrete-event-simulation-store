/* This package contains code for three methods that can be used to generate
 * random values with given distributions, to be used, for example, in discrete
 * event simulations.  There are functions for a Poisson distribution, a looose normal
 * distirbution, and a tight normal distrubution. Those familiar with statistics can
 * modify the methods to manipulate the range and variance of returned values.
 */
import java.util.Random;

public class RandomForSimulations {

    /* The main method simply demonstrates the use of the functions
     getPoisson(), getNormalLoose(), and getNormalTight() for a fixed mean of 10,
     each generating 20 values.
     */
    public static void main(String[] args) {

        final double MEAN = 10;  //mean for a sample run, fixed at 10

        // print headings for a table of results
        System.out.print("Poisson" + "\t");
        System.out.print("loose" + "\t");
        System.out.println("tight");

        // 20 times, call each function and print its values under the headings
        for (int i = 1; i <= 20; i++) {
            System.out.print(getPoisson(MEAN) + "\t");
            System.out.print(getNormalLoose(MEAN) + "\t");
            System.out.println(getNormalTight(MEAN));
        } // end for

    } // end main()
//*****************************************************************************

    /* The method getPoisson will generate random numbers with a Poisson distribution
     centered on the value input as mu.  It returns an integer value but takes a double
     value as its argument.   The method is based on an algorithm from Donald Knuth.
     See:  Knuth's The Art of Computer Programming, Volume 2. (1969 edition)
     */
    public static int getPoisson(double mu) {

        int r = 0;
        double a = Math.random();
        double p = Math.exp(-mu);

        while (a > p) {
            r++;
            a = a - p;
            p = p * mu / r;
        }
        return r;
    } // end getPoisson()
//*****************************************************************************

    /* The method getNormalLoose() returns a positive integer from a normal
     distribution centered on mu with standard deviation mu.  This is a broad
     distribution. For a narrower distribution use the method getNormalTight().
     In theory, 68.2 percent of the values generated will be between 0 and (2*mu).
     However, since negative values are not returned, roughly 85 percent of values
     will actually be less than (2*mu) and about 15 percent will be over (mu*2).
     For example, if mu = 10, 85 percent of the values will be between 0 and 20.
     */
    public static int getNormalLoose(double mu) {

        double g;       // a random number from a Gaussian (normal) distribution
        // see Java's Random class documentation for more info
        int value = 0;  // the value to be returned by this method

        // create an instance of Java's Random class
        Random randomGenerator = new Random();

        // use a function from the Random class object to generate a number
        // with a Gaussian (normal distribution)centered on 0 with deviation 1
        g = randomGenerator.nextGaussian();

        // This loop will repeat if  the number picked is negative.
        // This ensures that the method not return a negative value.
        while (value <= 0) {
            value = (int) (mu + randomGenerator.nextGaussian() * (mu));
        }
        return value;

    } // end getNormalLoose
//*****************************************************************************

    /* The method getNormalLoose() returns a positive integer from a normal
     distribution centered on mu with standard deviation (mu/2) .  This is a
     narrow distribution. For a broader distribution, use the method getNormalBroad()
     about 68.2 percent of the values returned will be between mu-(mu/2)  and mu + (mu/2).
     For example, if mu = 10, 68.2 percent of the values will be between 5 and 15.
     */
    public static int getNormalTight(double mu) {

        double g;       // a random number from a Gaussian (normal) distribution
        // see Java's Random class documentation for more info
        int value = 0;  // the value to be returned by this method

        // create an instance of Java's Random class
        Random randomGenerator = new Random();

        // use a function from the Random class object to generate a number
        // with a Gaussian (normal distribution)centered on 0 with deviation 1
        g = randomGenerator.nextGaussian();

        // This loop will repeat if  the number picked is negative.
        // This ensures that the method not return a negative value.
        while (value <= 0) {
            value = (int) (mu + randomGenerator.nextGaussian() * (mu / 2));
        }
        return value;

    } // end getNormalTight
//*****************************************************************************

} // end class randomforsimulations



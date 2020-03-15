package com.my.auth2.server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    public static void main(String[] args) {
    	 RandomValueStringGenerator generator = new RandomValueStringGenerator(10);
    	 Set<String> set=new HashSet<String>();
    	 int size=1000;
    	 CountDownLatch countDownLatch=new CountDownLatch(size);
    	 for (int i = 0; i < size; i++) {
    		 //set.add(generator.generate());
    		 
    		 new Thread(new Runnable() {
				
				@Override
				public void run() {
					 set.add(generator.generate());
					 countDownLatch.countDown();
				}
			}).start();
			
    		
		 }
    	 try {
			countDownLatch.await();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 System.out.println(set.size());
    	
	}
}

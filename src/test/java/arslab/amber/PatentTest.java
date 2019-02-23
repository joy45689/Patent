package arslab.amber;

import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class PatentTest
{
    /**
     * Rigorous Test :-)
     */
    Patent patent=new Patent();
    double[]k={-735.3,523.2,5261.8};
    double[]s={0.023,-0.015,0.0078};
    double[]V={1383.2,2965.8,1383.2,2965.7,3196.1};
    double[]r1={178.2,4789.3,378.8};
    double[]r2={7455.1,409.2,7809.2};
    double[]r3={2946.8,9163.5,-4236.3};
    double[]r4={132.3,-5734.4,89.34};
    double[]r5={1945.5,905.3,6734.1};

    double[]p1={569.5,-7836.1,1449.2};
    double[]p2={4658.2,5068.3,240.4};
    double[]p3={793.8,5723.9,-4372.3};
    double[]p4={6489.2,1644.5,2976.6};
    double[]p5={-593.7,2789.1,989.2};
    List<double[]>r=new ArrayList<>();
    List<double[]>p=new ArrayList<>();

    @Before
    public void setup(){
        r.add(r1);
        r.add(r2);
        r.add(r3);
        r.add(r4);
        r.add(r5);


        p.add(p1);
        p.add(p2);
        p.add(p3);
        p.add(p4);
        p.add(p5);
    }

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void instance1(){
        double result;
        int i=0;
        for(double v:V){
            double []cipher=patent.enc1(k,v,r.get(i));
            result=patent.dec1(k,cipher,r.get(i));
            i++;
            System.out.printf("Expected:%f Actual:%f\n",v,result);
            Assert.assertEquals(v, result, 0.00000001);
        }
    }

    @Test
    public void instance2(){
        double result;
        int i=0;
        for(double v:V){
            double []cipher=patent.enc2(k, s, v, r.get(i), p.get(i));
            result=patent.dec2(k, s, cipher, r.get(i), p.get(i));
            i++;
            System.out.printf("Expected:%f Actual:%f\n",v,result);
            Assert.assertEquals(v, result, 0.00000001);
        }
    }
    @Test
    public void instance3(){
        double result;
        int i=0;
        for(double v:V){
            double []cipher=patent.enc3(k, s, v, r.get(i), p.get(i));
            result=patent.dec3(k, s, cipher);
            i++;
            System.out.printf("Expected:%f Actual:%f\n",v,result);
            Assert.assertEquals(v, result, 0.00000001);
        }
    }

    @Test
    public void mul(){
        double v1=48;
        double v2=55.3;
        double actual=patent.Hmul(v1, v2, k, s, r.subList(0, 2), p.subList(0,2));
        System.out.printf("Expected:%f Actual:%f\n",v1*v2,actual);
        Assert.assertEquals(v1*v2, actual, 0.00000001);
    }

    @Test
    public void sum(){
        double actual=patent.Hsum(V, k, s, r, p);
        System.out.printf("Expected:%f Actual:%f\n",patent.sum(V),actual);
        Assert.assertEquals(patent.sum(V), actual, 0.00000001);
    }

    @Test
    public void avg(){
        double actual=patent.Havg(V, k, s, r, p);
        System.out.printf("Expected:%f Actual:%f\n",patent.sum(V)/V.length,actual);
        Assert.assertEquals(patent.sum(V)/V.length, actual, 0.00000001);
    }

}

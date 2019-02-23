package arslab.amber;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Patent
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
    Patent (){

    }

    /*instance 1*/
    double[] enc1(double[] k, double V, double[] r){
        int n=r.length-1;
        double [] ciphers =new double[k.length];
        for(int i=0;i<n;i++){
            ciphers[i]=(k[i]*V)+r[i];
        }

        ciphers[n]=k[n]*sum(r,n);
        return ciphers;
    }

    double dec1(double []k,double[]ciphers, double[] r){
        double V=0;
        int n=r.length-1;
        double L = sum(k,n);
        for(int i=0;i<n;i++){ //i=1~n-1
            V+=ciphers[i]/L;
        }
        V=V-(ciphers[n]/(L*k[n]));
        return V;
    }
    /*instance 2*/
    double[] enc2(double[] k, double[] s, double V, double[] r, double[] p){
        int n=r.length-1;
        double [] ciphers =new double[k.length];
        for(int i=0;i<n;i++){
            ciphers[i]=k[i]*(s[i]*V+p[i])+r[i];
        }
        ciphers[n]=s[n]*k[n]*sum(p,r,k,n);
        return ciphers;
    }
    double dec2(double[] k, double[] s, double[] ciphers, double[] r, double[] p){
        double V=0;
        int n=r.length-1;
        double S=sum(s,n);
        for(int i=0;i<n;i++){
            V+=ciphers[i]/(k[i]*S);
        }
        V=V-ciphers[n]/(k[n]*s[n]*S);
        return V;
    }

    /*instance 3*/
    double[] enc3(double[] k, double[] s, double V, double[] r, double[] p){
        int n=r.length-1;
        double [] ciphers =new double[k.length];
        ciphers[0]=k[0]*(s[0]*V+p[0]/k[1]-r[n]/k[n])+r[0]-p[n];
        for(int i=1;i<n;i++){
            ciphers[i]=k[i]*(s[i]*V+p[i]/k[i+1]-r[i-1]/k[i-1])+r[i]-p[i-1];
        }
        ciphers[n]=k[n]*(s[n]*V+p[n]/k[0]-r[n-1]/k[n-1])+r[n]-p[n-1];
        return ciphers;
    }
    double dec3(double[] k, double[] s, double[] ciphers){
        double V=0;
        int n=k.length-1;
        double S=sum(s);
        for(int i=0;i<=n;i++){
            V+=ciphers[i]/(k[i]*S);
        }
        return V;
    }


    /*Multiplication*/
    /**
     * Multiplication
     * Encrypt @param v1 and @param v2, then do Enc(v1)*Enc(v2)
     * Finally return the Dec(Enc(v1)*Enc(v2))
     * @param v1 the first plaintext
     * @param v2 the second plaintext
     * @param k the key
     * @param s the key
     * @param R the noise
     * @param P the noise
     * */
    double Hmul(double v1, double v2, double[] k, double[] s, List<double[]>R, List<double[]>P) {
        /*Encrypt v1 and v2*/
        double[] c1 = enc3(k, s, v1, R.get(0), P.get(0));
        double[] c2 = enc3(k, s, v2, R.get(1), P.get(1));
        /*Multiplication*/
        List<double[]>c=new ArrayList<>();
        double[] temp;
        for (double aC1 : c1) {
            temp = new double[c1.length];
            for (int j = 0; j < c2.length; j++) {
                temp[j] = aC1 * c2[j];
            }
            c.add(temp);
        }
        /*Decrypt Enc(v1)*Enc(v2)*/
        double[] c_temp=new double[c1.length];
            /* Step 1: for i from 1 to n, perform the following decryption to get (c1[i]*c2) */
        for(int i=0;i<c_temp.length;i++){
            c_temp[i]=dec3(k,s,c.get(i));
        }
            /* Step 2: perform the following decryption to get (c1*c2) */
        return dec3(k,s,c_temp);
    }


    /*Homomorphism - SUM */
    public double Hsum(double[] V, double[] k, double[] s, List<double[]>R, List<double[]>P){
        /*Encrypt each v in V*/
        List<double[]>C=new ArrayList<>();
        int count=0;
        for(double v:V){
            C.add(enc3(k,s,v,R.get(count),P.get(count)));
            count++;
        }
        /*SUM*/
        double[] temp=new double[k.length];
        for(int i=0;i<temp.length;i++){
            for (double[] c : C) {
                temp[i] += c[i];
            }
        }
        /*Decrypt*/
        return dec3(k,s,temp);

    }

    /*Homomorphism - avg */
    public double Havg(double[] V, double[] k, double[] s, List<double[]>R, List<double[]>P){
        /*Encrypt each v in V*/
        List<double[]>C=new ArrayList<>();
        int count=0;
        for(double v:V){
            C.add(enc3(k,s,v,R.get(count),P.get(count)));
            count++;
        }
        /*avg*/
        double[] temp=new double[k.length];
        for(int i=0;i<temp.length;i++){
            for (double[] c : C) {
                temp[i] += c[i];
            }
            temp[i]/=V.length;
        }
        /*Decrypt*/
        return dec3(k,s,temp);

    }




    /*--------Sum---------*/
    double sum(double[] p, double[] r, double[] k,int n){
        double sum=0;
        for(int i=0;i<n;i++){
            sum+=p[i]+r[i]/k[i];
        }
        return sum;
    }

    double sum(double[] r,int n){
        double sum=0;
        for(int i=0;i<n;i++){
            sum+=r[i];
        }
        return sum;
    }


    double sum(double[] r) {
        double sum=0;
        for(double temp:r){
            sum+=temp;
        }
        return sum;
    }


}

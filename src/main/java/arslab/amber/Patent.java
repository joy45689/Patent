package arslab.amber;

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

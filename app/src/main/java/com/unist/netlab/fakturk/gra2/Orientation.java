package com.unist.netlab.fakturk.gra2;

/**
 * Created by fakturk on 16. 10. 5.
 */

public class Orientation
{


    float[][] rotationMatrix;
    Gravity g ;

    public Orientation()
    {

        rotationMatrix = new float[3][3];
        g= new Gravity();
    }

    public float[][] getRotationMatrix()
    {

        return rotationMatrix;
    }

    public void setRotationMatrix(float[][] rotationMatrix)
    {

        this.rotationMatrix = rotationMatrix;
    }


    float[][] rotationFromEuler(float[] euler)
    {

        float alpha = euler[0];
        float beta = euler[1];
        float theta = euler[2];

        float[][] R = new float[3][3];
        R[0][0] = (float) (Math.cos(theta) * Math.cos(beta));
        R[0][1] = (float) (-Math.sin(theta) * Math.cos(alpha) + Math.cos(theta) * Math.sin(beta) * Math.sin(alpha));
        R[0][2] = (float) (Math.sin(theta) * Math.sin(alpha) + Math.cos(theta) * Math.sin(beta) * Math.cos(alpha));

        R[1][0] = (float) (Math.sin(theta) * Math.cos(beta));
        R[1][1] = (float) (Math.cos(theta) * Math.cos(alpha) + Math.sin(theta) * Math.sin(beta) * Math.sin(alpha));
        R[1][2] = (float) (-Math.cos(theta) * Math.sin(alpha) + Math.sin(theta) * Math.sin(beta) * Math.cos(alpha));

        R[2][0] = (float) -Math.sin(beta);
        R[2][1] = (float) (Math.cos(beta) * Math.sin(alpha));
        R[2][2] = (float) (Math.cos(beta) * Math.cos(alpha));

        setRotationMatrix(R);

        return R;


    }

    float[][] rotationFromGravity(float[] gravity)
    {
        float[] gravityEarth = g.getGravityEarth();
        float sinAlpha = (float) (gravity[0]/Math.sqrt(Math.pow(gravity[0],2)+Math.pow(gravityEarth[2],2)));
        float cosAlpha = (float) (gravityEarth[2]/Math.sqrt(Math.pow(gravity[0],2)+Math.pow(gravityEarth[2],2)));
        float sinBeta = (float) (gravity[1]/Math.sqrt(Math.pow(gravity[1],2)+Math.pow(gravityEarth[2],2)));
        float cosBeta = (float) (gravityEarth[2]/Math.sqrt(Math.pow(gravity[1],2)+Math.pow(gravityEarth[2],2)));



        float[][] R = new float[3][3];
        R[0][0] = cosBeta;
        R[0][1] = sinBeta * sinAlpha;
        R[0][2] = sinBeta * cosAlpha;

        R[1][0] = 0;
        R[1][1] = cosAlpha;
        R[1][2] = -sinAlpha;

        R[2][0] = -sinBeta;
        R[2][1] = cosBeta * sinAlpha;
        R[2][2] = cosBeta * cosAlpha;

        setRotationMatrix(R);

        return R;


    }

    float[][] linearizedRotationFromEuler(float[] euler)
    {

        float alpha = euler[0];
        float beta = euler[1];
        float theta = euler[2];

        float[][] R = new float[3][3];
        R[0][0] = (1);
        R[0][1] = (-theta + beta * alpha);
        R[0][2] = theta * alpha + beta;

        R[1][0] = theta;
        R[1][1] = 1 + theta * beta * alpha;
        R[1][2] = -alpha + theta * beta;

        R[2][0] = -beta;
        R[2][1] = alpha;
        R[2][2] = 1;

        return R;


    }

    float[][] updateRotationMatrix(float[][] R, float[] gyr, float deltaT)
    {
        float[][] newRotation = new float[3][3];
        newRotation[0][0] = R[0][0]+(R[0][1]*(-1)*gyr[2]+R[0][2]*gyr[1])*deltaT;
        newRotation[0][1] = R[0][1]+(R[0][0]*gyr[2]+R[0][2]*(-1)*gyr[0])*deltaT;
        newRotation[0][2] = R[0][2]+(R[0][0]*(-1)*gyr[1]+R[0][1]*gyr[0])*deltaT;

        newRotation[1][0] = R[1][0]+(R[1][1]*(-1)*gyr[2]+R[1][2]*gyr[1])*deltaT;
        newRotation[1][1] = R[1][1]+(R[1][0]*gyr[2]+R[1][2]*(-1)*gyr[0])*deltaT;
        newRotation[1][2] = R[1][2]+(R[1][0]*(-1)*gyr[1]+R[1][1]*gyr[0])*deltaT;

        newRotation[2][0] = R[2][0]+(R[2][1]*(-1)*gyr[2]+R[2][2]*gyr[1])*deltaT;
        newRotation[2][1] = R[2][1]+(R[2][0]*gyr[2]+R[2][2]*(-1)*gyr[0])*deltaT;
        newRotation[2][2] = R[2][2]+(R[2][0]*(-1)*gyr[1]+R[2][1]*gyr[0])*deltaT;

        setRotationMatrix(newRotation);

        return newRotation;
    }

    void printRotation(float[][]R)
    {
        System.out.println("Rotation Matrix: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(R[i][j]+" ");
            }
            System.out.println();
        }
    }

}

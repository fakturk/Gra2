package com.unist.netlab.fakturk.gra2;

import static android.hardware.SensorManager.GRAVITY_EARTH;

/**
 * Created by fakturk on 16. 5. 2.
 */
public class Gravity
{
    float[] gravity ;

    public Gravity() {
        gravity = new float[]{0,0,GRAVITY_EARTH};
    }



    float[] gravityAfterRotation(float[] gravity, float[][] rotationMatrix)
    {
        float[] newGravity = new float[3];
        for (int i = 0; i < 3; i++) {
            newGravity[i] = gravity[0]* rotationMatrix[i][0]+ gravity[1]* rotationMatrix[i][1]+gravity[2]* rotationMatrix[i][2];
        }
        float graNorm = gravityNorm(newGravity);
        for (int j = 0; j < 3; j++)
        {
            newGravity[j] = newGravity[j]*(GRAVITY_EARTH/graNorm);
        }
        setGravity(newGravity);

        return newGravity;
    }

    float gravityNorm(float[] gravity)
    {
        float graNorm = (float) Math.sqrt(Math.pow(gravity[0],2)+ Math.pow(gravity[1],2)+ Math.pow(gravity[2],2));
        return graNorm;
    }

    float[] getGravity()
    {
        return gravity;
    }

    void setGravity(float[] newGravity)
    {
       this.gravity = newGravity;
    }

    float[] getGravityEarth()
    {
        float[] gravityEarth = new float[]{0,0,GRAVITY_EARTH};
        return gravityEarth;
    }

    float[] getGravityDifference(float[][] R, float[] gyr, float deltaT)
    {
        float[] gravityEarth = getGravityEarth();
        float[] gD = new float[3];
        gD[0] = R[0][0]*(-1)*gyr[1]* gravityEarth[2]*deltaT+R[0][1]*gyr[0]* gravityEarth[2]*deltaT;
        gD[1] = R[1][0]*(-1)*gyr[1]* gravityEarth[2]*deltaT+R[1][1]*gyr[0]* gravityEarth[2]*deltaT;
        gD[2] = R[2][0]*(-1)*gyr[1]* gravityEarth[2]*deltaT+R[2][1]*gyr[0]* gravityEarth[2]*deltaT;
        return gD;
    }


}
